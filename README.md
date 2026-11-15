# Minha Refeição - Assinar Plano de Refeições (Java + CSV)

## Compilar e executar (Java 11+)
    javac -encoding UTF-8 -d out $(find src -name '*.java')
    java -Dfile.encoding=UTF-8 -Dstdout.encoding=UTF-8 -cp out Main

Os dados são gravados em ./dados/*.csv (a pasta é criada na 1ª execução).
Para demonstração, `-Dsms.fixo=123456` fixa o código SMS.
Cartão simulado: 16 dígitos; terminado em 0 = recusado (FA3).

## Pacotes x diagramas
- fronteira: viewAssinatura, fronteiraOperadora
- controle: ctlAssinatura
- colecao: ColAssinantes, ColPlanos (+ ColAssinaturas, extensão de persistência)
- entidade: Assinante, CodigoConfirmacao, PlanoAssinatura, Assinatura, Pedido, ItemPedido,
  PreferenciaAlimentar, Cardapio, ItemCardapio, EnderecoEntrega, EnderecoCobranca, Pagamento, OperadoraCartaoCredito
- persistencia: ArquivoCSV
