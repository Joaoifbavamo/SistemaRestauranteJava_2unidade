package restaurante.modelo;

import restaurante.contrato.Calculavel;

public abstract class Produto implements Calculavel {
    private final String nome;
    private final double preco;
    private boolean disponivel = true;

    protected Produto(String nome, double preco) {
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome obrigatorio.");
        validarValor(preco);
        this.nome = nome.trim();
        this.preco = preco;
    }

    protected static void validarValor(double valor) {
        if (!Double.isFinite(valor) || valor < 0) {
            throw new IllegalArgumentException("O valor deve ser finito e nao negativo.");
        }
    }

    public String getNome() { return nome; }
    public double getPreco() { return preco; }
    public boolean isDisponivel() { return disponivel; }
    public void marcarIndisponivel() { disponivel = false; }
    public void marcarDisponivel() { disponivel = true; }
    public abstract String getDescricao();
    @Override public abstract double calcularValor();
}
