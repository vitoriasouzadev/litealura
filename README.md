# 📚 Literalura - Catálogo de Livros

Projeto desenvolvido como parte do desafio da Alura com o objetivo de criar um catálogo de livros com interação via console.

---

## 🎯 Objetivo

Desenvolver uma aplicação Java com Spring Boot que:

- Busque livros através da API Gutendex
- Salve os dados em banco de dados
- Permita interação textual via console
- Ofereça no mínimo 5 opções de interação

---

## 🚀 Funcionalidades

A aplicação oferece as seguintes opções no menu:

1. Buscar livro pelo título
2. Listar livros registrados
3. Listar autores registrados
4. Listar autores vivos em determinado ano
5. Listar livros por idioma
0. Sair da aplicação

---

## 🛠️ Tecnologias utilizadas

- Java 17+
- Spring Boot
- Spring Data JPA
- Hibernate
- Banco de dados H2
- API Gutendex

---

## 🌐 API Utilizada

Gutendex API  
https://gutendex.com/

A API é utilizada para buscar informações sobre livros e autores.

---

## 🗄️ Banco de Dados

- Banco em memória H2
- Relacionamento ManyToOne entre Livro e Autor
- Persistência realizada com Spring Data JPA

---

## ▶️ Como executar o projeto

1. Clonar o repositório:
   ```bash
   git clone https://github.com/seu-usuario/literalura.git
