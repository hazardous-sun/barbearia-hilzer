package exercicio;

public class Maquininha extends Thread {
    public synchronized void cobrarCliente(Barbeiro barbeiro, Cliente cliente) {
        System.out.println("Cliente sendo cobrado.");
    }
}
