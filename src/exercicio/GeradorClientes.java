
public class GeradorClientes extends Thread {

    private final int MAX = 5;
    private static int Generated = 0; 

    GeradorClientes(Barbearia barbearia) {
        run(barbearia);
    }

    public void run(Barbearia barbearia) {

         while ( true ) {
            //  System.out.println("Novo cliente! "); 
             barbearia.addCliente(new Cliente());
          
             try {
                 sleep(Utils.RandomIntN(1, 10) * 500);
             } catch (InterruptedException e) {
                 System.out.println(e);
             }
         }
    }
}
