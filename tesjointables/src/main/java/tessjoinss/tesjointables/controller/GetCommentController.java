package tessjoinss.tesjointables.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tessjoinss.tesjointables.exception.ResourceNotFoundException;
import tessjoinss.tesjointables.model.Comment;
import tessjoinss.tesjointables.model.GetComment;
import tessjoinss.tesjointables.repository.CommentRepository;
import tessjoinss.tesjointables.repository.GetCommentRepository;
import tessjoinss.tesjointables.repository.PostRepository;

import javax.validation.Valid;

@RestController
public class GetCommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private GetCommentRepository getCommentRepository;

    @GetMapping("/comments/{commentId}/getcomments")
    public Page<GetComment> getAllCommentsByCommentId(@PathVariable (value = "commentId") Long commentId,
                                                   Pageable pageable) {
        return getCommentRepository.findByCommentId(commentId, pageable);
    }

    @PostMapping("/comments/{commentId}/getcomments")
    public GetComment createGetComment(@PathVariable (value = "commentId") Long commentId,
                                 @Valid @RequestBody GetComment getComment) {
        return commentRepository.findById(commentId).map(comment -> {
            getComment.setComment(comment);
            return getCommentRepository.save(getComment);
        }).orElseThrow(() -> new ResourceNotFoundException("commenttId " + commentId + " not found"));
    }
/*
    @PutMapping("/posts/{postId}/comments/{commentId}")
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
