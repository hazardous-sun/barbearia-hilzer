import java.util.concurrent.Semaphore;

public class Maquininha extends Thread {

    private Semaphore sem;

    Maquininha () {
        sem = new Semaphore(1);
    }

    public synchronized void cobrarCliente(Cliente cliente) {
        System.out.println("Cliente sendo cobrado");
    
        try {
            sleep( Utils.RandomIntN(2, 10) * 1_000 );
        } 
        catch (InterruptedException e) {
            System.out.println(e);
        }
    }

    public synchronized Maquininha getMaquininha() {

        while ( ! sem.tryAcquire() ) {
            try {
                this.wait();
            }
            catch ( InterruptedException e ) {
                System.out.println(e.getMessage());
            }
        }

        return this;
    }

    public  void returnMaquininha() {
        sem.release();
        this.notify();
    }


    @Override
    public void run() {
        while (true) {
            // try {
                // sleep(10_000);
            // } catch (InterruptedException e) {
                // System.out.println(e);
            // }
        }
    }
}
