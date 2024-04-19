
import java.awt.EventQueue;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.SwingUtilities;

public class Barbearia {
    private final int QUANTIDADE_MAX_CLIENTES = 20;
    private final int QUANTIDADE_MAX_BARBEIROS = 3;
    private final int TAMANHO_BANCO = 4;

    private Queue<Cliente> clientesLevantados;
    private Queue<Cliente> banco;

    /**
     * Cada barbeiro administra a própria cadeira;
     * 
     * Tal qual barbearias de rede:
     * Uma cadeira partence a um barbeiro 
     * 
     * :. o Cliente que ocupa uma cadeira, estará referenciado 
     * pela instância de Barbeiro que a administra;
     *    
     */
    private ArrayList<Barbeiro> barbeiros;

    public Maquininha maquininha;

    private GUI gui;

    public static void main(String[] args) {
        Barbearia barbearia = new Barbearia();

        for (int i = 0; i < barbearia.QUANTIDADE_MAX_BARBEIROS; i++) {
            Barbeiro b = new Barbeiro(barbearia);
            b.start();

            barbearia.barbeiros.add(b);
        }

        GeradorClientes geradorClientes = new GeradorClientes(barbearia);
        geradorClientes.start();

    }

    public Barbearia() {
        this.clientesLevantados = new LinkedList<>();
        this.banco = new LinkedList<>();
        this.barbeiros = new ArrayList<>();
        this.maquininha = new Maquininha();

        this.gui = new GUI();
    }


    /**
     * Representa a chegada de um novo cliente
     */
    public synchronized void addCliente(Cliente novoCliente) {

        if (populationExceeded()) {
            return;
        }

        /*
         * Não há necessidade de atualizar a GUI aqui
         * Uma vez que preencherBanco() fará;
         */
        clientesLevantados.add(novoCliente);    

        preencherBanco();
        acordaBarbeiros();
    }

    /**
     * Acorda os barbeiros que estão dormindo
     */
    private void acordaBarbeiros() {

        for (Barbeiro b : barbeiros) {

            synchronized (b) {
                if (b.getBarbeiroState() == BarbeirosState.DORMINDO) {
                    b.notify();
                }
            }
        }
    }

    /**
     * Popula o banco enquanto houver clientes levantados
     * e espaço no banco
     * 
     * Atualiza a GUI 
     */
    private void preencherBanco() {
        while (banco.size() < TAMANHO_BANCO && !clientesLevantados.isEmpty()) {
            banco.add(clientesLevantados.poll());
        }

        updateGUI(UpdateTypes.Levantados);
        updateGUI(UpdateTypes.Banco);
    }

    /**
     * Se a população total da barbearia foi excedida
     */
    private boolean populationExceeded() {
        return totalPopulation() >= QUANTIDADE_MAX_CLIENTES;
    }

    /**
     * Retorna a quantidade total de clientes sendo atendidos
     */
    public synchronized int cadeirasOcupadas () {
        int i = 0;
        for (Barbeiro b : barbeiros) {
            if ( b.isAttending() ) {
                i++;
            }
        }
        return i;
    }

    /**
     * População total incluindo quem está sendo atendido
     */
    public synchronized int totalPopulation() {
        return totalWaitingPopulation() + cadeirasOcupadas();
    }

    /**
     * Apenas clientes que estão aguardando atendimento 
     */
    public synchronized int totalWaitingPopulation() {
        return clientesLevantados.size() + banco.size();
    }

    /**
     * Se há clientes no banco
     */
    public boolean hasBanco() {
        return banco.size() > 0;
    }

    /*
     * Se houver clientes no banco
     *  | Retorna o cliente
     *  | Senão: Retorna null
     */
    public synchronized Cliente requestClient() {
        if (totalWaitingPopulation() > 0) {
            return chamarCliente();
        }

        return null;
    }

    /**
     * Retira o cliente do banco e o retorna;
     * Preenche a vaga vazia do banco
     */
    private synchronized Cliente chamarCliente() {
        Cliente temp = banco.poll();
        preencherBanco();

        return temp;
    }


    
    /**
     * 
     * @param t Tipo de atualização requisitada
     * 
     * Handler para a GUI, categorizado a fim de evitar
     * processamento desnecessaŕio.
     * 
     * Os barbeiros devem ser atualizados de maneira individual
     * uma vez que podem trocar de estado simultâneamente
     * e a GUI não pode lidar com isso
     */
    public void updateGUI(UpdateTypes t) {
        switch (t) {
            case Banco: {
                gui.SetBanco(banco);
                break;
            }
            case Levantados: {
                gui.SetLevatados(clientesLevantados);
                break;
            }
            default: break;
        }
    }

    public void updateGUIBarbeiro( Barbeiro b, int idx ) {
        this.gui.UpdateBarbeiro(b, idx);
    }



    /**
     * 
     * cli stuff
     * 
     */

    /**
     * @deprecated
     * @desc Função utilizada para printar os valores
     *       Depreciada por conta da GUI.
     */
    public synchronized void Display() {
         System.out.println(toString());
    }
    @Override
    public String toString() {

        return "Barbearia{ \n"
                + "\tBarbeiros = " + _getBarbeiros() + "\n"
                + this._line(30)
                + "\tBanco = " + banco.size() + "\n"
                + this._line(30)
                + "\tLevan = " + _getClientesLevantados() + "\n"
                + this._line(30)
                + "\tMaqui = " + maquininha + "\n"
                + "}";
    }

    private String _line(int size) {
        String s = "";
        for (int i = 0; i < size; i++) {
            s += '-';
        }
        return s + '\n';
    }

    private String _getClientesLevantados() {
        StringBuffer s = new StringBuffer();
        s.append('[');

        clientesLevantados.forEach(c -> {
            s.append(c.getNome() + " ");
        });

        s.append(']');

        return s.toString();
    }

    private String _getBarbeiros() {
        StringBuffer s = new StringBuffer();
        s.append("[ ");

        barbeiros.forEach(b -> {
            s.append("| " + b.toString() + " | ");
        });

        s.append(']');

        return s.toString();
    }
}
