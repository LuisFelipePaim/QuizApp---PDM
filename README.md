📖 Documentação Técnica: Quiz Master
1. Visão Geral do Sistema
O Quiz Master é uma aplicação móvel Android educacional e gamificada. O sistema permite que os utilizadores criem contas, realizem quizzes de múltiplas disciplinas, acumulem pontos de experiência (XP) e acompanhem o seu progresso através de um Dashboard de conquistas. O aplicativo foi desenhado com uma arquitetura Offline-First, garantindo que o núcleo da aplicação (responder a quizzes e salvar histórico) funcione perfeitamente sem conexão à internet.

2. Arquitetura do Software
O projeto foi desenvolvido seguindo o padrão arquitetural MVVM (Model-View-ViewModel) aliado aos princípios de Clean Architecture:

View (Camada de Apresentação): Totalmente construída em UI Declarativa utilizando Jetpack Compose. Observa os estados emitidos pelos ViewModels de forma reativa (StateFlow).

ViewModel (Camada de Lógica de Negócios): Gere o estado da UI (UiState) e atua como ponte entre a interface e os dados. Utiliza Kotlin Coroutines para processamento assíncrono.

Model / Repository (Camada de Dados): Atua como a Única Fonte de Verdade (Single Source of Truth). Decide inteligentemente quando buscar ou salvar dados na base de dados local (Room) ou na nuvem (Firebase).

3. Stack Tecnológico
Linguagem: Kotlin

UI Toolkit: Jetpack Compose (com Material Design 3 e estética Glassmorphism)

Injeção de Dependências: Dagger Hilt

Programação Assíncrona: Kotlin Coroutines & Flows

Banco de Dados Local: Room Database (SQLite)

Backend as a Service (BaaS): Firebase Authentication (Login/Registo) e Firebase Cloud Firestore (Nuvem e Ranking Global)

Navegação: Jetpack Navigation Compose

4. Requisitos Funcionais (RF)
RF01 - Autenticação: O sistema deve permitir o registo, login e logout de utilizadores via e-mail e palavra-passe.

RF02 - Realização de Quizzes: O sistema deve apresentar perguntas de múltipla escolha (A, B, C, D) separadas por disciplinas.

RF03 - Funcionamento Offline: O sistema deve permitir a realização e conclusão de quizzes mesmo sem conexão à internet, salvando os resultados localmente.

RF04 - Sincronização em Nuvem: O sistema deve sincronizar os resultados guardados localmente com o Firebase assim que uma conexão à internet for detetada.

RF05 - Gamificação (XP e Níveis): O sistema deve calcular 50 XP por cada resposta correta e subir o nível do utilizador a cada 500 XP acumulados.

RF06 - Dashboard e Conquistas: O utilizador deve poder visualizar as suas estatísticas (precisão, partidas jogadas) e medalhas de conquista no seu perfil.

RF07 - Ranking Global: O sistema deve listar os 10 melhores jogadores de cada matéria com base na pontuação máxima registada na nuvem.

5. Requisitos Não Funcionais (RNF)
RNF01 - Usabilidade: A interface deve ser responsiva, possuir scroll dinâmico para textos longos e feedback visual imediato (cores, cronómetro).

RNF02 - Desempenho: A aplicação não deve sofrer congelamentos na Main Thread, delegando operações de I/O (Room e Firebase) para as Dispatchers.IO.

RNF03 - Persistência: O sistema não deve perder dados de quizzes em andamento ou finalizados caso a aplicação seja encerrada abruptamente ou falte internet.

6. Fluxo de Sincronização (Room + Firebase)
Para garantir a resiliência do aplicativo, a camada de dados funciona no seguinte fluxo:

O utilizador faz o login (requer internet).

O repositório descarrega as questões do Firebase e faz um cache no Room Database.

O utilizador abre um Quiz. As questões são lidas a partir do Room (rápido e disponível offline).

O utilizador finaliza o Quiz. O QuizResult é salvo no Room imediatamente, atualizando as métricas locais.

Em segundo plano, o repositório verifica a conexão. Se houver internet, envia o QuizResult para o Firestore para contabilizar no Ranking Global. Se não houver internet, a tarefa fica pendente para a próxima abertura da aplicação.

7. Estrutura de Diretórios
Plaintext
com.example.quizapp
│
├── data/                 # Camada de Dados
│   ├── local/            # Configuração do Room (DAOs, Entity, Database)
│   ├── remote/           # Configuração do Firebase
│   ├── model/            # Classes de Dados (Question, QuizResult)
│   └── repository/       # Lógica de Sincronização e Regras de Negócio
│
├── di/                   # Módulos de Injeção de Dependência (Dagger Hilt)
│
├── ui/                   # Camada de Apresentação (Jetpack Compose)
│   ├── auth/             # LoginScreen, SignUpScreen, AuthViewModel
│   ├── dashboard/        # DashboardScreen (Gamificação)
│   ├── history/          # HistoryScreen, HistoryViewModel
│   ├── profile/          # ProfileScreen
│   ├── quiz/             # QuizScreen, QuizViewModel, SubjectScreen (Home)
│   └── ranking/          # RankingScreen, RankingViewModel
│
└── MainActivity.kt       # Ponto de Entrada e NavHost (Rotas)
8. Como Executar o Projeto (Instruções de Build)
Clone o repositório na sua máquina local.

Abra o projeto no Android Studio (versão Iguana ou superior recomendada).

Aguarde o Gradle Sync baixar todas as dependências (Compose, Hilt, Firebase, Room).

Certifique-se de que o ficheiro google-services.json (gerado na consola do Firebase) está presente na pasta app/.

Execute o comando Build > Clean Project e depois Build > Rebuild Project para gerar as classes de injeção do Hilt.

Execute a aplicação num emulador ou dispositivo físico com Android 8.0 (API 26) ou superior.
