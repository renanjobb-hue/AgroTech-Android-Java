# AgroTech (protótipo Android – Java)

App de gestão agrícola para pequenos e médios produtores.

## Como rodar
1. Android Studio (Hedgehog 2023.1.1 ou mais novo). Ele já traz o JDK 17 necessário.
2. File > Open > selecione a pasta `AgroTech`.
3. Aguarde o Gradle Sync (precisa de internet na 1ª vez).
4. Device Manager > crie um emulador (ex.: Pixel 6, API 34) e clique em Run ▶.

## Telas
- Login: usuário "César Augusto" (aceita maiúsculas, minúsculas, com ou sem acento) e senha "agrotech" (ou "agro tech"). O usuário "colhedor" com a mesma senha abre a tela do colhedor.
- Barra lateral (menu hambúrguer) com: Dashboard, Voluntários, Atividades, Colheitas, Relatórios, Tela do Colhedor e Sair
- Tela do Colhedor: registra o que colheu no dia (acessível pela barra lateral)

## Observações
- Linguagem: Java 8, minSdk 24 (Android 7.0+), sem bibliotecas além de AppCompat e Material.
- Dados ficam em memória (classe `Repo`) e voltam ao padrão ao fechar o app. Próximo passo: Room/SQLite.
