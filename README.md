# 🌱 AgroTech

Aplicativo Android de gestão agrícola para *pequenos e médios produtores rurais*. Centraliza em um só lugar o controle da equipe, das atividades, das colheitas e dos relatórios de produção.

> Projeto acadêmico do curso de Análise e Desenvolvimento de Sistemas (ADS) – UNICID.
> *Status:* protótipo funcional em desenvolvimento.

---

## 💡 Sobre o projeto

Muitos pequenos e médios produtores ainda controlam a propriedade no papel ou de cabeça, e parte da equipe, principalmente os funcionários mais velhos, nem sempre consegue ir ao campo com frequência. O AgroTech reúne tudo no celular para deixar o trabalho mais rápido, tanto para o coordenador quanto para quem está na lavoura.

A ideia nasceu no semestre anterior, na disciplina da Professora Ângela, a partir de soluções para uma fazenda/sítio com apoio de inteligência artificial. Neste semestre ela virou um protótipo no Figma e este aplicativo em Java.

## ✨ Funcionalidades

*Coordenador Agrícola (gestor)*
- *Dashboard:* total de voluntários, colheita do mês, atividades e status do sistema, além do clima local.
- *Voluntários:* cadastro, remoção e acompanhamento de especialidade e horas.
- *Atividades:* quadro Pendente → Em execução → Concluído, com delegação a um responsável.
- *Colheitas:* registro de produto, peso e responsável, com histórico.
- *Relatórios:* gráfico de rendimento por mês, *meta mensal de colheita* com percentual de progresso e gráfico de pizza dos produtos mais colhidos.

*Colhedor (campo)*
- Vê as atividades que ainda estão abertas.
- Registra quanto colheu no dia.


## 🛠️ Tecnologias

- *Java 8* (Android nativo)
- *Android Studio* (Hedgehog 2023.1.1 ou mais novo) e *Gradle*
- AppCompat e Material Components
- Navegação com menu lateral (Navigation Drawer) e Fragments
- Gráficos de barras e pizza desenhados com Canvas, sem bibliotecas externas
- Protótipo de design no *Figma*
- Compatível com Android 7.0+ (minSdk 24)

## 📂 Estrutura do código

O código do app está em app/src/main/:


app/src/main/
├── java/com/agrotech/app/
│   ├── LoginActivity.java        # Tela de login
│   ├── MainActivity.java         # Menu lateral e troca de telas
│   ├── DashboardFragment.java    # Painel geral
│   ├── VoluntariosFragment.java  # Equipe
│   ├── AtividadesFragment.java   # Quadro de atividades
│   ├── ColheitasFragment.java    # Registro e histórico de colheitas
│   ├── RelatoriosFragment.java   # Gráficos e meta mensal
│   ├── ColhedorFragment.java     # Tela do colhedor
│   ├── BarChartView.java         # Gráfico de barras (Canvas)
│   ├── PieChartView.java         # Gráfico de pizza (Canvas)
│   ├── Repo.java                 # Dados em memória
│   └── Util.java                 # Funções auxiliares
├── res/layout/                   # Telas em XML
└── AndroidManifest.xml


## ▶️ Como rodar

1. Instale o *Android Studio* (Hedgehog 2023.1.1 ou mais novo). Ele já traz o JDK 17 necessário.
2. Clone o repositório ou baixe o ZIP.
3. No Android Studio: *File > Open* e selecione a pasta do projeto.
4. Aguarde o *Gradle Sync* (precisa de internet na primeira vez).
5. Em *Device Manager, crie um emulador (ex.: Pixel 6, API 34) e clique em **Run ▶️*.

## 🔑 Acesso de demonstração

O login é fixo, só para demonstração:

| Perfil | Usuário | Senha |
|---|---|---|
| Coordenador Agrícola | César Augusto (maiúsculas/minúsculas e acentos são aceitos) | agrotech ou agro tech |
| Colhedor | colhedor | agrotech ou agro tech |

Ao tocar no campo de usuário, o app sugere "César Augusto" para completar.

## ⚠️ Limitações do protótipo

- Os dados ficam *em memória* e voltam ao padrão quando o app é fechado.
- O login não tem servidor: as credenciais são fixas no código.
- O clima e o histórico mensal do gráfico de barras são valores de exemplo.
- "Esqueceu sua senha?" e a exportação em PDF ainda não foram implementados.

## 🚀 Próximos passos

- Salvar os dados com Room/SQLite
- Autenticação real e cadastro de usuários
- Monitoramento do solo com apoio de IA
- Notificar o colhedor quando uma atividade for delegada
- Clima em tempo real
- Exportar relatórios em PDF

## 👥 Equipe

Gustavo Nascimento 

Renan Job De Souza

Endriws Correia Ramos

Baptista Luís Teixeira Chuma 

Henrique Rozario Santana 

Nicolas Teixeira Bueno

Gleyson Caninde do Rosário 


Orientação: Professora Ângela
