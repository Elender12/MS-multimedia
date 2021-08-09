package com.ecirstea.multimedia.controller;

import com.ecirstea.multimedia.model.MultimediaFile;
import io.swagger.annotations.*;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.util.List;
import java.util.UUID;


@Api(value = "Multimedias", description = "the Multimedia API")
@RestController
public interface MultimediaApi
{

    @ApiOperation(value = "Add a new Multimedia.", nickname = "addMultimedia", notes = "Takes a Multimedia object, saves it, and returns it with the saved id.", response = MultimediaFile.class, tags = {"Multimedias"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK.", response = MultimediaFile.class),
        @ApiResponse(code = 400, message = "Invalid Multimedia.", response = Error.class),
        @ApiResponse(code = 401, message = "Unauthorized.", response = Error.class),
        @ApiResponse(code = 403, message = "Forbidden.", response = Error.class),
        @ApiResponse(code = 404, message = "Not found.", response = Error.class),
        @ApiResponse(code = 500, message = "Server error.", response = Error.class)})
    @RequestMapping(
        value = "/multimedias",
        method = RequestMethod.POST,
        consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    ResponseEntity<MultimediaFile> addMultimedia( @ApiParam(value = "MultipartFile to update", required = true) @RequestPart(value = "file") MultipartFile fileData );


    @ApiOperation(value = "Update an existing Multimedia.", nickname = "updateMultimedia", notes = "Takes an existing Multimedia, updates it, and returns the new object.", response = MultimediaFile.class, tags = {"Multimedias"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK.", response = MultimediaFile.class),
        @ApiResponse(code = 400, message = "Invalid Multimedia.", response = Error.class),
        @ApiResponse(code = 401, message = "Unauthorized.", response = Error.class),
        @ApiResponse(code = 403, message = "Forbidden.", response = Error.class),
        @ApiResponse(code = 404, message = "Not found.", response = Error.class),
        @ApiResponse(code = 500, message = "Server error.", response = Error.class)})
    @RequestMapping(
        value = "/multimedias",
        method = RequestMethod.PUT,
        consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    ResponseEntity<MultimediaFile> updateMultimedia(
        @ApiParam(value = "Multimedia UUID to update", required = true) @RequestParam("id") UUID id,
        @ApiParam(value = "Throw an exception If the UUID doesn't exist (otherwise force to save/update it)") @RequestParam(value = "throwExceptionIfNotExist", defaultValue = "true") boolean throwExceptionIfNotExist,
        @ApiParam(value = "MultipartFile to update", required = true) @RequestPart("file") @Valid @NotNull @NotBlank MultipartFile fileData
    );


    @ApiOperation(value = "Delete an existing Multimedia by id.", nickname = "deleteMultimedia", notes = "Takes an existing Multimedia object, deletes it, and returns the deleted object.", response = MultimediaFile.class, tags = {"Multimedias"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK.", response = MultimediaFile.class),
        @ApiResponse(code = 401, message = "Unauthorized.", response = Error.class),
        @ApiResponse(code = 403, message = "Forbidden.", response = Error.class),
        @ApiResponse(code = 404, message = "Not found.", response = Error.class),
        @ApiResponse(code = 500, message = "Server error.", response = Error.class)})
    @RequestMapping(
        value = "/multimedias/{id}",
        method = RequestMethod.DELETE,
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    ResponseEntity<MultimediaFile> deleteMultimedia( @ApiParam(value = "Multimedia UUID to delete", required = true) @PathVariable("id") UUID id );


    @ApiOperation(value = "Get a Multimedia by id.", nickname = "getMultimediaByID", notes = "Returns one Multimedia by id.", response = MultimediaFile.class, tags = {"Multimedias"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK.", response = MultimediaFile.class),
        @ApiResponse(code = 401, message = "Unauthorized.", response = Error.class),
        @ApiResponse(code = 403, message = "Forbidden.", response = Error.class),
        @ApiResponse(code = 404, message = "Not found.", response = Error.class),
        @ApiResponse(code = 500, message = "Server error.", response = Error.class)})
    @RequestMapping(
        value = "/multimedias/{id}",
        method = RequestMethod.GET,
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    ResponseEntity<MultimediaFile> getMultimediaByID( @ApiParam(value = "Multimedia UUID to get", required = true) @PathVariable("id") UUID id );


    @ApiOperation(value = "Get all Multimedias.", nickname = "getAllMultimedias", notes = "Returns all Multimedia objects.", response = MultimediaFile.class, responseContainer = "List", tags = {"Multimedias"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK.", response = MultimediaFile.class, responseContainer = "List"),
        @ApiResponse(code = 401, message = "Unauthorized.", response = Error.class),
        @ApiResponse(code = 403, message = "Forbidden.", response = Error.class),
        @ApiResponse(code = 404, message = "Not found.", response = Error.class),
        @ApiResponse(code = 500, message = "Server error.", response = Error.class)})
    @RequestMapping(
        value = "/multimedias",
        method = RequestMethod.GET,
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    ResponseEntity<List<MultimediaFile>> getAllMultimedias();


    @ApiOperation(value = "Get file by id.", nickname = "downloadFileByID", notes = "Return a file by id.", response = File.class, tags = {"Multimedias"})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK.", response = File.class),
        @ApiResponse(code = 401, message = "Unauthorized.", response = Error.class),
        @ApiResponse(code = 403, message = "Forbidden.", response = Error.class),
        @ApiResponse(code = 404, message = "Not found.", response = Error.class),
        @ApiResponse(code = 500, message = "Server error.", response = Error.class)})
    @RequestMapping(
        value = "/download/{id}",
        method = RequestMethod.GET,
        produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    ResponseEntity<Resource> downloadFileByID( @ApiParam(value = "File UUID to download", required = true) @PathVariable("id") UUID id );

}

