package com.devskiller.tasks.blog.rest;

import com.devskiller.tasks.blog.model.dto.CommentDto;
import com.devskiller.tasks.blog.model.dto.NewCommentDto;
import com.devskiller.tasks.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class CommentController {

	private final CommentService commentService;


	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}

	@GetMapping("/{id}/comments")
	@ResponseStatus(HttpStatus.OK)
	public List<CommentDto> retrieveComments(@PathVariable("id") Long postId){
		return this.commentService.getCommentsForPost(postId);
	}

	@PostMapping("/{id}/comments")
	@ResponseStatus(HttpStatus.CREATED)
	public void addOrUpdateComment(@PathVariable("id") Long postId, @RequestBody NewCommentDto newCommentDto){
		commentService.addComment(postId,newCommentDto);
	}
}
