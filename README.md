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

O código-fonte possui correspondência estrita (1:1) com o **Diagrama de Sequência Unificado (mensagens 1 a 65)** e o **Diagrama de Classes de Projeto da Entrega 3**, aplicando a arquitetura **Boundary-Controller-Entity (BCE)** e os padrões **GRASP** (*Controller*, *Creator*, *Information Expert*, *Low Coupling*, *High Cohesion* e *Polymorphism*):

```text
src/
├── Main.java                          # Ponto de entrada e simulação das mensagens 1 a 65 (Fluxo Principal + FA1, FA2, FA3)
├── fronteira/                         # Estereótipo <<Fronteira>> (Boundary)
│   ├── viewAssinatura.java            # Interface principal com o ator Assinante
│   └── fronteiraOperadora.java        # Interface de comunicação com a Operadora de Cartão de Crédito
├── controle/                          # Estereótipo <<Controller>>
│   └── ctlAssinatura.java             # Coordenador do caso de uso Assinar Plano de Refeições
├── colecao/                           # Estereótipo <<Collection>> (Multiobjetos)
│   ├── ColecaoPlanoAssinatura.java    # Coleção de planos de assinatura disponíveis
│   ├── ColecaoProduto.java            # Coleção de produtos do cardápio (PratoPrincipal, Acompanhamento, Sobremesa)
│   └── ColecaoItemPedido.java         # Coleção de itens compostos no pedido
├── entidade/                          # Estereótipo <<Entidade>> (Domínio)
│   ├── Assinante.java
│   ├── CodigoConfirmacao.java
│   ├── PreferenciaAlimentar.java
│   ├── PlanoAssinatura.java
│   ├── Cardapio.java
│   ├── Produto.java                   # Superclasse de itens de refeição
│   ├── PratoPrincipal.java            # Subclasse de Produto (-pesoGramas: int)
│   ├── Acompanhamento.java            # Subclasse de Produto (-porcao: String)
│   ├── Sobremesa.java                 # Subclasse de Produto (-contemAcucar: boolean)
│   ├── Assinatura.java
│   ├── Pedido.java
│   ├── ItemPedido.java
│   ├── Endereco.java
│   ├── Entrega.java
│   ├── Pagamento.java
│   └── OperadoraCartaoCredito.java
└── persistencia/                      # Persistência de dados em arquivo
    └── ArquivoCSV.java                # Gravação das assinaturas confirmadas em dados/assinaturas.csv
```

---

## Como Compilar e Executar

### 1. Compilar o projeto
Na raiz do repositório (`Proj_1_Projeto_de_Software`), execute:

```bash
javac -encoding UTF-8 -d out src/Main.java src/fronteira/*.java src/controle/*.java src/colecao/*.java src/entidade/*.java src/persistencia/*.java
```

### 2. Executar a simulação completa (Mensagens 1 a 65 + FA1, FA2 e FA3)
```bash
java -cp out Main
```

### 3. Executar em modo interativo (opcional)
```bash
java -cp out Main --interativo
```

### 4. Persistência em CSV
Ao confirmar o pagamento e ativar a assinatura, os dados completos (protocolo, data de início, status, celular do assinante, preferência alimentar, plano, valor, itens do pedido, endereço, previsão da 1ª entrega e status do pagamento) são gravados automaticamente em:
- `dados/assinaturas.csv`
