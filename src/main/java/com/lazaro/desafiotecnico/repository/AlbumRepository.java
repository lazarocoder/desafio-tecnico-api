package com.lazaro.desafiotecnico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lazaro.desafiotecnico.model.Album;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {

}
