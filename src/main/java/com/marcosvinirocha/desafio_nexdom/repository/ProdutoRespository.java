package com.marcosvinirocha.desafio_nexdom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marcosvinirocha.desafio_nexdom.entity.Produto;

public interface ProdutoRespository extends JpaRepository<Produto, Long> {

}
