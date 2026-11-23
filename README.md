# Sistema de Processo Seletivo

Aplicação console desenvolvida em Java para cadastrar candidatos, validar os requisitos de uma vaga e gerar um ranking dos aprovados.

## Funcionalidades

- Cadastro de candidatos e vagas
- Validação de CPF
- Validação de e-mail
- Verificação da idade mínima
- Comparação de experiência, pretensão salarial, formação e habilidades
- Avaliação técnica e comportamental
- Controle do status do candidato
- Geração de relatório geral e ranking por média

## Requisitos

- Java JDK 17 ou superior
- Terminal PowerShell ou Prompt de Comando

Verifique a instalação do Java:

```powershell
java -version
javac -version
```

## Como executar

Na raiz do projeto, compile os arquivos Java:

```powershell
mkdir out -Force
javac -encoding UTF-8 -d out @sources.txt
```

Em seguida, execute a aplicação:

```powershell
java -cp out br.com.seletivo.Main
```

## Fluxo da triagem

A triagem é realizada em três etapas:

1. **Dados cadastrais**
   - CPF válido
   - E-mail válido
   - Idade igual ou superior a 18 anos

2. **Aderência à vaga**
   - Experiência mínima conforme o nível da vaga
   - Pretensão salarial dentro da faixa
   - Formação superior, quando exigida
   - Pelo menos 70% das habilidades requeridas

3. **Avaliações**
   - Nota técnica mínima: 6
   - Nota comportamental mínima: 6
   - Média utilizada no ranking dos aprovados

Os candidatos sem notas recebem o status **Aguardando Documentos** até que as avaliações sejam atribuídas.

## Estrutura do projeto

```text
src/main/java/br/com/seletivo/
├── Main.java
├── enums/
├── model/
├── service/
├── util/
└── validator/
```

- `Main.java`: demonstração com uma vaga e candidatos fictícios
- `model`: entidades do domínio
- `service`: cadastro, triagem e geração de relatórios
- `validator`: regras de validação
- `util`: validadores de CPF e e-mail
- `enums`: níveis de vaga e status dos candidatos

## Status possíveis

- `PENDENTE`
- `EM_ANALISE`
- `APROVADO`
- `REPROVADO`
- `AGUARDANDO_DOCUMENTOS`

## Licença

Este projeto está licenciado sob os termos da licença MIT. Consulte o arquivo [LICENSE](LICENSE).
