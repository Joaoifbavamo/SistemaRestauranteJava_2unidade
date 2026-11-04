package restaurante.servico;

import java.util.Arrays;
import java.util.Objects;
import restaurante.modelo.*;

/** Gerencia os cadastros em arrays nativos com crescimento automatico. */
public class Restaurante {
    private Mesa[] mesas = new Mesa[10];
    private Produto[] produtos = new Produto[10];
    private Pedido[] pedidos = new Pedido[10];
    private int quantidadeMesas;
    private int quantidadeProdutos;
    private int quantidadePedidos;

    public void cadastrarMesas(Mesa mesa) {
        Objects.requireNonNull(mesa, "Mesa obrigatoria.");
        for (int i = 0; i < quantidadeMesas; i++) {
            if (mesas[i].getNumero() == mesa.getNumero()) throw new IllegalArgumentException("Numero de mesa ja cadastrado.");
        }
        if (quantidadeMesas == mesas.length) mesas = Arrays.copyOf(mesas, mesas.length * 2);
        mesas[quantidadeMesas++] = mesa;
    }
    public void cadastrarProduto(Produto produto) {
        Objects.requireNonNull(produto, "Produto obrigatorio.");
        for (int i = 0; i < quantidadeProdutos; i++) {
            if (produtos[i] == produto) throw new IllegalArgumentException("Produto ja cadastrado.");
        }
        if (quantidadeProdutos == produtos.length) produtos = Arrays.copyOf(produtos, produtos.length * 2);
        produtos[quantidadeProdutos++] = produto;
    }
    public Pedido abrirPedido(Mesa mesa) { return abrirPedido(mesa, null); }
    public Pedido abrirPedido(Mesa mesa, Cliente cliente) {
        Objects.requireNonNull(mesa, "Mesa obrigatoria.");
        boolean cadastrada = false;
        for (int i = 0; i < quantidadeMesas; i++) if (mesas[i] == mesa) cadastrada = true;
        if (!cadastrada) throw new IllegalArgumentException("Mesa nao cadastrada.");
        if (!mesa.isDisponivel()) throw new IllegalStateException("Mesa ja ocupada.");
        if (quantidadePedidos == pedidos.length) pedidos = Arrays.copyOf(pedidos, pedidos.length * 2);
        Pedido pedido = new Pedido(mesa, cliente);
        pedidos[quantidadePedidos++] = pedido;
        return pedido;
    }
    public Mesa[] consultarMesasDisponiveis() {
        Mesa[] livres = new Mesa[quantidadeMesas];
        int contador = 0;
        for (int i = 0; i < quantidadeMesas; i++) if (mesas[i].isDisponivel()) livres[contador++] = mesas[i];
        return Arrays.copyOf(livres, contador);
    }
    public Mesa[] getMesas() { return Arrays.copyOf(mesas, quantidadeMesas); }
    public Produto[] getProdutos() { return Arrays.copyOf(produtos, quantidadeProdutos); }
    public Pedido[] getPedidos() { return Arrays.copyOf(pedidos, quantidadePedidos); }
}
