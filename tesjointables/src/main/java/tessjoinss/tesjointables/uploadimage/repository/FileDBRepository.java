package tessjoinss.tesjointables.uploadimage.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tessjoinss.tesjointables.model.Comment;
import tessjoinss.tesjointables.uploadimage.model.FileDB;

import java.util.Optional;


@Repository
public interface FileDBRepository extends JpaRepository<FileDB, String> {
    Page<FileDB> findByPostId(Long postId, Pageable pageable);
    Optional<FileDB> findByIdAndPostId(String id,  Long postId);

}
