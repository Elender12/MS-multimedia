package com.ecirstea.multimedia.controller;


import com.ecirstea.multimedia.exception.MultimediaException;
import com.ecirstea.multimedia.model.MultimediaFile;
import com.ecirstea.multimedia.service.MultimediaApiServiceImpl;
import com.ecirstea.multimedia.storage.Storage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


@RestController
public class MultimediaApiController implements MultimediaApi
{

    @Autowired
    @Qualifier("FTP")
    private Storage storage;

    @Autowired
    private MultimediaApiServiceImpl multimediaService;

    private static final Logger logger = LogManager.getLogger(MultimediaApiController.class);


    public ResponseEntity<MultimediaFile> addMultimedia(MultipartFile fileData )
    {
        logger.info( "Received addMultimedia request");
        MultimediaFile multimedia = null;

        try
        {
            System.out.println("name is: "+fileData.getOriginalFilename());
            multimedia = new
                    MultimediaFile(fileData.getOriginalFilename(), fileData.getContentType(), fileData.getSize());
//            multimedia = new MultimediaFile("testName", fileData.getContentType(), fileData.getSize());

            multimedia = multimediaService.save(multimedia);

            boolean success = storage.write(multimedia.getId(), fileData.getBytes());
//            boolean success = storage.write(UUID.fromString("344d1931-42c2-4782-a5a7-1694b4de4acf"), fileData.getBytes());

            if( !success )
            {
                multimediaService.delete(multimedia.getId());
            }
        }
        catch( IOException ex )
        {
            ex.printStackTrace();
        }

        return ResponseEntity.ok().body(multimedia);
    }



    public ResponseEntity<MultimediaFile> updateMultimedia( UUID id, boolean throwExceptionIfNotExist, MultipartFile fileData )
    {
        logger.info( "Received updateMultimedia request");
        MultimediaFile multimedia = null;

        try
        {
            multimedia = new MultimediaFile(id.toString(), fileData.getOriginalFilename(), fileData.getContentType(), fileData.getSize());
            multimedia = multimediaService.edit(multimedia, throwExceptionIfNotExist);

            boolean success = storage.write(multimedia.getId(), fileData.getBytes());

            if( !success )
            {
                multimediaService.delete(multimedia.getId());
            }
        }
        catch( IOException ex )
        {
            ex.printStackTrace();
        }

        return ResponseEntity.ok().body(multimedia);
    }



    public ResponseEntity<MultimediaFile> deleteMultimedia( UUID id )
    {
        logger.info( "Received addMultimedia request");

        MultimediaFile multimedia = multimediaService.delete(id);

        storage.delete(multimedia.getId());

        return ResponseEntity.ok().body(multimedia);
    }



    public ResponseEntity<MultimediaFile> getMultimediaByID( UUID id )
    {
        logger.info( "Received getMultimediaByID request");
        return ResponseEntity.ok().body(multimediaService.findById(id));
    }



    public ResponseEntity<List<MultimediaFile>> getAllMultimedias()
    {
        logger.info( "Received getAllMultimedias request");
        return ResponseEntity.ok().body(multimediaService.findAll());
    }



    //ResponseEntity<byte[]>
    public ResponseEntity<Resource> downloadFileByID( UUID id )
    {
        MultimediaFile multimedia;
        byte[] data;
        try
        {
            multimedia = multimediaService.findById(id);
            multimedia.setDownloadCounter(multimedia.getDownloadCounter() + 1);
            multimedia = multimediaService.edit(multimedia, true);

            data = storage.read(multimedia.getId());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentDispositionFormData("attachment", multimedia.getFullName());
            headers.setContentType(MediaType.valueOf(multimedia.getMediaType()));
            headers.setContentLength(multimedia.getLength());
            //return new ResponseEntity<>(data, headers, HttpStatus.OK);
            InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(data));
            return ResponseEntity.ok()
                .headers(headers)
                .contentLength(multimedia.getLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
        }
        catch( MultimediaException ex )
        {
            throw new MultimediaException(HttpStatus.NOT_FOUND, "fileID");
        }
        catch( Exception ex )
        {
            ex.printStackTrace();
            throw new MultimediaException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

}
