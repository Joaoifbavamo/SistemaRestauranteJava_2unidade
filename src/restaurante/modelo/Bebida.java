package restaurante.modelo;

public final class Bebida extends Produto {
    private final int volumeMl;
    private final double adicionalEmbalagem;

    public Bebida(String nome, double preco, int volumeMl, double adicionalEmbalagem) {
        super(nome, preco);
        if (volumeMl <= 0) throw new IllegalArgumentException("Volume deve ser positivo.");
        validarValor(adicionalEmbalagem);
        this.volumeMl = volumeMl;
        this.adicionalEmbalagem = adicionalEmbalagem;
    }
    public int getVolumeMl() { return volumeMl; }
    public double getAdicionalEmbalagem() { return adicionalEmbalagem; }
    @Override public double calcularValor() { return getPreco() + adicionalEmbalagem; }
    @Override public String getDescricao() { return "Bebida: " + getNome() + " (" + volumeMl + " ml)"; }
}
