# Chat TCP Multithreaded com ThreadPool

## Descrição

Este projeto implementa um servidor de chat TCP multithreaded utilizando um pool de threads (`ThreadPool`) para gerenciar a comunicação entre múltiplos clientes. O servidor aceita conexões de clientes e retransmite as mensagens recebidas para todos os outros clientes conectados.

## Estrutura do Projeto

- **ThreadPoolTCPServer**: O servidor TCP que gerencia múltiplos clientes usando um pool de threads.
- **SimpleTCPClient**: O cliente TCP que se conecta ao servidor e permite a comunicação em tempo real.

## Requisitos

- Java Development Kit (JDK) 8 ou superior.

## Compilação e Execução

Para compilar e executar os arquivos Java, siga as instruções abaixo:

### Compilação

Navegue até o diretório onde os arquivos `.java` estão localizados e execute os seguintes comandos:

```bash
javac ThreadPoolTCPServer.java
javac SimpleTCPClient.java
```

## Passo 1: Executar o Servidor

Primeiro, execute o servidor para que ele possa começar a aceitar conexões. No terminal, navegue até o diretório onde o arquivo ThreadPoolTCPServer.java está localizado e execute o comando:

```bash
java ThreadPoolTCPServer
```

O servidor agora está em execução e aguardando conexões de clientes.

## Passo 2: Executar os Clientes
Em novas janelas de terminal (uma para cada cliente), navegue até o diretório onde o arquivo SimpleTCPClient.java está localizado e execute o comando:

```bash
java SimpleTCPClient
```

## Funcionamento

### Servidor

O servidor aceita conexões de clientes e cria um `ClientHandler` para cada cliente conectado. Cada `ClientHandler` é executado em uma thread separada gerenciada pelo pool de threads (`ExecutorService`). Quando um cliente envia uma mensagem, o `ClientHandler` a recebe e o servidor a retransmite para todos os outros clientes conectados, exceto o remetente.

### Cliente

Cada cliente se conecta ao servidor e cria uma nova thread para ouvir mensagens do servidor e exibi-las no console. O cliente principal lê mensagens do teclado e as envia para o servidor.

## Exemplo de Uso

1. Abra um terminal e execute o servidor:

    ```bash
    java ThreadPoolTCPServer
    ```

2. Abra um novo terminal e execute o primeiro cliente:

    ```bash
    java SimpleTCPClient
    ```

3. Abra outro terminal e execute o segundo cliente:

    ```bash
    java SimpleTCPClient
    ```

4. Digite mensagens em qualquer cliente e veja as mensagens serem retransmitidas para os outros clientes conectados.
