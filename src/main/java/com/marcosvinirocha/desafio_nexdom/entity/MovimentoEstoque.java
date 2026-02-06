package com.marcosvinirocha.desafio_nexdom.entity;

import java.time.LocalDateTime;

import com.marcosvinirocha.desafio_nexdom.entity.enums.TipoMovimentacao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "movimento_estoque")
public class MovimentoEstoque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "produto_id")
    private Long produtoId;

    private TipoMovimentacao tipoMovimentacao;
    private Integer valorVenda;
    private LocalDateTime dataMovimentacao;
    private Integer quantidade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id", insertable = false, updatable = false)
    private Produto produto;

    public MovimentoEstoque() {
    }

    public MovimentoEstoque(Long id, Long produtoId, TipoMovimentacao tipoMovimentacao, Integer valorVenda,
            LocalDateTime dataMovimentacao, Integer quantidade) {
        this.id = id;
        this.produtoId = produtoId;
        this.tipoMovimentacao = tipoMovimentacao;
        this.valorVenda = valorVenda;
        this.dataMovimentacao = dataMovimentacao;
        this.quantidade = quantidade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public TipoMovimentacao getTipoMovimentacao() {
        return tipoMovimentacao;
    }

    public void setTipoMovimentacao(TipoMovimentacao tipoMovimentacao) {
        this.tipoMovimentacao = tipoMovimentacao;
    }

    public Integer getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(Integer valorVenda) {
        this.valorVenda = valorVenda;
    }

    public LocalDateTime getDataMovimentacao() {
        return dataMovimentacao;
    }

    public void setDataMovimentacao(LocalDateTime dataMovimentacao) {
        this.dataMovimentacao = dataMovimentacao;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return "MovimentoEstoque [id=" + id + ", produtoId=" + produtoId + ", tipoMovimentacao=" + tipoMovimentacao
                + ", valorVenda=" + valorVenda + ", dataMovimentacao=" + dataMovimentacao + ", quantidade="
                + quantidade + "]";
    }
}
