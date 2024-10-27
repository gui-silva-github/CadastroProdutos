package com.example.cadastroprodutos;

public class Produto {

    private int id; // ID do produto no banco
    private String nome; // Nome do produto
    private double preco; // Preço do produto

    public Produto(int id, String nome, double preco) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }
}
