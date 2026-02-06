package com.marcosvinirocha.desafio_nexdom.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marcosvinirocha.desafio_nexdom.dto.LucroProdutoResponseDTO;
import com.marcosvinirocha.desafio_nexdom.dto.MovimentoEstoqueRequestDTO;
import com.marcosvinirocha.desafio_nexdom.dto.MovimentoEstoqueResponseDTO;
import com.marcosvinirocha.desafio_nexdom.entity.MovimentoEstoque;
import com.marcosvinirocha.desafio_nexdom.entity.Produto;
import com.marcosvinirocha.desafio_nexdom.entity.enums.TipoMovimentacao;
import com.marcosvinirocha.desafio_nexdom.exception.EstoqueInsuficienteException;
import com.marcosvinirocha.desafio_nexdom.repository.MovimentacaoRepository;
import com.marcosvinirocha.desafio_nexdom.repository.ProdutoRespository;

@Service
public class MovimentacaoEstoqueService {

    private final ProdutoRespository produtoRespository;
    private final MovimentacaoRepository movimentacaoRepository;

    public MovimentacaoEstoqueService(ProdutoRespository produtoRespository,
            MovimentacaoRepository movimentacaoRepository) {
        this.produtoRespository = produtoRespository;
        this.movimentacaoRepository = movimentacaoRepository;
    }

    @Transactional
    public void movimentar(MovimentoEstoqueRequestDTO dto) {
        Produto produto = produtoRespository.findById(dto.produtoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        if (dto.tipoMovimentacao() == TipoMovimentacao.ENTRADA) {
            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + dto.quantidade());
        } else {
            if (produto.getQuantidadeEstoque() < dto.quantidade()) {
                throw new EstoqueInsuficienteException("Estoque insuficiente");
            }
            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - dto.quantidade());
        }

        produtoRespository.save(produto);

        MovimentoEstoque movimentoEstoque = new MovimentoEstoque();
        movimentoEstoque.setProdutoId(dto.produtoId());
        movimentoEstoque.setTipoMovimentacao(dto.tipoMovimentacao());
        movimentoEstoque.setValorVenda(dto.valorVenda());
        movimentoEstoque.setDataMovimentacao(LocalDateTime.now());
        movimentoEstoque.setQuantidade(dto.quantidade());

        movimentacaoRepository.save(movimentoEstoque);
    }
}
