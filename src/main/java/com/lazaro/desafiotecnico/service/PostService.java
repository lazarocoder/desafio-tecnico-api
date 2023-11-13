package com.lazaro.desafiotecnico.service;

import static java.util.stream.Collectors.toList;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lazaro.desafiotecnico.dto.PostDto;
import com.lazaro.desafiotecnico.dto.PostRequest;
import com.lazaro.desafiotecnico.exception.BaseException;
import com.lazaro.desafiotecnico.exception.PostNotFoundException;
import com.lazaro.desafiotecnico.model.Comment;
import com.lazaro.desafiotecnico.model.Post;
import com.lazaro.desafiotecnico.repository.CommentRepository;
import com.lazaro.desafiotecnico.repository.PostRepository;

@Service
public class PostService {

	@Autowired
	private AuthService authService;

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private CommentRepository commentRepository;

	@Transactional
	public List<PostDto> showAllPosts() {
		List<Post> posts = postRepository.findAll();
		return posts.stream().map(this::mapFromPostToDto).collect(toList());
	}

	@Transactional
	public void createPost(PostRequest postRequest) {
		Post post = mapFromDtoToPost(postRequest);
		postRepository.save(post);
	}

	@Transactional
	public PostDto updatePost(Long id, PostRequest postRequest) {
		Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("For id " + id));
		post.setTitulo(postRequest.getTitulo());
		post.setConteudo(postRequest.getConteudo());
		postRepository.save(post);
		return mapFromPostToDto(post);
	}

	@Transactional
	public PostDto readSinglePost(Long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("For id " + id));
		return mapFromPostToDto(post);
	}

	private PostDto mapFromPostToDto(Post post) {
		PostDto postDto = new PostDto();
		BeanUtils.copyProperties(post, postDto);
		return postDto;
	}

	private Post mapFromDtoToPost(PostRequest postRequest) {
		Post post = new Post();
		post.setTitulo(postRequest.getTitulo());
		post.setConteudo(postRequest.getConteudo());
		User loggedInUser = authService.getCurrentUser()
				.orElseThrow(() -> new IllegalArgumentException("User Not Found"));
		post.setDtCriacao(LocalDateTime.now());
		post.setUserName(loggedInUser.getUsername());
		return post;
	}

	@Transactional
	public void deletePost(Long id) {
		User loggedInUser = authService.getCurrentUser()
				.orElseThrow(() -> new IllegalArgumentException("User Not Found"));
		PostDto postDto = readSinglePost(id);
		if (!loggedInUser.getUsername().equals(postDto.getUserName())) {
			throw new BaseException("Apenas o criador do post poderá ter permissão para excluí-lo.");
		}
		List<Comment> comments = commentRepository.findAllByPostId(id);
		for (Comment comment : comments) {
			commentRepository.delete(comment);
		}
		postRepository.deleteById(id);
	}
}
