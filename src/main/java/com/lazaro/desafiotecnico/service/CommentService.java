package com.lazaro.desafiotecnico.service;

import static java.util.stream.Collectors.toList;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lazaro.desafiotecnico.dto.CommentDto;
import com.lazaro.desafiotecnico.dto.CommentRequest;
import com.lazaro.desafiotecnico.dto.PostDto;
import com.lazaro.desafiotecnico.exception.BaseException;
import com.lazaro.desafiotecnico.exception.CommentNotFoundException;
import com.lazaro.desafiotecnico.model.Comment;
import com.lazaro.desafiotecnico.repository.CommentRepository;

@Service
public class CommentService {

	@Autowired
	private AuthService authService;

	@Autowired
	private PostService postService;

	@Autowired
	private CommentRepository commentRepository;

	@Transactional(readOnly = true)
	public List<CommentDto> showAllComments() {
		List<Comment> comments = commentRepository.findAll();
		return comments.stream().map(this::mapFromCommentToDto).collect(toList());
	}

	@Transactional
	public void createComment(CommentRequest commentRequest) {
		Comment comment = mapFromDtoToComment(commentRequest);
		commentRepository.save(comment);
	}

	@Transactional
	public CommentDto updateComment(Long id, CommentRequest commentRequest) {
		Comment comment = commentRepository.findById(id)
				.orElseThrow(() -> new CommentNotFoundException("For id " + id));
		comment.setConteudo(commentRequest.getConteudo());
		commentRepository.save(comment);
		return mapFromCommentToDto(comment);
	}

	@Transactional(readOnly = true)
	public CommentDto readSingleComment(Long id) {
		Comment comment = commentRepository.findById(id)
				.orElseThrow(() -> new CommentNotFoundException("For id " + id));
		return mapFromCommentToDto(comment);
	}

	private CommentDto mapFromCommentToDto(Comment comment) {
		CommentDto commentDto = new CommentDto();
		BeanUtils.copyProperties(comment, commentDto);
		return commentDto;
	}

	private Comment mapFromDtoToComment(CommentRequest commentRequest) {
		Comment comment = new Comment();
		comment.setConteudo(commentRequest.getConteudo());
		PostDto postDto = postService.readSinglePost(commentRequest.getPostId());
		comment.setPostId(postDto.getId());
		User loggedInUser = authService.getCurrentUser()
				.orElseThrow(() -> new IllegalArgumentException("User Not Found"));
		comment.setDtCriacao(LocalDateTime.now());
		comment.setUserName(loggedInUser.getUsername());
		return comment;
	}

	@Transactional
	public void deleteComment(Long id) {
		User loggedInUser = authService.getCurrentUser()
				.orElseThrow(() -> new IllegalArgumentException("User Not Found"));
		CommentDto commentDto = readSingleComment(id);
		if (!loggedInUser.getUsername().equals(commentDto.getUserName())) {
			throw new BaseException("Apenas o criador do comentário poderá ter permissão para excluí-lo.");
		}
		commentRepository.deleteById(id);
	}

	@Transactional(readOnly = true)
	public List<CommentDto> showAllCommentsByPostId(Long id) {
		List<Comment> comments = commentRepository.findAllByPostId(id);
		return comments.stream().map(this::mapFromCommentToDto).collect(toList());
	}
}
