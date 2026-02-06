package com.marcosvinirocha.desafio_nexdom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marcosvinirocha.desafio_nexdom.entity.MovimentoEstoque;

public interface MovimentacaoRepository extends JpaRepository<MovimentoEstoque, Long> {

}
