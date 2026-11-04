# Evolução da Unidade I para a Unidade II

Fonte: https://github.com/Joaoifbavamo/SistemaRestauranteJava

Commit consultado: `8042fe76083fa9039375ba3df8f50d10f88f4377`.

As sete classes originais e o README foram preservados em `unidade-I/` para consulta. Não entram na compilação: são fontes de referência da etapa anterior. Os arquivos originais DOCX e PNG permanecem no repositório de origem; a documentação e o UML desta unidade foram atualizados.

| Unidade I | Unidade II |
|---|---|
| `Produto` concreto: nome, preço, disponibilidade | `Produto` abstrato com os mesmos dados e três subclasses |
| `ItemPedido` usava `getPreco() * quantidade` | Usa `calcularValor() * quantidade` com despacho dinâmico |
| `Pedido` com array de itens | Mesmo modelo, interface `Calculavel`, validação de estado e cópia defensiva |
| Soma iterativa e recursiva | Ambas preservadas; conta principal chama a recursiva |
| `Mesa` com estado disponível | Preservada, com validação e ocupação pelo ciclo de vida do pedido |
| `Cliente` isolado | Preservado e associado opcionalmente ao pedido |
| `Restaurante` com arrays de dez posições | Arrays preservados e expandidos por `Arrays.copyOf` |
| Demonstração em `Main` | Demonstração ampliada e menu interativo |
| Sem pacotes | Pacotes `modelo`, `contrato`, `servico`, `app` |
| Falhas por impressão ou `null` | Exceções tratadas no console |

`Arrays.copyOf` cria um novo array; não utiliza `ArrayList`. A antiga sobrecarga pública de recursão que recebia um array externo foi substituída por auxiliar privado para proteger o estado. A API pública `calcularTotalRecursivo()` foi preservada. A classe `Produto` agora deve ser criada por suas subclasses.
