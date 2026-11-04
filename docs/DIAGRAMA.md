# Diagrama de classes UML

[Versão vetorial ampliável](diagrama-classes.svg) | [Fonte Mermaid](diagrama-classes.mmd)

```mermaid
classDiagram
direction TB
class Calculavel {
  <<interface>>
  +calcularValor() double
}
class Produto {
  <<abstract>>
  -String nome
  -double preco
  -boolean disponivel
  +getNome() String
  +getPreco() double
  +isDisponivel() boolean
  +marcarDisponivel() void
  +marcarIndisponivel() void
  +getDescricao() String*
  +calcularValor() double*
}
class Prato {
  -double adicionalAcompanhamento
  +calcularValor() double
  +getDescricao() String
}
class Bebida {
  -int volumeMl
  -double adicionalEmbalagem
  +calcularValor() double
  +getDescricao() String
}
class Sobremesa {
  -int bolasExtras
  -double precoBolaExtra
  +calcularValor() double
  +getDescricao() String
}
class Cliente {
  -String nome
  -String cpf
  -String telefone
  +getNome() String
  +getCpf() String
  +getTelefone() String
}
class Mesa {
  -int numero
  -int capacidade
  -boolean disponivel
  +getNumero() int
  +getCapacidade() int
  +isDisponivel() boolean
  ~ocuparMesa() void
  ~liberarMesa() void
}
class ItemPedido {
  -Produto produto
  -int quantidade
  +getProduto() Produto
  +getQuantidade() int
  +calcularSubtotal() double
  +calcularValor() double
}
class Pedido {
  -Mesa mesa
  -Cliente cliente
  -ItemPedido[] itens
  -int quantidadeItens
  -boolean finalizado
  +adicionarProduto(Produto, int) void
  +calcularTotal() double
  +calcularTotalRecursivo() double
  -somarItens(int) double
  +calcularValor() double
  +finalizarPedido() void
  +getItens() ItemPedido[]
  +isFinalizado() boolean
}
class Restaurante {
  -Mesa[] mesas
  -Produto[] produtos
  -Pedido[] pedidos
  -int quantidadeMesas
  -int quantidadeProdutos
  -int quantidadePedidos
  +cadastrarMesas(Mesa) void
  +cadastrarProduto(Produto) void
  +abrirPedido(Mesa) Pedido
  +abrirPedido(Mesa, Cliente) Pedido
  +consultarMesasDisponiveis() Mesa[]
  +getMesas() Mesa[]
  +getProdutos() Produto[]
  +getPedidos() Pedido[]
}
class Main {
  +main(String[]) void
  -menu() void
  -demonstrar() void
  +conta(Pedido) void
}
Calculavel <|.. Produto
Calculavel <|.. ItemPedido
Calculavel <|.. Pedido
Produto <|-- Prato
Produto <|-- Bebida
Produto <|-- Sobremesa
Restaurante "1" o-- "0..*" Mesa : cadastra
Restaurante "1" o-- "0..*" Produto : cadastra
Restaurante "1" o-- "0..*" Pedido : registra
Pedido "1" *-- "0..*" ItemPedido : contem
ItemPedido "0..*" --> "1" Produto : referencia
Pedido "0..*" --> "1" Mesa : atendimento
Pedido "0..*" --> "0..1" Cliente : identifica
Main ..> Restaurante : utiliza
Main ..> Calculavel : calcula

```

O losango cheio indica composição: cada item pertence a um pedido. O losango vazio indica agregação de cadastros pelo restaurante. Uma mesa pode estar associada a vários pedidos históricos, mas a regra de negócio permite somente um aberto por vez. O cliente é opcional em cada pedido. `Produto`, `ItemPedido` e `Pedido` realizam `Calculavel`; o cálculo concreto dos produtos vem das subclasses.
