public class Main {
    public static void main(String[] args) {
        Restaurante novoRestaurante = new Restaurante();

        Mesa mesa1 = new Mesa(1, 4);
        Mesa mesa2 = new Mesa(2, 4);
        novoRestaurante.cadastrarMesas(mesa1);
        novoRestaurante.cadastrarMesas(mesa2);

        Produto produto1 = new Produto("Produto Genérico", 20.0);
        Produto produto2 = new Produto("Produto Genérico 2", 15.0);
        novoRestaurante.cadastrarProduto(produto1);
        novoRestaurante.cadastrarProduto(produto2);

        System.out.println("----- Abrindo pedido na mesa 1 -----");
        Pedido pedido1 = novoRestaurante.abrirPedido(mesa1);

        System.out.println("----- Adicionando itens ao pedido -----");
        pedido1.adicionarProduto(produto1, 5);
        pedido1.adicionarProduto(produto2, 2);

        System.out.println("----- Calculando total (com for) -----");
        double totalCalculado = pedido1.calcularTotal();
        System.out.println("Total: " + totalCalculado);

        System.out.println("----- Calculando total (recursivo) -----");
        double totalRecursivo = pedido1.calcularTotalRecursivo();
        System.out.println("Total recursivo: " + totalRecursivo);

        System.out.println("----- Testando regra: mesa já ocupada -----");
        Pedido pedidoTeste = novoRestaurante.abrirPedido(mesa1);
        System.out.println("Resultado (esperado: null): " + pedidoTeste);

        System.out.println("----- Consultando mesas disponíveis (mesa1 ocupada, mesa2 livre) -----");
        novoRestaurante.consultarMesasDisponiveis();

        System.out.println("----- Finalizando pedido da mesa 1 -----");
        pedido1.finalizarPedido();

        System.out.println("----- Testando: mesa 1 liberada, deve aceitar novo pedido -----");
        Pedido pedidoTeste2 = novoRestaurante.abrirPedido(mesa1);
        System.out.println("Resultado (esperado: objeto Pedido, não null): " + pedidoTeste2);
    }
}
