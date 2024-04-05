package exercicio.src.exercicio;

public class GeradorClientes extends Thread {
    GeradorClientes(Barbearia barbearia) {
        while (true) {
            System.out.println(barbearia.toString());
            barbearia.addCliente(new Cliente());
            try {
                sleep((int)(Math.random() * 5000));
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }
}
