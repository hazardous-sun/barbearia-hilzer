
public class GeradorClientes extends Thread {

    GeradorClientes(Barbearia barbearia) {
        run(barbearia);
    }

    public void run(Barbearia barbearia) {

        while (true) {
            barbearia.addCliente(new Cliente());

            try {
                sleep(Utils.RandomIntN(1, 10) * 500);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }
}
