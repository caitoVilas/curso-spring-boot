package com.caito.springboot.app.models.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UploadFileServiceImpl implements IUploadFileService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final static String UPLOAD_FOLDER = "upload";

    @Override
    public Resource load(String filename) throws MalformedURLException{
        Path pathFoto = getPath(filename);
        Resource recurso = null;


            recurso = new UrlResource(pathFoto.toUri());
            if (!recurso.exists() || !recurso.isReadable()){
                throw new RuntimeException("Error : no se puede cargar la imagen"+pathFoto.toString());
            }


        return recurso;
    }

    @Override
    public String copy(MultipartFile file) throws IOException {

        String uniqueFilename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path rootPath = getPath(uniqueFilename);

        logger.info("rootPath : ", rootPath);

            Files.copy(file.getInputStream(), rootPath);

        return uniqueFilename;
    }

    @Override
    public Boolean delete(String filename) {

        Path rootPath = getPath(filename);
        File archivo = rootPath.toFile();
        if (archivo.exists() && archivo.canRead()){
            if (archivo.delete()){
                return true;
            }
        }
        return false;
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(Paths.get(UPLOAD_FOLDER).toFile());
    }

    @Override
    public void init() throws IOException {

     Files.createDirectories(Paths.get(UPLOAD_FOLDER));

    }

    public Path getPath(String filename){

        return Paths.get(UPLOAD_FOLDER).resolve(filename).toAbsolutePath();
    }
}
