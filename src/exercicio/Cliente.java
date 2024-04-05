package exercicio.src.exercicio;

public class Cliente extends Thread {
    private long horaChegada;

    Cliente() {
        this.horaChegada = System.nanoTime();
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
