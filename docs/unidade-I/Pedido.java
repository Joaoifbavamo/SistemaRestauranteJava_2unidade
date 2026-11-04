public class Pedido {
    private Mesa mesa;
    private ItemPedido[] itens;
    private int quantidadeItens;
    private boolean finalizado;

    public Pedido(Mesa mesa) {
        this.mesa = mesa;
        this.itens = new ItemPedido[10];
        this.quantidadeItens = 0;
        this.finalizado = false;
    }

    public void adicionarProduto(Produto produto, int quantidade) {
        if (produto.isDisponivel() == false) {
            System.out.println("Não foi possível adicionar: produto indisponível.");
        } else {
            if (quantidadeItens == itens.length) {
                aumentarCapacidade();
            }
            itens[quantidadeItens] = new ItemPedido(produto, quantidade);
            quantidadeItens++;
            System.out.println("Produto adicionado ao pedido com sucesso!");
        }
    }

    private void aumentarCapacidade() {
        ItemPedido[] novoArray = new ItemPedido[itens.length * 2];
        for (int i = 0; i < itens.length; i++) {
            novoArray[i] = itens[i];
        }
        itens = novoArray;
    }

    public double calcularTotal() {
        double total = 0.0;
        for (int i = 0; i < quantidadeItens; i++) {
            total += itens[i].calcularSubtotal();
        }
        return total;
    }

    public double calcularTotalRecursivo(ItemPedido[] lista, int indice) {
        if (indice >= quantidadeItens) {
            return 0.0;
        } else {
            return lista[indice].calcularSubtotal() + calcularTotalRecursivo(lista, indice + 1);
        }
    }

    public double calcularTotalRecursivo() {
        return calcularTotalRecursivo(itens, 0);
    }

    public ItemPedido[] getItens() {
        return itens;
    }

    public int getQuantidadeItens() {
        return quantidadeItens;
    }

    public void finalizarPedido() {
        if (quantidadeItens == 0) {
            System.out.println("Não foi possível finalizar: pedido não possui itens.");
        } else {
            finalizado = true;
            mesa.liberarMesa();
            System.out.println("Pedido finalizado com sucesso! Mesa liberada.");
        }
    }
}
