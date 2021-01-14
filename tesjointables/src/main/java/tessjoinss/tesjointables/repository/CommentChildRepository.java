package tessjoinss.tesjointables.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import tessjoinss.tesjointables.model.Comment;
import tessjoinss.tesjointables.model.CommentChild;

import java.util.Optional;

public interface CommentChildRepository extends JpaRepository<CommentChild, Long> {
    Page<CommentChild> findByCommentId(Long commentId, Pageable pageable);
    Optional<CommentChild> findByIdAndCommentId(Long id, Long commentId);

}
