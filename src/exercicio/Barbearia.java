package exercicio.src.exercicio;
import java.io.BufferedWriter;
import java.io.File;
import java.nio.file.FileStore;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Barbearia {
    private final int QUANTIDADE_MAX_CLIENTES = 20;
    private final int QUANTIDADE_MAX_BARBEIROS = 3;
    private final int TAMANHO_BANCO = 4;

    private Queue<Cliente> clientesLevantados;
    private Queue<Cliente> banco;

    private ArrayList<Barbeiro> barbeiros;


    private Maquininha maquininha;

    public static void main(String[] args) {
        Barbearia barbearia = new Barbearia();
        
        for (int i = 0; i < barbearia.QUANTIDADE_MAX_BARBEIROS; i++) {
            barbearia.barbeiros.add(new Barbeiro(barbearia));
        }
        
        for (int i = 0; i < barbearia.QUANTIDADE_MAX_BARBEIROS; i++) {
            barbearia.barbeiros.get(i).start();
        }

        GeradorClientes geradorClientes = new GeradorClientes(barbearia);
        geradorClientes.start();
    }

    public Barbearia() {
        this.clientesLevantados = new LinkedList<>();
        this.banco = new LinkedList<>();
        this.barbeiros = new ArrayList<>();
        this.maquininha = new Maquininha();
    }

    public synchronized void addCliente(Cliente novoCliente) {
        if ( populationExceeded() ) {
            return;
        }

        clientesLevantados.add(novoCliente);

        preencherBanco();

        this.Display();

        acordaBarbeiros();
    }

    private void acordaBarbeiros() {

        // tem que ajeitar essa bosta
        // é apenas um prototipo
        for (Barbeiro b : barbeiros) {

            synchronized (b) {
                // System.out.println("barb: " + b.getNome() + ": " + b.getState());
                if (b.getBarbeiroState() == BarbeirosState.DORMINDO) {
                    b.notify();
                }
            }
        }
    }

    // Prototipo pra resolver o problema
    public void barbeiroFinalizou ( Barbeiro b ) {
        
        synchronized (maquininha) {
            maquininha.returnMaquininha();
        }

        if ( ! hasBanco() ) {
            synchronized (b) {
                b.dormir();
            }
        }
    }

    private void preencherBanco() {
        while (banco.size() < TAMANHO_BANCO && !clientesLevantados.isEmpty()) {
            banco.add(clientesLevantados.poll());
        }
    }

    private boolean populationExceeded() {
        return totalPopulation() >= QUANTIDADE_MAX_CLIENTES;
    }
    
    public synchronized int totalPopulation() {
        return clientesLevantados.size() + banco.size();
    }

    public boolean hasBanco() {
        return banco.size() > 0;
    }

    public synchronized Cliente requestClient(  ) {
        if ( totalPopulation() > 0 ) {
            return chamarCliente();
        }

        return null;
    }

    public synchronized Cliente chamarCliente() {
        Cliente temp = banco.poll();
        preencherBanco();
        
        return temp;
    }

    public synchronized Maquininha requestPOS() {
        return maquininha.getMaquininha();
    }

    public void Display() {
        System.out.println(toString());
    }


    /**
     * 
     * cli stuff
     * 
     */

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
            s+='-';
        }
        return s+'\n';
    }
    private String _getClientesLevantados() {
        StringBuffer s = new StringBuffer();
        s.append('[');

        clientesLevantados.forEach( c -> {
            s.append(c.getNome() + " ");
        });

        s.append(']');

        return s.toString();
    }

    private String _getBarbeiros() {
        StringBuffer s = new StringBuffer();
        s.append("[ ");

        barbeiros.forEach( b -> {
            s.append("| " + b.toString() + " | ");
        });

        s.append(']');

        return s.toString();
    }
}
