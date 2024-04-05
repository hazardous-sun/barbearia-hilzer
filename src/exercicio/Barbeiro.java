package exercicio.src.exercicio;
public class Barbeiro extends Thread {
    private void atenderCliente(Barbearia barbearia) {
        while (barbearia.totalPopulation() > 0) {
            Cliente cliente = barbearia.chamarCliente();
            System.out.println("Cliente sendo atendido");
            try {
                sleep((int) (Math.random() * Math.random()));
            } catch (InterruptedException e) {
                System.out.println(e);
            }
            barbearia.pegarMaquinha(cliente);
        }
    }

    public void run(Barbearia barbearia) {
        atenderCliente(barbearia);
    }
}
