package restaurante.modelo;

public final class Prato extends Produto {
    private final double adicionalAcompanhamento;

    public Prato(String nome, double preco, double adicionalAcompanhamento) {
        super(nome, preco);
        validarValor(adicionalAcompanhamento);
        this.adicionalAcompanhamento = adicionalAcompanhamento;
    }
    public double getAdicionalAcompanhamento() { return adicionalAcompanhamento; }
    @Override public double calcularValor() { return getPreco() + adicionalAcompanhamento; }
    @Override public String getDescricao() { return "Prato: " + getNome() + " (acompanhamento incluso no valor)"; }
}
