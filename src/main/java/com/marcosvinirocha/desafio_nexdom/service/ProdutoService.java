package com.marcosvinirocha.desafio_nexdom.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marcosvinirocha.desafio_nexdom.entity.Produto;
import com.marcosvinirocha.desafio_nexdom.repository.ProdutoRespository;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRespository produtoRespository;

    public List<Produto> findAll() {
        return produtoRespository.findAll();
    }

    public Produto findById(Long id) {
        return produtoRespository.findById(id).orElse(null);
    }

    public Produto save(Produto produto) {
        return produtoRespository.save(produto);
    }

    public void delete(Long id) {
        produtoRespository.deleteById(id);
    }

}
