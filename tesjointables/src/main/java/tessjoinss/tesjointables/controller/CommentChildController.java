package tessjoinss.tesjointables.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tessjoinss.tesjointables.exception.ResourceNotFoundException;
import tessjoinss.tesjointables.model.Comment;
import tessjoinss.tesjointables.model.CommentChild;
import tessjoinss.tesjointables.repository.CommentChildRepository;
import tessjoinss.tesjointables.repository.CommentRepository;
import tessjoinss.tesjointables.repository.PostRepository;

import javax.validation.Valid;

@RestController
public class CommentChildController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentChildRepository commentChildRepository;

    @GetMapping("/comments/{commentId}/commentschild")
    public Page<CommentChild> getAllCommentChildsByCommentId(@PathVariable (value = "commentId") Long commentId,
                                                     Pageable pageable) {
        return commentChildRepository.findByCommentId(commentId, pageable);
    }

    @PostMapping("/comments/{commentId}/commentschild")
    public CommentChild createCommentChild(@PathVariable (value = "commentId") Long commentId,
                                 @Valid @RequestBody CommentChild commentChild) {
        return commentRepository.findById(commentId).map(comment -> {
            commentChild.setComment(comment);
            return commentChildRepository.save(commentChild);
        }).orElseThrow(() -> new ResourceNotFoundException("CommentId " + commentId + " not found"));
    }
/*
    @PutMapping("/comment/{commentId}/commentschild/{commentchildId}")
    public Comment updateComment(@PathVariable (value = "postId") Long postId,
                                 @PathVariable (value = "commentId") Long commentId,
                                 @Valid @RequestBody Comment commentRequest) {
        if(!postRepository.existsById(postId)) {
            throw new ResourceNotFoundException("PostId " + postId + " not found");
        }

        return commentRepository.findById(commentId).map(comment -> {
            comment.setText(commentRequest.getText());
            return commentRepository.save(comment);
        }).orElseThrow(() -> new ResourceNotFoundException("CommentId " + commentId + "not found"));
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable (value = "postId") Long postId,
                                           @PathVariable (value = "commentId") Long commentId) {
        return commentRepository.findByIdAndPostId(commentId, postId).map(comment -> {
            commentRepository.delete(comment);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Comment not found with id " + commentId + " and postId " + postId));
    }

 */
}
