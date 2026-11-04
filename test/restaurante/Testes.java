package restaurante;

import restaurante.contrato.Calculavel;
import restaurante.modelo.*;
import restaurante.servico.Restaurante;

/** Testes executaveis com Java 20, sem dependencias externas. */
public final class Testes {
    private static int verificacoes;
    public static void main(String[] args) {
        Produto prato = new Prato("Executivo", 25, 5);
        Produto bebida = new Bebida("Suco", 8, 300, 2);
        Produto sobremesa = new Sobremesa("Brownie", 12, 2, 3);
        Calculavel[] calculaveis = {prato, bebida, sobremesa};
        double[] esperados = {30, 10, 18};
        for (int i = 0; i < calculaveis.length; i++) igual(esperados[i], calculaveis[i].calcularValor());
        Restaurante restaurante = new Restaurante();
        Mesa mesa = new Mesa(1, 4);
        restaurante.cadastrarMesas(mesa);
        restaurante.cadastrarProduto(prato);
        restaurante.cadastrarProduto(bebida);
        restaurante.cadastrarProduto(sobremesa);
        falha(IllegalArgumentException.class, () -> restaurante.cadastrarMesas(new Mesa(1, 2)));
        falha(IllegalArgumentException.class, () -> restaurante.abrirPedido(new Mesa(99, 2)));
        Pedido pedido = restaurante.abrirPedido(mesa);
        igual(0, pedido.calcularValor());
        falha(IllegalStateException.class, pedido::finalizarPedido);
        falha(IllegalStateException.class, () -> restaurante.abrirPedido(mesa));
        falha(IllegalStateException.class, () -> new Pedido(mesa));
        pedido.adicionarProduto(prato, 2);
        pedido.adicionarProduto(bebida, 1);
        pedido.adicionarProduto(sobremesa, 1);
        igual(88, pedido.calcularValor());
        igual(88, pedido.calcularTotal());
        igual(60, pedido.getItens()[0].calcularSubtotal());
        falha(IllegalArgumentException.class, () -> pedido.adicionarProduto(prato, 0));
        falha(IllegalArgumentException.class, () -> pedido.adicionarProduto(prato, -1));
        bebida.marcarIndisponivel();
        falha(IllegalStateException.class, () -> pedido.adicionarProduto(bebida, 1));
        bebida.marcarDisponivel();
        pedido.getItens()[0] = null;
        restaurante.getMesas()[0] = null;
        igual(88, pedido.calcularValor());
        pedido.finalizarPedido();
        confere(mesa.isDisponivel(), "Mesa liberada");
        falha(IllegalStateException.class, () -> pedido.adicionarProduto(prato, 1));
        Pedido novo = restaurante.abrirPedido(mesa);
        falha(IllegalStateException.class, pedido::finalizarPedido);
        confere(!mesa.isDisponivel(), "Finalizacao repetida nao libera mesa de outro pedido");
        novo.adicionarProduto(bebida, 1);
        novo.finalizarPedido();
        igual(1, restaurante.consultarMesasDisponiveis().length);
        for (int i = 2; i <= 25; i++) restaurante.cadastrarMesas(new Mesa(i, 2));
        for (int i = 0; i < 25; i++) restaurante.cadastrarProduto(new Prato("Prato " + i, 1, 0));
        igual(25, restaurante.getMesas().length);
        igual(28, restaurante.getProdutos().length);
        for (int i = 0; i < 25; i++) {
            Pedido p = restaurante.abrirPedido(mesa);
            for (int j = 0; j < 25; j++) p.adicionarProduto(bebida, 2);
            igual(500, p.calcularValor());
            igual(p.calcularTotal(), p.calcularTotalRecursivo());
            igual(25, p.getItens().length);
            p.finalizarPedido();
        }
        igual(27, restaurante.getPedidos().length);
        falha(IllegalArgumentException.class, () -> new Prato("", 1, 0));
        falha(IllegalArgumentException.class, () -> new Prato("P", -1, 0));
        falha(IllegalArgumentException.class, () -> new Prato("P", Double.NaN, 0));
        falha(IllegalArgumentException.class, () -> new Prato("P", Double.POSITIVE_INFINITY, 0));
        falha(IllegalArgumentException.class, () -> new Bebida("B", 1, 0, 0));
        falha(IllegalArgumentException.class, () -> new Sobremesa("S", 1, -1, 2));
        falha(IllegalArgumentException.class, () -> new Mesa(0, 2));
        igual(0.3, new Prato("Decimal", 0.1, 0.2).calcularValor());
        System.out.println("SUCESSO: " + verificacoes + " verificacoes.");
    }
    private static void igual(double esperado, double obtido) {
        confere(Double.isFinite(obtido) && Math.abs(esperado - obtido) < 0.000001, esperado + " != " + obtido);
    }
    private static void confere(boolean condicao, String mensagem) {
        verificacoes++;
        if (!condicao) throw new AssertionError(mensagem);
    }
    private static void falha(Class<? extends RuntimeException> tipo, Runnable acao) {
        verificacoes++;
        try { acao.run(); }
        catch (RuntimeException e) {
            if (tipo.isInstance(e)) return;
            throw new AssertionError("Excecao inesperada", e);
        }
        throw new AssertionError("Esperada: " + tipo.getSimpleName());
    }
}
