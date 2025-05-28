package model;

import java.time.LocalDate;

public class Colecao {
    private int id;
    private String nome;
    private String equipamento;
    private String requerente;
    private String localImagem;
    private LocalDate dataDoPedido; 
    private String descricao;
    private boolean status;

    public Colecao() {
    }

    public Colecao(int id, String nome, String equipamento, String requerente, String localImagem, LocalDate dataDoPedido, String descricao, boolean status) {
        this.id = id;
        this.nome = nome;
        this.equipamento = equipamento;
        this.requerente = requerente;
        this.localImagem = localImagem;
        this.dataDoPedido = dataDoPedido;
        this.descricao = descricao;
        this.status = status;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(String equipamento) {
        this.equipamento = equipamento;
    }

    public String getRequerente() {
        return requerente;
    }

    public void setRequerente(String requerente) {
        this.requerente = requerente;
    }

    public String getLocalImagem() {
        return localImagem;
    }

    public void setLocalImagem(String localImagem) {
        this.localImagem = localImagem;
    }

    public LocalDate getDataDoPedido() {
        return dataDoPedido;
    }

    public void setDataDoPedido(LocalDate dataDoPedido) {
        this.dataDoPedido = dataDoPedido;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void getEquipamento(String string) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
