package com.marcosvinirocha.desafio_nexdom.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import com.marcosvinirocha.desafio_nexdom.dto.LucroProdutoResponseDTO;
import com.marcosvinirocha.desafio_nexdom.dto.MovimentoEstoqueResponseDTO;
import com.marcosvinirocha.desafio_nexdom.entity.MovimentoEstoque;
import com.marcosvinirocha.desafio_nexdom.entity.Produto;
import com.marcosvinirocha.desafio_nexdom.entity.enums.TipoMovimentacao;
import com.marcosvinirocha.desafio_nexdom.entity.enums.TipoProduto;

@DataJpaTest
public class ProdutoRepositoryTest {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private MovimentacaoRepository movimentoRepository;

    @Test
    void deveSalvarProduto() {
        Produto produto = new Produto();
        produto.setDescricao("Teclado");
        produto.setQuantidadeEstoque(5);

        Produto salvo = produtoRepository.save(produto);

        assertNotNull(salvo.getId());
    }

    @Test
    void deveBuscarProdutoPorId() {
        Produto produto = new Produto();
        produto.setDescricao("Mouse");
        produto.setQuantidadeEstoque(10);
        Produto salvo = produtoRepository.save(produto);

        Produto encontrado = produtoRepository.findById(salvo.getId()).orElse(null);

        assertNotNull(encontrado);
    }

    @Test
    void deveAtualizarProduto() {
        Produto produto = new Produto();
        produto.setDescricao("Monitor");
        produto.setQuantidadeEstoque(15);
        Produto salvo = produtoRepository.save(produto);

        salvo.setQuantidadeEstoque(20);
        Produto atualizado = produtoRepository.save(salvo);

        assertNotNull(atualizado.getId());
    }

    @Test
    void deveDeletarProduto() {
        Produto produto = new Produto();
        produto.setDescricao("Cadeira");
        produto.setQuantidadeEstoque(25);
        Produto salvo = produtoRepository.save(produto);

        produtoRepository.delete(salvo);

        assertNull(produtoRepository.findById(salvo.getId()).orElse(null));
    }

    @Test
    void deveBuscarTodosOsProdutos() {
        Produto produto1 = new Produto();
        produto1.setDescricao("Produto 1");
        produto1.setQuantidadeEstoque(1);
        produtoRepository.save(produto1);

        Produto produto2 = new Produto();
        produto2.setDescricao("Produto 2");
        produto2.setQuantidadeEstoque(2);
        produtoRepository.save(produto2);

        List<Produto> produtos = produtoRepository.findAll();

        assertNotNull(produtos);
        assertEquals(2, produtos.size());
    }

    @Test
    void deveConsultarProdutosPorTipoComSaidaEEstoque() {
        Produto produto = new Produto();
        produto.setCodigo("P001");
        produto.setDescricao("Produto 1");
        produto.setTipo(TipoProduto.ELETRONICO);
        produto.setQuantidadeEstoque(10);
        produto = produtoRepository.save(produto);

        MovimentoEstoque saida = new MovimentoEstoque();
        saida.setProdutoId(produto.getId());
        saida.setTipoMovimentacao(TipoMovimentacao.SAIDA);
        saida.setQuantidade(3);
        movimentoRepository.save(saida);

        List<MovimentoEstoqueResponseDTO> resultado = produtoRepository.findResumoPorTipo();

        assertFalse(resultado.isEmpty());

        MovimentoEstoqueResponseDTO dto = resultado.get(0);
        assertEquals(10, dto.quantidadeDisponivel());
        assertEquals(3L, dto.quantidadeTotalSaida());
        assertEquals(TipoProduto.ELETRONICO, dto.tipo());
        assertEquals("P001", dto.codigo());
        assertEquals("Produto 1", dto.descricao());
    }

    @Test
    void deveConsultarLucroPorProduto() {
        Produto produto = new Produto();
        produto.setCodigo("P001");
        produto.setDescricao("Produto 1");
        produto.setTipo(TipoProduto.ELETRONICO);
        produto.setQuantidadeEstoque(10);
        produto = produtoRepository.save(produto);

        MovimentoEstoque saida = new MovimentoEstoque();
        saida.setProdutoId(produto.getId());
        saida.setTipoMovimentacao(TipoMovimentacao.SAIDA);
        saida.setQuantidade(3);
        saida.setValorVenda(BigDecimal.valueOf(33));
        movimentoRepository.save(saida);

        List<LucroProdutoResponseDTO> resultado = produtoRepository.findLucroPorProduto();

        assertFalse(resultado.isEmpty());

        LucroProdutoResponseDTO dto = resultado.get(0);
        assertEquals(produto.getId(), dto.produtoId());
        assertEquals("P001", dto.codigo());
        assertEquals("Produto 1", dto.descricao());
        assertEquals(3L, dto.quantidadeTotalSaida());
    }
}
