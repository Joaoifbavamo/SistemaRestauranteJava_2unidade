package restaurante.modelo;

import java.util.Objects;
import restaurante.contrato.Calculavel;

public final class ItemPedido implements Calculavel {
    private final Produto produto;
    private final int quantidade;

    public ItemPedido(Produto produto, int quantidade) {
        this.produto = Objects.requireNonNull(produto, "Produto obrigatorio.");
        if (quantidade <= 0) throw new IllegalArgumentException("Quantidade deve ser positiva.");
        this.quantidade = quantidade;
    }
    public Produto getProduto() { return produto; }
    public int getQuantidade() { return quantidade; }
    public double calcularSubtotal() { return calcularValor(); }
    @Override public double calcularValor() {
        // Despacho dinamico: a subclasse do produto define seu preco de venda.
        return produto.calcularValor() * quantidade;
    }
}
