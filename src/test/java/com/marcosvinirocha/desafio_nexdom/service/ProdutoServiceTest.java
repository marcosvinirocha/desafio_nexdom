package com.marcosvinirocha.desafio_nexdom.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.marcosvinirocha.desafio_nexdom.entity.Produto;
import com.marcosvinirocha.desafio_nexdom.entity.enums.TipoProduto;
import com.marcosvinirocha.desafio_nexdom.repository.ProdutoRepository;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    @Test
    void deveCriarProdutoComSucesso() {
        Produto produto = new Produto();
        produto.setDescricao("Notebook");
        produto.setTipo(TipoProduto.ELETRONICO);
        produto.setValorFornecedor(BigDecimal.valueOf(1000.0));
        produto.setQuantidadeEstoque(10);

        when(produtoRepository.save(any())).thenReturn(produto);

        Produto resultado = produtoService.save(produto);

        assertNotNull(resultado);
        verify(produtoRepository).save(produto);
    }

    @Test
    void deveBuscarProdutoPorId() {
        Produto produto = new Produto();
        produto.setId(1L);

        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));

        Produto resultado = produtoService.findById(1L);

        assertEquals(1L, resultado.getId());
    }

    @Test
    void deveLancarExceptionQuandoProdutoNaoExiste() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> produtoService.findById(1L));
    }

    @Test
    void deveAtualizarProdutoComSucesso() {
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setDescricao("Notebook");
        produto.setTipo(TipoProduto.ELETRONICO);
        produto.setValorFornecedor(BigDecimal.valueOf(1000.0));
        produto.setQuantidadeEstoque(10);

        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        when(produtoRepository.save(any())).thenReturn(produto);

        Produto resultado = produtoService.update(1L, produto);

        assertNotNull(resultado);
        verify(produtoRepository).save(produto);
    }

    @Test
    void deveDeletarProdutoComSucesso() {
        produtoService.delete(1L);

        verify(produtoRepository).deleteById(1L);
    }
}
