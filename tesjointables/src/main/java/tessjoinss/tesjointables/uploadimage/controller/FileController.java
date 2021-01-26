package tessjoinss.tesjointables.uploadimage.controller;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tessjoinss.tesjointables.exception.ResourceNotFoundException;
import tessjoinss.tesjointables.repository.PostRepository;
import tessjoinss.tesjointables.uploadimage.message.ResponseFile;
import tessjoinss.tesjointables.uploadimage.message.ResponseMessage;
import tessjoinss.tesjointables.uploadimage.model.FileDB;
import tessjoinss.tesjointables.uploadimage.repository.FileDBRepository;
import tessjoinss.tesjointables.uploadimage.service.FileStorageService;

import javax.validation.Valid;

@Controller
@CrossOrigin("http://localhost:8081")

public class FileController {

    @Autowired
    private FileStorageService storageService;

    @Autowired
    private FileDBRepository fileDBRepository;


    @Autowired
    private PostRepository postRepository;

    @GetMapping("/posts/{postId}/comments")
    public Page<Comment> getAllCommentsByPostId(@PathVariable (value = "postId") Long postId,
                                                Pageable pageable) {
        return commentRepository.findByPostId(postId, pageable);
    }

    @PostMapping("/posts/{postId}/comments")
    public Comment createComment(@PathVariable (value = "postId") Long postId,
                                 @Valid @RequestBody Comment comment) {
        return postRepository.findById(postId).map(post -> {
            comment.setPost(post);
            return commentRepository.save(comment);
        }).orElseThrow(() -> new ResourceNotFoundException("PostId " + postId + " not found"));
    }

    @PostMapping("/posts/{postId}/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@PathVariable (value = "postId") Long postId,@RequestParam("file") MultipartFile file, @RequestParam (required = false) String imageDesc,@RequestParam (required = false) String imagePrice, @RequestParam (required = false)String imageLocation,@Valid @RequestBody FileDB fileDB) {
        String message = "";

        try {
            storageService.store(file);

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }

        return postRepository.findById(postId).map(post -> {
            fileDB.setPost(post);
            return fileDBRepository.save(fileDB);

        }).orElseThrow(() -> new ResourceNotFoundException("PostId " + postId + " not found"));

    }
/*
    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            storageService.store(file);

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

 */

    @GetMapping("/files")
    public ResponseEntity<List<ResponseFile>> getListFiles() {
        List<ResponseFile> files = storageService.getAllFiles().map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/files/")
                    .path(dbFile.getId())
                    .toUriString();

            return new ResponseFile(
                    dbFile.getName(),
                    fileDownloadUri,
                    dbFile.getType(),
                    dbFile.getData().length);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping("/files/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        FileDB fileDB = storageService.getFile(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
                .body(fileDB.getData());
    }


}
