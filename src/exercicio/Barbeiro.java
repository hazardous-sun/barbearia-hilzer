package exercicio.src.exercicio;
public class Barbeiro extends Thread {
    public void atenderCliente(Cliente cliente) {
        System.out.println("Cliente sendo atendido");
        try {
            sleep((int) (Math.random() * Math.random()));
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }

//    public void run(Barbearia barbearia) {
//        atenderCliente();
//    }
}
