package restaurante.modelo;

public final class Sobremesa extends Produto {
    private final int bolasExtras;
    private final double precoBolaExtra;

    public Sobremesa(String nome, double preco, int bolasExtras, double precoBolaExtra) {
        super(nome, preco);
        if (bolasExtras < 0) throw new IllegalArgumentException("Quantidade de bolas invalida.");
        validarValor(precoBolaExtra);
        this.bolasExtras = bolasExtras;
        this.precoBolaExtra = precoBolaExtra;
    }
    public int getBolasExtras() { return bolasExtras; }
    public double getPrecoBolaExtra() { return precoBolaExtra; }
    @Override public double calcularValor() { return getPreco() + bolasExtras * precoBolaExtra; }
    @Override public String getDescricao() { return "Sobremesa: " + getNome() + " (" + bolasExtras + " bolas extras)"; }
}
