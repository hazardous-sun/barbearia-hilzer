
public class Barbeiro extends Thread {

    public static int TotalBarbeiros = 0;

    private Barbearia barbearia;
    private int nome;
    private BarbeirosState state;

    private Cliente clienteAtual;

    public Barbeiro(Barbearia barbearia) {
        this.barbearia = barbearia;
        this.nome = ++TotalBarbeiros;

        this.setState(BarbeirosState.UNINITIALIZED);
    }

    private void atenderCliente( Cliente c ) {

        this.setState(BarbeirosState.ATENDENDO);
        this.barbearia.Display();

        try {
            sleep( Utils.RandomIntN(2, 10) * 1_000 );
        }

        catch (InterruptedException e) {
            System.out.println(e);
        }
    }

    private void cobrarCliente(Cliente cliente) {

        this.setState(BarbeirosState.AGUARDANDO_POS);
        this.barbearia.Display();

        // blocks execution until POS obtained.
        barbearia.requestPOS();

        this.setState(BarbeirosState.COBRANDO);
        this.barbearia.Display();

        try {
            sleep( Utils.RandomIntN(2, 10) * 1_000 );
        }

        catch (InterruptedException e) {
            System.out.println(e);
        }
    }

    public void procedimentoCliente(Cliente c) {
        
        this.clienteAtual = c;
        
        this.atenderCliente(c);
        this.cobrarCliente(c);

        this.clienteAtual = null;    

        // System.out.println("barbeiro finalizou.");
        
        setState(BarbeirosState.FINALIZOU_ATENDIMENTO);
    }
    
    public String getNome() {
        return "#" + this.nome;
    }

    public void setState(BarbeirosState s) {
        this.state = s;
    }

    // Mudando o nome pra não sobreescrever o methodo da classe Thread
    public BarbeirosState getBarbeiroState() {
        return this.state;
    }

    public void dormir() {
        try {
            this.setState(BarbeirosState.DORMINDO); 
            
            barbearia.Display();

            this.wait();
        }
        catch ( InterruptedException e ) {
            System.out.println("Erro na hora do barbeiro "+ this.nome +" mimir.");
        }
    }
    

    @Override
    public void run() {

        while (true) {
            Cliente c = barbearia.requestClient();

            if ( c != null ) {
                procedimentoCliente(c);

                // isso aqui pode causar um erro: corrigir
                barbearia.barbeiroFinalizou(this);
            }  
        }
    }
    
    private String getStateName() {
        switch (this.state) {
            case ATENDENDO:      return "Atendendo";
            case AGUARDANDO_POS: return "Aguardando POS";
            case COBRANDO:       return "Cobrando";
            case FINALIZOU_ATENDIMENTO: return "Finalizou ";
            case DORMINDO:       return "Dormindo";
            
            default: return "Desconhecido.";
        }
    }

    @Override
    public String toString() {
        return "Barbeiro " + this.getNome() + ": " + getStateName()
            // Eventually promote to function 
            + ( clienteAtual != null 
                ? (" C: " + clienteAtual.getNome()) 
                : "" 
            )
        ; 
    }

}