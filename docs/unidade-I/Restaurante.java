public class Restaurante {
    private Mesa[] mesas;
    private Produto[] produtos;
    private Pedido[] pedidos;
    private int quantidadeMesas;
    private int quantidadeProdutos;
    private int quantidadePedidos;

    public Restaurante() {
        this.mesas = new Mesa[10];
        this.produtos = new Produto[10];
        this.pedidos = new Pedido[10];
        this.quantidadeMesas = 0;
        this.quantidadeProdutos = 0;
        this.quantidadePedidos = 0;
    }

    public void cadastrarMesas(Mesa mesa) {
        if (quantidadeMesas == mesas.length) {
            mesas = aumentarMesas();
        }
        mesas[quantidadeMesas] = mesa;
        quantidadeMesas++;
    }

    private Mesa[] aumentarMesas() {
        Mesa[] novoArray = new Mesa[mesas.length * 2];
        for (int i = 0; i < mesas.length; i++) {
            novoArray[i] = mesas[i];
        }
        return novoArray;
    }

    public void cadastrarProduto(Produto produto) {
        if (quantidadeProdutos == produtos.length) {
            produtos = aumentarProdutos();
        }
        produtos[quantidadeProdutos] = produto;
        quantidadeProdutos++;
    }

    private Produto[] aumentarProdutos() {
        Produto[] novoArray = new Produto[produtos.length * 2];
        for (int i = 0; i < produtos.length; i++) {
            novoArray[i] = produtos[i];
        }
        return novoArray;
    }

    public Pedido abrirPedido(Mesa mesa) {
        if (mesa.isDisponivel() == false) {
            System.out.println("Não foi possível abrir pedido: mesa já está ocupada.");
            return null;
        } else {
            System.out.println("Pedido aberto com sucesso na mesa!");
            Pedido pedido = new Pedido(mesa);
            mesa.ocuparMesa();
            if (quantidadePedidos == pedidos.length) {
                pedidos = aumentarPedidos();
            }
            pedidos[quantidadePedidos] = pedido;
            quantidadePedidos++;
            return pedido;
        }
    }

    private Pedido[] aumentarPedidos() {
        Pedido[] novoArray = new Pedido[pedidos.length * 2];
        for (int i = 0; i < pedidos.length; i++) {
            novoArray[i] = pedidos[i];
        }
        return novoArray;
    }

    public Mesa[] consultarMesasDisponiveis() {
        Mesa[] resultado = new Mesa[quantidadeMesas];
        int contador = 0;
        for (int i = 0; i < quantidadeMesas; i++) {
            if (mesas[i].isDisponivel()) {
                System.out.println("Mesa " + mesas[i].getNumero() + " está disponível.");
                resultado[contador] = mesas[i];
                contador++;
            }
        }
        Mesa[] resultadoFinal = new Mesa[contador];
        for (int i = 0; i < contador; i++) {
            resultadoFinal[i] = resultado[i];
        }
        return resultadoFinal;
    }
}
