package file.upload.service;


import file.upload.entity.FileData;
import file.upload.repository.StorageRepository;
import file.upload.util.ImageUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

//@Service
//public class StorageService {
//
//    @Autowired
//    private StorageRepository repository;
//
//    public String uploadImage(MultipartFile file) throws IOException {
//
//        String fileName = file.getOriginalFilename();
//        String fileExtension = getFileExtension(fileName);
//
//        // Generate a unique file name
//        String uniqueFileName = generateUniqueFileName(fileExtension);
//
//        // Save the image file to the file system
//        saveImageToFileSystem(file, uniqueFileName);
//
//        // Save the file details to the database
//        FileData imageData = repository.save(FileData.builder()
//                .name(fileName)
//                .type(file.getContentType())
//                .imagePath(uniqueFileName)
//                .build());
//
//        if (imageData != null) {
//            return "File uploaded successfully: " + fileName;
//        }
//        return null;
//    }
//
//    public Resource downloadImage(String fileName) throws FileNotFoundException {
//        Optional<FileData> dbImageData = repository.findByName(fileName);
//
//        if (dbImageData.isPresent()) {
//            String imagePath = dbImageData.get().getImagePath();
//            File file = new File(imagePath);
//
//            if (file.exists()) {
//                return (Resource) new FileSystemResource(file);
//            }
//        }
//
//        throw new FileNotFoundException("Image file not found: " + fileName);
//    }
//
//    private String getFileExtension(String fileName) {
//        int dotIndex = fileName.lastIndexOf(".");
//        return (dotIndex == -1) ? "" : fileName.substring(dotIndex);
//    }
//
//    private String generateUniqueFileName(String fileExtension) {
//        return UUID.randomUUID().toString() + fileExtension;
//    }
//
//    private void saveImageToFileSystem(MultipartFile file, String fileName) throws IOException {
//        Path filePath = Paths.get("your-file-upload-directory", fileName);
//        Files.write(filePath, file.getBytes());
//    }
//}

@Service
public class StorageService {

    @Autowired
    private StorageRepository repository;

    public String uploadImage(MultipartFile file) throws IOException {

        FileData imageData = repository.save(FileData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtils.compressImage(file.getBytes())).build());
        if (imageData != null) {
            return "file uploaded successfully : " + file.getOriginalFilename();
        }
        return null;
    }

    public byte[] downloadImage(String fileName){
        Optional<FileData> dbImageData = repository.findByName(fileName);
        byte[] images=ImageUtils.decompressImage(dbImageData.get().getImageData());
        return images;
    }
}