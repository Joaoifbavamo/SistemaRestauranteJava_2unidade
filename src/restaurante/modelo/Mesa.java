package restaurante.modelo;

public class Mesa {
    private final int numero;
    private final int capacidade;
    private boolean disponivel = true;

    public Mesa(int numero, int capacidade) {
        if (numero <= 0 || capacidade <= 0) throw new IllegalArgumentException("Numero e capacidade devem ser positivos.");
        this.numero = numero;
        this.capacidade = capacidade;
    }
    public int getNumero() { return numero; }
    public int getCapacidade() { return capacidade; }
    public boolean isDisponivel() { return disponivel; }
    void ocuparMesa() {
        if (!disponivel) throw new IllegalStateException("Mesa ja ocupada.");
        disponivel = false;
    }
    void liberarMesa() { disponivel = true; }
}
