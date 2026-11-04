package restaurante.modelo;

import java.util.Arrays;
import java.util.Objects;
import restaurante.contrato.Calculavel;

public class Pedido implements Calculavel {
    private final Mesa mesa;
    private final Cliente cliente;
    private ItemPedido[] itens = new ItemPedido[10];
    private int quantidadeItens;
    private boolean finalizado;

    public Pedido(Mesa mesa) { this(mesa, null); }
    public Pedido(Mesa mesa, Cliente cliente) {
        this.mesa = Objects.requireNonNull(mesa, "Mesa obrigatoria.");
        this.cliente = cliente;
        mesa.ocuparMesa();
    }
    public void adicionarProduto(Produto produto, int quantidade) {
        if (finalizado) throw new IllegalStateException("Pedido ja finalizado.");
        Objects.requireNonNull(produto, "Produto obrigatorio.");
        if (!produto.isDisponivel()) throw new IllegalStateException("Produto indisponivel.");
        ItemPedido item = new ItemPedido(produto, quantidade);
        if (quantidadeItens == itens.length) itens = Arrays.copyOf(itens, itens.length * 2);
        itens[quantidadeItens++] = item;
    }
    public double calcularTotal() {
        double total = 0;
        for (int i = 0; i < quantidadeItens; i++) total += itens[i].calcularSubtotal();
        return total;
    }
    public double calcularTotalRecursivo() { return somarItens(0); }
    private double somarItens(int indice) {
        if (indice >= quantidadeItens) return 0.0;
        return itens[indice].calcularSubtotal() + somarItens(indice + 1);
    }
    @Override public double calcularValor() { return calcularTotalRecursivo(); }
    public void finalizarPedido() {
        if (finalizado) throw new IllegalStateException("Pedido ja finalizado.");
        if (quantidadeItens == 0) throw new IllegalStateException("Pedido nao possui itens.");
        finalizado = true;
        mesa.liberarMesa();
    }
    public ItemPedido[] getItens() { return Arrays.copyOf(itens, quantidadeItens); }
    public int getQuantidadeItens() { return quantidadeItens; }
    public Mesa getMesa() { return mesa; }
    public Cliente getCliente() { return cliente; }
    public boolean isFinalizado() { return finalizado; }
}
