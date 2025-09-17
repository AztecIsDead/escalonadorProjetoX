# Escalonador de Processos em Java
Esse repositório contém o código do trabalho da disciplina Algoritmo e Estrutura de Dados 1, onde nosso grupo ficou encarregado de projetar um escalonador de processos utilizando estruturas de dados para simular o comportamento de listas de processos, aplicando na prática os conceitos fundamentais das ADTs vistas em classe.

## Como usar
### Pré requisitos:
- Compilador de Java ``Oracle Open JDK versão 24.0.2 ou maior``


- Qualquer editor de texto (Para editar o arquivo ``processos.csv`` que servirá de entrada para o código)

### Instalação:
Clonando o repositório:
```
git clone https://github.com/AztecIsDead/escalonadorProjetoX.git
cd escalonadorProjetoX
```
### Configurando o arquivo ``processos.csv``:
O arquivo deve ser formatado como no exemplo a seguir:
```
ID,Nome,Prioridade,Ciclos,Recurso (Cabeçalho)
1,Processo,1,1,NENHUM (Dados do primeiro processo, seguindo o cabeçalho)
2,Processo,1,1,DISCO (Dados do segundo processo)
```
1. A prioridade do processo inserido pode ser de 1 a 3 (1 para ``Alta``, 2 para ``Média`` e 3 para ``Baixa``).


2. O número de ``ciclos`` necessários para qualquer dado processo não pode ser menor que um (1).


3. Cada processo deve ser inserido com um parâmetro de ``Recurso``, que pode ser deixado em branco, ou escrito como ``Nenhum`` ou ``Nulo``. Processos com o recurso marcado como ``Disco`` são sujeitos a um **bloqueio**, e só são desbloqueados no final de um ciclo.
### Regras de funcionamento do escalonador:
- A cada cinco (5) processos de **Alta Prioridade**  realizados em **sequência**, o escalonador executará um processo de prioridade **Média** ou menor (de acordo com a disponibilidade). A ``prioridade`` de um processo é representada no código pela variável da classe ``Processo.java``:``prioridade`` do tipo `int`.


- Executar um processo diminui os **ciclos** necessários por um (1), até chegar a **zero** e partir para o próximo **processo**. Os **ciclos** são representados dentro do código pela variável ``ciclos_necessarios``.


- Os processos serão realizados de acordo com a **prioridade** de cada um (``Alta`` prioridade primeiro, depois ``Média`` e, por fim, ``Baixa`` prioridade), exceto quando a primeira regra se aplica, onde processos de prioridades menores que ``Alta`` serão executados primeiro após uma sequência de processos de ``Alta`` prioridade.


- Processos com o campo ``Recurso`` marcados com ``DISCO`` sofrem **bloqueio**, e são adicionados a uma lista. Eles serão removidos dessa lista e considerados **desbloqueados** quando um **ciclo** terminar. Após serem desbloqueados, eles serão executados seguindo a sua **prioridade**. No código é gerenciado a partir da variável do tipo `String`, ``recurso_necessario``.

## Saída Esperada
Após a execução do código seguindo as regras anteriores, e fazendo uso do arquivo de exemplo ``processos.csv`` presente neste repositório, a saída esperada no terminal será:
```
--- Iniciando Simulação ---
[EXECUTANDO] Processo {Id: 1, Nome: TESTE, Prioridade: 1 (Alta), Ciclos restantes: 0, Recurso: }
[FINALIZADO] TESTE
Processo {Id: 1, Nome: TESTE, Prioridade: 1 (Alta), Ciclos restantes: 0, Recurso: }
[BLOQUEADO] TESTE precisa de Disco.
Processo {Id: 3, Nome: TESTE, Prioridade: 1 (Alta), Ciclos restantes: 1, Recurso: DISCO}
[DESBLOQUEADO] TESTE
[EXECUTANDO] Processo {Id: 3, Nome: TESTE, Prioridade: 1 (Alta), Ciclos restantes: 0, Recurso: DISCO}
[FINALIZADO] TESTE
Processo {Id: 3, Nome: TESTE, Prioridade: 1 (Alta), Ciclos restantes: 0, Recurso: DISCO}
[EXECUTANDO] Processo {Id: 2, Nome: TESTE, Prioridade: 2 (Média), Ciclos restantes: 1, Recurso: }
[EXECUTANDO] Processo {Id: 2, Nome: TESTE, Prioridade: 2 (Média), Ciclos restantes: 0, Recurso: }
[FINALIZADO] TESTE
Processo {Id: 2, Nome: TESTE, Prioridade: 2 (Média), Ciclos restantes: 0, Recurso: }
[EXECUTANDO] Processo {Id: 4, Nome: TESTE, Prioridade: 3 (Baixa), Ciclos restantes: 2, Recurso: }
[BLOQUEADO] TESTE precisa de Disco.
Processo {Id: 5, Nome: TESTE, Prioridade: 3 (Baixa), Ciclos restantes: 4, Recurso: DISCO}
[DESBLOQUEADO] TESTE
[EXECUTANDO] Processo {Id: 4, Nome: TESTE, Prioridade: 3 (Baixa), Ciclos restantes: 1, Recurso: }
[EXECUTANDO] Processo {Id: 5, Nome: TESTE, Prioridade: 3 (Baixa), Ciclos restantes: 3, Recurso: DISCO}
[EXECUTANDO] Processo {Id: 4, Nome: TESTE, Prioridade: 3 (Baixa), Ciclos restantes: 0, Recurso: }
[FINALIZADO] TESTE
Processo {Id: 4, Nome: TESTE, Prioridade: 3 (Baixa), Ciclos restantes: 0, Recurso: }
[EXECUTANDO] Processo {Id: 5, Nome: TESTE, Prioridade: 3 (Baixa), Ciclos restantes: 2, Recurso: DISCO}
[EXECUTANDO] Processo {Id: 5, Nome: TESTE, Prioridade: 3 (Baixa), Ciclos restantes: 1, Recurso: DISCO}
[EXECUTANDO] Processo {Id: 5, Nome: TESTE, Prioridade: 3 (Baixa), Ciclos restantes: 0, Recurso: DISCO}
[FINALIZADO] TESTE
Processo {Id: 5, Nome: TESTE, Prioridade: 3 (Baixa), Ciclos restantes: 0, Recurso: DISCO}

--- Simulação Encerrada ---
```

### INTEGRANTES DO GRUPO
- [Caius Vinícius Araújo de Farias](https://github.com/AztecIsDead) (_Responsável pela lógica central do escalonador_)
- [José Victor Félix](https://github.com/josevictorcfelix) (_Responsável pelo leitor de arquivo ``.csv``_)
- [Renan Augusto de Moura Carvalho](https://github.com/onamureS) (_Responsável pela lógica das listas encadeadas de processos, correção de erros e edição do arquivo ``README``_)


- Disciplina: Algoritmos e Estruturas de Dados 1
- Turma Allen, Segundo período, Tarde
- Professor: Dimmy Magalhães
- Instituto de Ensino Superior Icev