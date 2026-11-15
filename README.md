# Minha Refeição — Serviço de Assinatura de Marmitas

Implementação em Java do cenário de caso de uso **"Assinar Plano de Refeições"**, desenvolvida para a disciplina de **Projeto de Software** da Faculdade de Computação e Informática (FCI) — Universidade Presbiteriana Mackenzie.

## Autores

| Integrante | RA |
| :--- | :--- |
| Daniel Arais Motta | 10419718 |
| Pedro Nicoletti Galante | 10436275 |
| Pedro Botelho | 10738317 |

---

## Arquitetura do Projeto (BCE e GRASP)

O código-fonte foi estruturado em pacotes que refletem diretamente os estereótipos do **Diagrama de Sequência** e do **Diagrama de Classes de Projeto**:

```text
src/
├── Main.java                          # Ponto de entrada da aplicação e execução dos cenários
├── fronteira/                         # Estereótipo <<Fronteira>> (Boundary)
│   ├── viewAssinatura.java            # Interface com o ator principal (Assinante)
│   └── fronteiraOperadora.java        # Interface de comunicação com a Operadora de Cartão
├── controle/                          # Estereótipo <<Controller>>
│   └── ctlAssinatura.java             # Coordenador do caso de uso Assinar Plano de Refeições
├── colecao/                           # Estereótipo <<Collection>> (Multiobjetos)
│   ├── ColecaoPlanoAssinatura.java    # Coleção de planos disponíveis
│   ├── ColecaoProduto.java            # Coleção de produtos do cardápio
│   └── ColecaoItemPedido.java         # Coleção de itens compostos no pedido
├── entidade/                          # Estereótipo <<Entidade>> (Domínio)
│   ├── Assinante.java
│   ├── CodigoConfirmacao.java
│   ├── PreferenciaAlimentar.java
│   ├── PlanoAssinatura.java
│   ├── Cardapio.java
│   ├── Produto.java                   # Superclasse de itens de refeição
│   ├── PratoPrincipal.java            # Subclasse de Produto
│   ├── Acompanhamento.java            # Subclasse de Produto
│   ├── Sobremesa.java                 # Subclasse de Produto
│   ├── Assinatura.java
│   ├── Pedido.java
│   ├── ItemPedido.java
│   ├── Endereco.java
│   ├── Entrega.java
│   ├── Pagamento.java
│   └── OperadoraCartaoCredito.java
└── persistencia/                      # Persistência de dados em arquivo
    └── ArquivoCSV.java                # Leitura e gravação dos registros em formato CSV