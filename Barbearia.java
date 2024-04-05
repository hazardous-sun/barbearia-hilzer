package exercicio;

import java.util.ArrayList;

public class Barbearia {
    private ArrayList<Cliente> clientesDentro;
    private ArrayList<Cliente> banco;

    public Barbearia() {
        this.clientesDentro = new ArrayList<>();
    }

    public synchronized void addCliente(Cliente novoCliente) {
        if (populationExceeded()) {
            return;
        }
        clientesDentro.add(novoCliente);
    }

    private boolean populationExceeded() {
        return clientesDentro.size() >= 20;
    }
}
