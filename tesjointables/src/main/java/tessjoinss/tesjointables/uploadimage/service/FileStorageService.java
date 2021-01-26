package tessjoinss.tesjointables.uploadimage.service;


import java.io.IOException;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import tessjoinss.tesjointables.uploadimage.model.FileDB;
import tessjoinss.tesjointables.uploadimage.repository.FileDBRepository;

@Service
public class FileStorageService {

    @Autowired
    private FileDBRepository fileDBRepository;

    public FileDB store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileDesc =StringUtils.cleanPath(file.getContentType());
        String filePrice = StringUtils.cleanPath(file.getContentType());
        String filelocation= StringUtils.cleanPath(file.getContentType());
        FileDB FileDB = new FileDB(fileName, file.getContentType(), file.getBytes(),fileDesc,filePrice,filelocation);

        return fileDBRepository.save(FileDB);
    }

    public FileDB getFile(String id) {
        return fileDBRepository.findById(id).get();
    }

    public Stream<FileDB> getAllFiles() {
        return fileDBRepository.findAll().stream();
    }
}
