package com.devskiller.tasks.blog.service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.devskiller.tasks.blog.model.Comment;
import com.devskiller.tasks.blog.repository.CommentRepository;
import io.micrometer.core.annotation.Timed;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.devskiller.tasks.blog.model.dto.CommentDto;
import com.devskiller.tasks.blog.model.dto.NewCommentDto;

@Service
public class CommentService {

	private final CommentRepository commentRepository;

	public CommentService(final CommentRepository commentRepository) {
		this.commentRepository = commentRepository;
	}

	/**
	 * Returns a list of all comments for a blog post with passed id.
	 *
	 * @param postId id of the post
	 * @return list of comments sorted by creation date descending - most recent first
	 */

	@Timed(value = "getCommentsForPost.time", description = "Time taken to fetch comments")
	public List<CommentDto> getCommentsForPost(Long postId) {
		return
			this.commentRepository
				.findByPostId(postId)
				.stream()
				//Map comment to DTO
				.map(cm ->
				new CommentDto(cm.getId(),cm.getComment(),cm.getAuthor(),cm.getCreationDate()))
			//Sort by creation date in descending order by reversing using Comparator
				.sorted(Comparator.comparing(CommentDto::creationDate).reversed())
				.collect(Collectors.toList());
	}

	/**
	 * Creates a new comment
	 *
	 * @param postId id of the post
	 * @param newCommentDto data of new comment
	 * @return id of the created comment
	 *
	 * @throws IllegalArgumentException if postId is null or there is no blog post for passed postId
	 */
	@Transactional
	@Timed(value = "addComment.time", description = "Time taken to add comment")
	public Long addComment(Long postId, NewCommentDto newCommentDto) {
			Comment comment = new Comment();
			comment.setPostId(postId);
			comment.setAuthor(newCommentDto.author());
			comment.setComment(newCommentDto.content());
			comment.setCreationDate(LocalDateTime.now());
			commentRepository.save(comment);
			return comment.getId();
	}
}
