package com.ecirstea.multimedia.service;

import com.ecirstea.multimedia.model.MultimediaFile;

import java.util.List;
import java.util.UUID;

public interface MultimediaApiService {
     MultimediaFile save(MultimediaFile multimediaFile );

     MultimediaFile edit(MultimediaFile multimediaFile, boolean throwExceptionIfNotExist );

     MultimediaFile delete( UUID id );

     MultimediaFile findById( UUID id );

     List<MultimediaFile> findAll();
}
