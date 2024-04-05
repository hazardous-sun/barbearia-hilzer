package exercicio;

public class Cliente extends Thread {
    private long horaChegada;

    Cliente() {
        this.horaChegada = System.nanoTime();
    }
}
