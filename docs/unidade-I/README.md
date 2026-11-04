# Sistema de Gerenciamento de Restaurante

Trabalho de LP1 — **Grupo B**.

Sistema em Java que simula as operações básicas de um restaurante: cadastro de mesas e produtos, abertura de pedidos, adição de itens, cálculo do total e finalização do atendimento.

> **Observação:** por exigência do professor, o projeto **não utiliza `ArrayList`**. Todas as coleções de objetos (mesas, produtos, pedidos e itens de um pedido) são armazenadas em **arrays nativos do Java**, com uma variável contadora controlando quantas posições estão ocupadas e um método que dobra o tamanho do array automaticamente quando ele enche.

## Objetivo

Aplicar, na prática, os conceitos de Programação Orientada a Objetos estudados na disciplina: classes e objetos, atributos e métodos, encapsulamento, construtores, relacionamento entre objetos, depuração e recursividade.

## Funcionalidades

- Cadastro de mesas (número e capacidade)
- Cadastro de produtos do cardápio (nome, preço e disponibilidade)
- Abertura de pedido vinculado a uma mesa disponível
- Adição de produtos (com quantidade) a um pedido
- Cálculo do valor total do pedido — versão iterativa e versão recursiva
- Finalização do pedido, liberando a mesa
- Consulta das mesas disponíveis no momento

## Estrutura das classes

| Classe | Responsabilidade |
|---|---|
| `Produto` | Representa um item do cardápio (nome, preço, disponibilidade) |
| `Mesa` | Representa uma mesa física (número, capacidade, disponibilidade) |
| `Cliente` | Representa o cliente atendido (nome, CPF, telefone) |
| `ItemPedido` | Representa um produto pedido, com sua quantidade |
| `Pedido` | Reúne os itens pedidos (em um array `ItemPedido[]`) em uma mesa e concentra as regras de negócio |
| `Restaurante` | Gerencia, em arrays próprios (`Mesa[]`, `Produto[]`, `Pedido[]`), as mesas, produtos e pedidos do sistema |

## Regras de negócio implementadas

- Uma mesa não pode ter dois pedidos ativos simultaneamente
- Não é permitida a inclusão de produto indisponível em um pedido
- Um pedido precisa ter pelo menos um item para ser finalizado
- O valor total é calculado a partir dos produtos e suas quantidades
- Ao finalizar um pedido, a mesa correspondente volta a ficar disponível
- As coleções internas usam arrays de capacidade inicial fixa, que dobram de tamanho automaticamente quando ficam cheias (sem uso de `ArrayList`)

## Diagrama de classes

![Diagrama de classes do sistema](diagrama_classes.png)

## Como executar

1. Compile todas as classes:
   ```
   javac *.java
   ```
2. Execute a classe principal:
   ```
   java Main
   ```

## Exemplo de uso (Main.java)

```java
Restaurante restaurante = new Restaurante();

Mesa mesa1 = new Mesa(1, 4);
restaurante.cadastrarMesas(mesa1);

Produto produto1 = new Produto("Produto Genérico", 20.0);
restaurante.cadastrarProduto(produto1);

Pedido pedido1 = restaurante.abrirPedido(mesa1);
pedido1.adicionarProduto(produto1, 5);

System.out.println(pedido1.calcularTotal());          // 100.0
System.out.println(pedido1.calcularTotalRecursivo());  // 100.0

pedido1.finalizarPedido();
```

## Integrantes — Grupo B

- Marlon Santos Carvalho
- João Vitor Gonçalves de Jesus
- Marco Antônio Chaves De Souza
- Gabriel Ribeiro Guimaraes

## Dificuldades encontradas

Durante o desenvolvimento, o grupo enfrentou algumas dificuldades típicas de quem está começando com Programação Orientada a Objetos em Java:

- Entender a diferença entre `this.atributo` e o parâmetro do construtor;
- Compreender que um método getter não deve receber parâmetro;
- Diferenciar o uso de `=` e `+=` ao acumular valores dentro de um laço de repetição;
- Lidar com conflitos de nome entre atributos e parâmetros de mesmo nome dentro de um método;
- Adaptar o projeto, inicialmente pensado com `ArrayList`, para arrays nativos, controlando manualmente o tamanho ocupado e implementando o crescimento dinâmico do array quando ele fica cheio;
- Compreender e implementar a recursividade no cálculo do total do pedido, usando um índice para percorrer o array, definindo corretamente o caso base e o caso recursivo.
