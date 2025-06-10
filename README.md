# 📚 BookReading App

Aplicativo Android desenvolvido com Java que permite aos usuários adicionarem, visualizarem, editarem e excluírem livros que estão lendo, utilizando autenticação e integração com o Firebase Firestore.

## 🚀 Funcionalidades

- 🔐 Autenticação de usuários (login e cadastro)
- 📖 Cadastro de livros com título, autor, status e nota
- 📝 Atualização e exclusão de livros
- 📊 Dashboard com gráficos (barras e donut chart) para visualização das leituras
- 🔄 Redirecionamento automático para a tela principal após salvar livros
- ☁️ Integração com Firebase Firestore e Firebase Authentication

## 🧱 Tecnologias Utilizadas

- Java (Android)
- Firebase Authentication
- Firebase Firestore
- MPAndroidChart (para gráficos)
- Material Components

## 📂 Estrutura do Projeto

── activities/           # Telas principais (Login, Cadastro, Dashboard, etc.)
── models/               # Modelos como Livro.java
── adapters/             # Adaptadores para RecyclerView
── utils/                # Classes utilitárias
── AndroidManifest.xml
── build.gradle

## 🛠️ Configuração do Firebase

1. Crie um projeto no Firebase.
2. Habilite **Authentication** por e-mail/senha.
3. Crie um **Firestore Database**.
4. Baixe o arquivo `google-services.json` e coloque na pasta `app/`.

## 👨‍💻 Desenvolvedor

- Gustavo ([@Gustaa7K](https://github.com/Gustaa7K))
