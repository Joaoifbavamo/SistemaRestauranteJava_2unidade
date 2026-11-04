package restaurante.app;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;
import restaurante.contrato.Calculavel;
import restaurante.modelo.*;
import restaurante.servico.Restaurante;

public final class Main {
    private static final NumberFormat MOEDA = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR"));
    private final Restaurante restaurante = new Restaurante();
    private final Scanner entrada = new Scanner(System.in);

    private static String dinheiro(double valor) {
        return MOEDA.format(valor).replace('\u00a0', ' ');
    }

    public static void main(String[] args) {
        if (args.length == 1 && args[0].equals("--demo")) demonstrar();
        else new Main().menu();
    }

    private void menu() {
        System.out.println("SISTEMA DE RESTAURANTE - UNIDADE II - JAVA 20");
        while (true) {
            System.out.println("\n1 Cadastrar mesa | 2 Cadastrar produto | 3 Ver cardapio");
            System.out.println("4 Abrir pedido | 5 Adicionar item | 6 Ver conta | 7 Finalizar pedido");
            System.out.println("8 Mesas livres | 9 Disponibilidade de produto | 0 Sair");
            try {
                switch (inteiro("Opcao: ")) {
                    case 0 -> { return; }
                    case 1 -> restaurante.cadastrarMesas(new Mesa(inteiro("Numero: "), inteiro("Capacidade: ")));
                    case 2 -> cadastrarProduto();
                    case 3 -> cardapio();
                    case 4 -> {
                        Mesa mesa = escolherMesa();
                        String nome = texto("Cliente (vazio para nao informar): ");
                        Cliente cliente = nome.isBlank() ? null : new Cliente(nome, texto("CPF: "), texto("Telefone: "));
                        restaurante.abrirPedido(mesa, cliente);
                        System.out.println("Pedido aberto.");
                    }
                    case 5 -> {
                        Pedido pedido = escolherPedido();
                        cardapio();
                        Produto produto = selecionar(restaurante.getProdutos(), inteiro("Produto: "));
                        pedido.adicionarProduto(produto, inteiro("Quantidade: "));
                        System.out.println("Item adicionado.");
                    }
                    case 6 -> conta(escolherPedido());
                    case 7 -> {
                        Pedido pedido = escolherPedido();
                        conta(pedido);
                        pedido.finalizarPedido();
                        System.out.println("Pedido finalizado. Mesa liberada.");
                    }
                    case 8 -> {
                        Mesa[] livres = restaurante.consultarMesasDisponiveis();
                        if (livres.length == 0) System.out.println("Nenhuma mesa livre.");
                        for (Mesa mesa : livres) System.out.println("Mesa " + mesa.getNumero() + " - " + mesa.getCapacidade() + " lugares");
                    }
                    case 9 -> {
                        cardapio();
                        Produto produto = selecionar(restaurante.getProdutos(), inteiro("Produto: "));
                        int opcao = inteiro("1 Disponivel | 2 Indisponivel: ");
                        if (opcao == 1) produto.marcarDisponivel();
                        else if (opcao == 2) produto.marcarIndisponivel();
                        else throw new IllegalArgumentException("Opcao invalida.");
                    }
                    default -> System.out.println("Opcao invalida.");
                }
            } catch (FimEntrada e) {
                System.out.println("\nEntrada encerrada.");
                return;
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Nao foi possivel: " + e.getMessage());
            }
        }
    }

    private void cadastrarProduto() {
        int categoria = inteiro("1 Prato | 2 Bebida | 3 Sobremesa: ");
        String nome = texto("Nome: ");
        double preco = decimal("Preco base: ");
        Produto produto = switch (categoria) {
            case 1 -> new Prato(nome, preco, decimal("Adicional de acompanhamento (0 se nenhum): "));
            case 2 -> new Bebida(nome, preco, inteiro("Volume em ml: "), decimal("Adicional de embalagem (0 se nenhum): "));
            case 3 -> new Sobremesa(nome, preco, inteiro("Bolas extras (0 se nenhuma): "), decimal("Preco de cada bola extra: "));
            default -> throw new IllegalArgumentException("Categoria invalida.");
        };
        restaurante.cadastrarProduto(produto);
        System.out.println("Produto cadastrado.");
    }
    private void cardapio() {
        Produto[] produtos = restaurante.getProdutos();
        if (produtos.length == 0) System.out.println("Cardapio vazio.");
        for (int i = 0; i < produtos.length; i++) {
            Produto produto = produtos[i];
            System.out.println((i + 1) + " " + produto.getDescricao() + " - " + dinheiro(produto.calcularValor())
                    + (produto.isDisponivel() ? "" : " [indisponivel]"));
        }
    }
    private Mesa escolherMesa() {
        for (Mesa mesa : restaurante.getMesas()) System.out.println("Mesa " + mesa.getNumero() + (mesa.isDisponivel() ? " livre" : " ocupada"));
        int numero = inteiro("Numero da mesa: ");
        for (Mesa mesa : restaurante.getMesas()) if (mesa.getNumero() == numero) return mesa;
        throw new IllegalArgumentException("Mesa nao encontrada.");
    }
    private Pedido escolherPedido() {
        Pedido[] pedidos = restaurante.getPedidos();
        for (int i = 0; i < pedidos.length; i++) System.out.println((i + 1) + " Mesa " + pedidos[i].getMesa().getNumero()
                + (pedidos[i].isFinalizado() ? " [finalizado]" : " [aberto]"));
        return selecionar(pedidos, inteiro("Pedido: "));
    }
    private static <T> T selecionar(T[] valores, int numero) {
        if (numero < 1 || numero > valores.length) throw new IllegalArgumentException("Numero nao encontrado.");
        return valores[numero - 1];
    }
    private String texto(String pergunta) {
        System.out.print(pergunta);
        if (!entrada.hasNextLine()) throw new FimEntrada();
        return entrada.nextLine().trim();
    }
    private int inteiro(String pergunta) {
        try { return Integer.parseInt(texto(pergunta)); }
        catch (NumberFormatException e) { throw new IllegalArgumentException("Informe um numero inteiro."); }
    }
    private double decimal(String pergunta) {
        try { return Double.parseDouble(texto(pergunta).replace(',', '.')); }
        catch (NumberFormatException e) { throw new IllegalArgumentException("Informe um numero decimal."); }
    }
    private static final class FimEntrada extends RuntimeException { private static final long serialVersionUID = 1L; }

    public static void conta(Pedido pedido) {
        System.out.println("\nCONTA - MESA " + pedido.getMesa().getNumero());
        if (pedido.getCliente() != null) System.out.println("Cliente: " + pedido.getCliente().getNome());
        for (ItemPedido item : pedido.getItens()) {
            System.out.println(item.getQuantidade() + " x " + item.getProduto().getDescricao() + " = " + dinheiro(item.calcularSubtotal()));
        }
        Calculavel calculavel = pedido;
        System.out.println("Total recursivo: " + dinheiro(calculavel.calcularValor()));
        System.out.println("Total iterativo: " + dinheiro(pedido.calcularTotal()));
    }

    private static void demonstrar() {
        Restaurante restaurante = new Restaurante();
        Mesa mesa = new Mesa(1, 4);
        restaurante.cadastrarMesas(mesa);
        restaurante.cadastrarMesas(new Mesa(2, 2));
        Produto[] cardapio = {
            new Prato("Prato executivo", 25, 5),
            new Bebida("Suco", 8, 300, 2),
            new Sobremesa("Brownie", 12, 2, 3)
        };
        System.out.println("1. CARDAPIO POLIMORFICO");
        for (Produto produto : cardapio) {
            restaurante.cadastrarProduto(produto);
            Calculavel calculavel = produto;
            System.out.println(produto.getDescricao() + " = " + dinheiro(calculavel.calcularValor()));
        }
        Pedido pedido = restaurante.abrirPedido(mesa, new Cliente("Cliente demonstracao", "nao informado", "nao informado"));
        pedido.adicionarProduto(cardapio[0], 2);
        pedido.adicionarProduto(cardapio[1], 1);
        pedido.adicionarProduto(cardapio[2], 1);
        conta(pedido);
        System.out.println("\n2. REGRAS DE NEGOCIO");
        tentar("Mesa ocupada", () -> restaurante.abrirPedido(mesa));
        cardapio[1].marcarIndisponivel();
        tentar("Produto indisponivel", () -> pedido.adicionarProduto(cardapio[1], 1));
        cardapio[1].marcarDisponivel();
        tentar("Quantidade zero", () -> pedido.adicionarProduto(cardapio[1], 0));
        pedido.finalizarPedido();
        tentar("Pedido fechado", () -> pedido.adicionarProduto(cardapio[0], 1));
        System.out.println("Mesas livres apos finalizar: " + restaurante.consultarMesasDisponiveis().length);
        Pedido novo = restaurante.abrirPedido(mesa);
        tentar("Pedido vazio", novo::finalizarPedido);
        System.out.println("Nova abertura na mesa 1: OK");
    }
    private static void tentar(String cenario, Runnable acao) {
        try { acao.run(); }
        catch (IllegalArgumentException | IllegalStateException e) { System.out.println(cenario + ": " + e.getMessage()); }
    }
}
