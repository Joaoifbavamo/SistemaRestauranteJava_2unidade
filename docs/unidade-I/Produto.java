public class Produto {
    private String nome;
    private double preco;
    private boolean disponivel;

    public Produto(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
        this.disponivel = true;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void marcarIndisponivel() {
        disponivel = false;
    }

    public void marcarDisponivel() {
        disponivel = true;
    }
}
