package tessjoinss.tesjointables.service;


import java.io.IOException;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import tessjoinss.tesjointables.model.Comment;
import tessjoinss.tesjointables.repository.CommentRepository;


@Service
public class FileStorageService {

    @Autowired
    private CommentRepository commentRepository;

    public Comment store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Comment FileDB = new Comment(fileName, file.getContentType(), file.getBytes());

        return commentRepository.save(FileDB);
    }

    public Comment getFile(Long id) {
        return commentRepository.findById(id).get();
    }

    public Stream<Comment> getAllFiles() {
        return commentRepository.findAll().stream();
    }
}
