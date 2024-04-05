package exercicio.src.exercicio;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Barbearia {
    private final int QUANTIDADE_MAX_CLIENTES = 20;
    private final int QUANTIDADE_MAX_BARBEIROS = 3;
    private final int TAMANHO_BANCO = 4;
    private Queue<Cliente> clientesLevantados;
    private Queue<Cliente> banco;
    private Maquininha maquininha;

    public static void main(String[] args) {
        Barbearia barbearia = new Barbearia();

        GeradorClientes geradorClientes = new GeradorClientes(barbearia);
        geradorClientes.start();

        ArrayList<Barbeiro> barbeiros = new ArrayList<>();

        for (int i = 0; i < barbearia.QUANTIDADE_MAX_BARBEIROS; i++) {
            barbeiros.add(new Barbeiro(barbearia));
        }

        for (int i = 0; i < barbearia.QUANTIDADE_MAX_BARBEIROS; i++) {
            barbeiros.get(i).start();
        }
    }

    public Barbearia() {
        this.clientesLevantados = new LinkedList<>();
        this.banco = new LinkedList<>();
        this.maquininha = new Maquininha();
    }

    public synchronized void addCliente(Cliente novoCliente) {
        if (populationExceeded()) {
            return;
        }
        clientesLevantados.add(novoCliente);
        preencherBanco();
    }

    private void preencherBanco() {
        while (banco.size() < TAMANHO_BANCO && !clientesLevantados.isEmpty()) {
            banco.add(clientesLevantados.poll());
        }
    }

    private boolean populationExceeded() {
        return totalPopulation() >= QUANTIDADE_MAX_CLIENTES;
    }

    public int totalPopulation() {
        return clientesLevantados.size() + banco.size();
    }

    public synchronized Cliente chamarCliente() {
        Cliente temp = banco.poll();
        preencherBanco();
        return temp;
    }

    public synchronized Maquininha pegarMaquinha(Cliente cliente) {
        maquininha.cobrarCliente(cliente);
        return this.maquininha;
    }

    @Override
    public String toString() {
        return "Barbearia{" +
                "QUANTIDADE_MAX_CLIENTES=" + QUANTIDADE_MAX_CLIENTES +
                ", TAMANHO_BANCO=" + TAMANHO_BANCO +
                ", clientesLevantados=" + clientesLevantados +
                ", banco=" + banco +
                ", maquininha=" + maquininha +
                '}';
    }
}
