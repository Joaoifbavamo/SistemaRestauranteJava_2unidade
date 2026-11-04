public class Mesa {
    private int numero;
    private int capacidade;
    private boolean disponivel;

    public Mesa(int numero, int capacidade) {
        this.numero = numero;
        this.capacidade = capacidade;
        this.disponivel = true;
    }

    public int getNumero() {
        return numero;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void ocuparMesa() {
        disponivel = false;
    }

    public void liberarMesa() {
        disponivel = true;
    }

    public boolean isDisponivel() {
        return disponivel;
    }
}
