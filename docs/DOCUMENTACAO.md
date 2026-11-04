# Documentação - Sistema de Restaurante - Grupo B

## Descrição e continuidade

O sistema organiza mesas, cardápio e pedidos de um restaurante pelo terminal. Parte das classes da Unidade I e mantém cadastro, disponibilidade, pedidos, quantidades, cálculo do total, liberação de mesa e arrays com crescimento automático. A versão atual acrescenta categorias de produtos e separação por pacotes. A referência original é o commit `8042fe76083fa9039375ba3df8f50d10f88f4377` do repositório `Joaoifbavamo/SistemaRestauranteJava`.

## Classes e responsabilidades

| Classe/pacote | Responsabilidade |
|---|---|
| `Calculavel` / contrato | Define `double calcularValor()` |
| `Produto` / modelo | Classe abstrata com nome, preço base, disponibilidade e validações comuns |
| `Prato` / modelo | Soma ao preço base o adicional de acompanhamento |
| `Bebida` / modelo | Informa volume em ml e soma o adicional de embalagem |
| `Sobremesa` / modelo | Soma quantidade de bolas extras multiplicada pelo preço de cada bola |
| `Cliente` / modelo | Mantém nome, CPF e telefone, com associação opcional ao pedido |
| `Mesa` / modelo | Número, capacidade e disponibilidade; ocupação controlada pelo pedido |
| `ItemPedido` / modelo | Associa produto e quantidade positiva e calcula subtotal |
| `Pedido` / modelo | Gerencia itens, estado, cliente, conta e finalização |
| `Restaurante` / servico | Gerencia arrays de mesas, produtos e pedidos |
| `Main` / app | Interface de console e demonstração reproduzível |

## Herança e classe abstrata

`Produto` generaliza os dados do cardápio e não pode ser instanciado diretamente. As subclasses chamam `super(nome, preco)`, herdam disponibilidade e getters e implementam os métodos abstratos `getDescricao()` e `calcularValor()`. O preço base e os adicionais são imutáveis para que mudanças de cadastro não alterem contas já registradas.

## Interface e polimorfismo

`Calculavel` estabelece um contrato comum a produtos, itens e pedidos. `Produto` declara o cálculo abstrato; suas três subclasses fornecem a implementação concreta. `ItemPedido` multiplica o valor de venda pela quantidade. `Pedido` soma os itens recursivamente.

```java
Produto produto = new Sobremesa("Brownie", 12, 2, 3);
Calculavel calculavel = produto;
System.out.println(calculavel.calcularValor()); // 18.0
```

O uso real está em `ItemPedido.calcularValor()`: `produto.calcularValor() * quantidade`. A JVM escolhe a implementação concreta sem `instanceof` ou condicionais para descobrir a categoria. O `switch` do menu apenas cria o produto escolhido pelo usuário; ele não decide como calcular produtos já criados. A demonstração também percorre `Produto[]` e usa referências `Calculavel`.

## Recursividade

```java
private double somarItens(int indice) {
    if (indice >= quantidadeItens) return 0.0;
    return itens[indice].calcularSubtotal() + somarItens(indice + 1);
}
```

O problema é dividido no subtotal atual e na soma dos itens restantes. A parada acontece quando o índice alcança a quantidade ocupada, e não a capacidade do array. A chamada com `indice + 1` aproxima a execução da parada. `calcularTotalRecursivo()` inicia em zero, e `calcularValor()` usa essa versão na conta real.

Para subtotais `[60, 10, 18]`: `soma(0) = 60 + soma(1)`, `soma(1) = 10 + soma(2)`, `soma(2) = 18 + soma(3)` e `soma(3) = 0`. No retorno: 18, 28 e 88. Pedido vazio retorna zero, mas não pode ser finalizado. Tempo O(n), pilha O(n). A versão iterativa permanece para comparação.

## Regras de negócio

- Mesa cadastrada, número único e capacidade positiva.
- Um pedido aberto ocupa a mesa, inclusive se criado diretamente pelo construtor.
- Mesa ocupada não recebe outro pedido.
- Produto indisponível não pode ser incluído; quantidade deve ser positiva.
- Preços e adicionais devem ser finitos e não negativos.
- Pedido vazio não pode ser finalizado.
- Pedido finalizado não aceita itens nem nova finalização. Isso impede que um pedido antigo libere uma mesa já ocupada por outro atendimento.
- Finalizar libera a mesa; a indisponibilidade posterior do produto não remove itens já pedidos.
- Getters de arrays retornam cópias das posições ocupadas, protegendo a estrutura interna.

## Organização e evolução de erros

As regras ficam no modelo e no serviço; o menu captura exceções e apresenta mensagens. Na Unidade I, algumas operações apenas imprimiam mensagens ou retornavam `null`. A versão atual lança `IllegalArgumentException` para dados inválidos e `IllegalStateException` para estado incompatível. Os nomes principais dos métodos foram mantidos, mas as importações e o tratamento de falhas foram atualizados.

## Dificuldades e decisões técnicas

Pontos de atenção desta evolução: tornar `Produto` abstrato sem perder os dados comuns; calcular a partir do valor de venda, em vez de usar apenas `getPreco()`; preservar arrays sem `ArrayList`; separar mensagens de interface das regras; impedir alterações em pedidos fechados; e fazer a recursão parar no total de posições ocupadas. Estes são pontos técnicos da implementação preparada. O relato pessoal de dificuldades e a participação de cada integrante devem ser escritos pelo grupo conforme sua experiência real, sem atribuir atividades que não realizou.

## Validação

Execute `executar.ps1 -Testar`. As verificações cobrem despacho polimórfico, total 88, pedido vazio, indisponibilidade, quantidades inválidas, ocupação de mesa, reabertura, finalização repetida, cópias de arrays, expansão acima de dez posições e equivalência entre as somas iterativa e recursiva. O modo `-Demo` exercita o fluxo público completo. A compilação usa `--release 20` e `-Xlint:all`.

## Artefatos e apresentação

O diagrama está em `DIAGRAMA.md` com fonte Mermaid em `diagrama-classes.mmd` e versão vetorial em `diagrama-classes.svg`. O código e esta documentação são enviados ao repositório da Unidade II. O roteiro em PDF fica separado. A apresentação deve durar de 15 a 20 minutos, mostrar todos os integrantes e demonstrar execução, herança, abstração, interface, polimorfismo, recursividade e UML.
