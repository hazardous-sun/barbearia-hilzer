package exercicio;

public class Cliente extends Thread {
    private long horaChegada;

    Cliente(Barbearia barbearia) {
        this.horaChegada = System.nanoTime();
    }
}
