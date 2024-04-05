package exercicio.src.exercicio;

public class Maquininha extends Thread {
    public synchronized void cobrarCliente(Cliente cliente) {
        System.out.println("Cliente sendo cobrado.");
        try {
            sleep((int) (Math.random() * Math.random()));
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                sleep(10000);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }
}
