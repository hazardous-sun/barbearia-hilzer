package exercicio.src.exercicio;

public enum BarbeirosState {
    ATENDENDO,
    COBRANDO,
    DORMINDO    
}

public class Barbeiro extends Thread {

    public static int TotalBarbeiros = 0;

    private Barbearia barbearia;
    private int nome;
    private BarbeirosState state;

    public Barbeiro(Barbearia barbearia) {
        this.barbearia = barbearia;
        this.nome = ++TotalBarbeiros; 
    }

    private void atenderCliente(Barbearia barbearia) {

        Cliente cliente = barbearia.chamarCliente();
        System.out.println("Cliente sendo atendido");
        
        this.setState(BarbeirosState.ATENDENDO);

        try {
            sleep((int) (Math.random() * Math.random()));
        }

        catch (InterruptedException e) {
            System.out.println(e);
        }

        this.cobarCliente(cliente);
    }

    private void cobarCliente(Cliente cliente) {

        this.setState(BarbeirosState.COBRANDO);

        Maquininha mq = barbearia.pegarMaquinha(cliente);
        mq.cobrarCliente(cliente);
    }

    private void dormir() {
        this.setState(BarbeirosState.DORMINDO); 
        this.wait();
    }

    public string getNome() {
        return "#" + this.nome;
    }

    private void setState(BarbeirosState s) {
        this.state = s;
    }

    // Mudando o nome pra não sobreescrever o methodo da classe Thread
    public BarbeirosState getBarbeiroState() {
        return this.state;
    }

    @Override
    public void run() {

        System.out.println("Barbeiro " +this.getNome() + ": Buscando clietes");

        while (barbearia.totalPopulation() > 0) {
            atenderCliente(this.barbearia);
        }

        this.dormir();
    }
    
    private String getStateName() {
        switch (this.state) {
            case ATENDENDO: return "Atendendo";
            case COBRANDO:  return "Cobrando";
            case DORMINDO:  return "Dormindo";
            
            default: return "Desconhecido.";
        }
    }

    @Override
    public String toString() {
        return this.getNome() + ": " + getStateName(); 
    }

}
