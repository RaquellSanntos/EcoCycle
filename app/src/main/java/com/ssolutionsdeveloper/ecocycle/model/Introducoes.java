package com.ssolutionsdeveloper.ecocycle.model;
//CLASSE INTRODUCOES É A CLASSE POSTAGEM
public class Introducoes {
    private String introducao;
    private int imagem;

    public Introducoes() {
    }

    public Introducoes(String introducao, int imagem) {
        this.introducao = introducao;
        this.imagem = imagem;
    }

    public String getIntroducao() {
        return introducao;
    }

    public void setIntroducao(String introducao) {
        this.introducao = introducao;
    }

    public int getImagem() {
        return imagem;
    }

    public void setImagem(int imagem) {
        this.imagem = imagem;
    }
}
