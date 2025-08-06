# Bank Account

### Features

* Begin DTO, Model for aplication (PL-00)
* Finish for another view (PL-01)
* Adicionada criação de usuário e conta com vínculo dinâmico, e pesquisar usuários por nome e ID. (PL-02)
* Adicionada validação para criação de conta, evitando saldo negativo e duplicação de usuários, com suporte para telefone e e-mail opcionais. (PL-03)
* Adicionada validação para o tipo de conta com enum AccountType. (PL-04)
* Criação de funcionalidades de saque e depósito: Implementado método de saque e depósito com mensagens de sucesso para cada operação. (PL-05)
* Validação de valores para depósito e saque: Garantido que depósitos e saques não possam ser realizados com valores negativos. (PL-05)
* Mensagem personalizada ao sacar: Ao realizar um saque, uma mensagem é retornada com o valor final da conta do usuário. (PL-05)
* Consulta de saldo: Adicionada a funcionalidade de verificação de saldo, retornando uma mensagem clara com o valor da conta. (PL-05)
* Adicionar testes unitários para AccountService cobrindo criação de conta, depósito, saque e consulta de saldo. (PL-06)
* Ajuste no Docker e criação do script para povoar o banco. (PL-07)
