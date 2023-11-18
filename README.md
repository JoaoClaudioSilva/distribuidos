# Sistema de rastreamento de pacotes

## Equipe
- João Claudio de Oliveira Silva
- Mateus Luz Francischini Bonardi
- Victor Hugo Barbosa da Silva
- Rafael Duarte Daltio
- Eduardo Golin Sudaia

## Link dos repositórios

[Link do GitHub](https://github.com/JoaoClaudioSilva/distribuidos)

[Link do Docker Hub](https://hub.docker.com/u/joaoclaudiosilva)

## Instalação
Pré-requisitos:
- Docker Desktop - 4.25 ou superior ([Link do site oficial](https://www.docker.com/products/docker-desktop/))
- Xming - 6.9.0.31 ou superior ([Link do SourceForge](https://sourceforge.net/projects/xming/))

1 - Baixe e instale o Docker Hub em seu sistema operacional utilizando o tutorial disponibilizado pelo site de download.

- Caso necessário, atualize as variáveis de ambiente do Windows para que os comandos do Docker sejam reconhecidos pelo prompt de comando.

2 - Baixe e instale o Xming utilizando o tutorial disponibilizado pelo site de download.

3 - Puxe a imagem [servidor](https://hub.docker.com/r/joaoclaudiosilva/servidor) disponível no Docker Hub.
```cmd
docker pull joaoclaudiosilva/servidor
```

4 - Puxe a imagem [cliente](https://hub.docker.com/r/joaoclaudiosilva/cliente) disponível no Docker Hub.
```cmd
docker pull joaoclaudiosilva/cliente
```

5 - Crie uma nova rede no docker chamada "minha-rede".
```cmd
docker network create minha-rede
```

6 - Execute o Xming com a configuração padrão.

7 - Rode a imagem "servidor" com a linha de comando seguinte.
```cmd
docker run --network=minha-rede -p 65000:65000 -e DISPLAY=host.docker.internal:0.0 --name=Servidor joaoclaudiosilva/servidor
```

8 - Rode a imagem "cliente" com a linha de comando seguinte.
```cmd
docker run --network=minha-rede -e DISPLAY=host.docker.internal:0.0 --name=Cliente joaoclaudiosilva/cliente
```
