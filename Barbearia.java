package exercicio;

import java.util.LinkedList;
import java.util.Queue;

public class Barbearia {
    private final int QUANTIDADE_MAX_CLIENTES = 20;
    private final int TAMANHO_BANCO = 4;
    private Queue<Cliente> clientesLevantados;
    private Queue<Cliente> banco;
    private Maquininha maquininha;

    public static void main(String[] args) {
        Barbearia barbearia = new Barbearia();
        GeradorClientes geradorClientes = new GeradorClientes(barbearia);

    }

    public Barbearia() {
        this.clientesLevantados = new LinkedList<>();
        this.banco = new LinkedList<>();
    }

    public synchronized void addCliente(Cliente novoCliente) {
        if (populationExceeded()) {
            return;
        }
        clientesLevantados.add(novoCliente);
    }

    private void preencherBanco() {}

    private boolean populationExceeded() {
        return (clientesLevantados.size() + banco.size()) >= QUANTIDADE_MAX_CLIENTES;
    }

    public synchronized Cliente chamarCliente() {
        Cliente temp = banco.poll();
        banco.add(clientesLevantados.poll());
        return temp;
    }

    public synchronized Maquininha pegarMaquinha() {
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
