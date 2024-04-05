package exercicio.src.exercicio;

public class GeradorClientes extends Thread {
    GeradorClientes(Barbearia barbearia) {
        run(barbearia);
    }

    public void run(Barbearia barbearia) {
        while (true) {
            barbearia.addCliente(new Cliente());
            try {
                sleep((int)(Math.random() * 5000));
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }
}
