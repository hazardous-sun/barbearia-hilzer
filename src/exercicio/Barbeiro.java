
public class Barbeiro extends Thread {

    public static int BarbeirosInstanciados = 0;
   
    /*
     * Referência à barbearia
     */
    private Barbearia barbearia;

    /**
     * Representa qual a cadeira gerenciada
     * pelo barbeiro
     */
    private int cadeiraGerenciada;

    /*
     * Estado do barbeiro;
     * [ Atendendo | Aguardando POS | ... ]
     */
    private BarbeirosState state;

    /**
     * Cliente que esta na cadeira gerenciada  
     * por este barbeiro.
     */
    private Cliente clienteAtual;

    public Barbeiro(Barbearia barbearia) {
        this.barbearia = barbearia;
        this.cadeiraGerenciada = ++BarbeirosInstanciados;

        /**
         * Estado *Não inicializado* por definição;
         * Ao primeiro ciclo do Run;
         *  | Caso *não* encontre cliente, a thread dormirá;
         *  | Caso *encontre* cliente, entrará em atendimento.
         */
        this.setState(BarbeirosState.UNINITIALIZED);
    }

    /**
     * Retorna *em texto* a cadeira gerenciada
     * por este barbeiro;
     */
    public String getCadeiraGerenciada() {
        return "#" + this.cadeiraGerenciada;
    }

    /**
     * Configura o estado atual do barbeiro
     */
    private void setState(BarbeirosState s) {
        this.state = s;

        /**
         * Os barbeiros possuem uma função
         * de atualização individual na GUI;
         * 
         * Uma vez que podem trocar de estado
         * dentro de um mesmo ciclo de renderização;
         * 
         * O que resulta em uma ConcurrentModificationException.
         */
        barbearia.updateGUIBarbeiro(this, cadeiraGerenciada - 1);

        /** 
         * Função CLI depreciada 
         * barbearia.Display();
         */
    }

    public BarbeirosState getBarbeiroState() {
        return this.state;
    }

    /**
     * Identifica se um barbeiro está ou não em atendimento
     * a partir da referencia do cliente que está sendo atendido. 
     */
    public synchronized boolean isAttending () {
        return this.clienteAtual != null;
    }

    /**
     * Estapa de atendimento;
     * Fazendo a barba...
     */
    private void atenderCliente( Cliente c ) {

        this.setState(BarbeirosState.ATENDENDO);

        try {
            sleep( Utils.RandomIntN(2, 10) * 1_000 );
        }

        catch (InterruptedException e) {
            System.out.println(e);
        }
    }

    /**
     * Etapa de cobrança;
     * Requisita a POS, e então entra em estado de cobrança
     */
    private void cobrarCliente(Cliente cliente) {

        this.setState(BarbeirosState.AGUARDANDO_POS);

        synchronized (barbearia.maquininha) {

            this.setState(BarbeirosState.COBRANDO);

            try {
                sleep(Utils.RandomIntN(2, 10) * 1_000);
            }

            catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }

    /**
     * Procedimento de atendimento de um cliente
     */
    public void procedimentoCliente(Cliente c) {
        
        this.clienteAtual = c;
        
        this.atenderCliente(c);
        this.cobrarCliente(c);

        this.clienteAtual = null;    
        
        // Estado transiente entre liberar a máquina e Dormir/Próximo Atendimento 
        // Raramente aparece
        setState(BarbeirosState.FINALIZOU_ATENDIMENTO);
    }
    
    /** 
     * Altera o status do barbeiro para DORMINDO
     * e põe a Thread em espera. 
     */    
    public synchronized void dormir() {
        try {
            this.setState(BarbeirosState.DORMINDO); 
            this.wait();
        }
        catch ( InterruptedException e ) {

            // Mensagem intuitiva se houver erro na hora de dormir;
            System.out.println("O barbeiro "+ this.getCadeiraGerenciada() +" caiu da cama.");
        }
    }

    @Override
    public void run() {

        // mantem a thread viva
        while (true) {
            // System.out.println("barb " +getCadeiraGerenciada() + " Requesting client");
            /*
             * Requisita um cliente para na Barbearia
             * Cliente | Nulo
             */
            Cliente c = barbearia.requestClient();

            /**
             * Se tiver obtido um cliente
             */
            if ( c != null ) {
                /**
                 * Executa o procedimento de atendimento
                 * Atendimento + Cobrança
                 */
                procedimentoCliente(c);

                /**
                 * Se ao fim do procedimento:
                 * Não houver cliente no banco; Vai dormir;
                 * 
                 * 
                 * Se houver; Segue executando o loop normalmente, onde
                 * na próxima iteração requisitando outro cliente.
                 */
            }  
            if ( ! barbearia.hasBanco() ) {
                this.dormir();
            }
        }
    }
    
    /**
     * Função utilitária para a GUI
     */
    public String getCurrentClienteName() {
        if ( this.clienteAtual == null ) {
            return "Sem cliente";
        }

        return "Cliente: " + this.clienteAtual.getNome();
    }


    /**
     * Função utilitária para GUI 
     */
    public String getStateName() {
        switch (this.state) {
            case ATENDENDO:      return "Atendendo";
            case AGUARDANDO_POS: return "Aguardando POS";
            case COBRANDO:       return "Cobrando";
            case FINALIZOU_ATENDIMENTO: return "Finalizou ";
            case DORMINDO:       return "Dormindo";
            
            default: return "Desconhecido.";
        }
    }

    /**
     * Era útil pra CLI.
     */
    @Override
    public String toString() {
        return "Barbeiro " + this.getCadeiraGerenciada() + ": " + getStateName()
            // Eventually promote to function 
            + getCurrentClienteName(); 
    }

}