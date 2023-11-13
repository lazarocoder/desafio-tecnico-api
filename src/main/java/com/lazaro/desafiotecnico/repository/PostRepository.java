package com.lazaro.desafiotecnico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lazaro.desafiotecnico.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}
