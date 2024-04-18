public class Cliente extends Thread {
    public static int TotalClientes = 0;

    private long horaChegada;
    private int nome;

    Cliente() {
        this.horaChegada = System.nanoTime();
        this.nome = ++Cliente.TotalClientes;
    }

    public int getNome() {
        return this.nome;
    }

    @Override
    public void run() {
        while (true) {
            try {
                sleep(10_000);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }
}
