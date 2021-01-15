package tessjoinss.tesjointables.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import tessjoinss.tesjointables.model.Comment;
import tessjoinss.tesjointables.model.GetComment;

import java.util.Optional;

public interface GetCommentRepository extends JpaRepository<GetComment, Long> {
    Page<GetComment> findByCommentId(Long commentId, Pageable pageable);
    Optional<GetComment> findByIdAndCommentId(Long id, Long commentId);

}
