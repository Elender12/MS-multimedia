package com.ecirstea.multimedia.model;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.io.FilenameUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Document(collection = "MultimediaFiles")
public class MultimediaFile {
    @Id
    @Indexed(unique = true)
    @ApiModelProperty()
    private UUID id;

    @ApiModelProperty(position = 1)
    private String title;

    @ApiModelProperty(position = 2)
    private String extension;

    @ApiModelProperty(position = 3)
    private UUID author;

    @ApiModelProperty(position = 4)
    private UUID narrator;
    @Transient
    @ApiModelProperty(position = 5)
    private String fullName;

    @ApiModelProperty(position = 6)
    private String mediaType;

    @ApiModelProperty(position = 7)
    private long length;

    @ApiModelProperty(position = 8)
    private long downloadCounter;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @ApiModelProperty(position = 9, value = "Field provided by server.")
    private Date modified;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @ApiModelProperty(position = 10, value = "Field provided by server.")
    private Date created;


    public MultimediaFile(){}

    public MultimediaFile(UUID id, String title, String extension, UUID author, UUID narrator, String fullName, String mediaType, long length, long downloadCounter, Date modified, Date created) {
        this.id = id;
        this.title = title;
        this.extension = extension;
        this.author = author;
        this.narrator = narrator;
        this.fullName = fullName;
        this.mediaType = mediaType;
        this.length = length;
        this.downloadCounter = downloadCounter;
        this.modified = modified;
        this.created = created;
    }

    public MultimediaFile(String fullName, String author, String mediaType, long length )
    {
        // this.title = getNameByStringHandling(fullName).orElse(null);
        this.title = removeFileExtension(fullName, true);
        // this.extension = getExtensionByStringHandling(fullName).orElse(null);
        this.extension = getExtensionByApacheCommonLib(fullName);
        this.fullName = title + "." + extension;
        this.mediaType = mediaType;
        this.length = length;
    }

    public MultimediaFile(String originalFilename, String contentType, long size) {
        this.title = getNameByStringHandling(originalFilename).orElse(null);
     //   this.extension = getExtensionByStringHandling(fullName).orElse(null);
        this.extension = getExtensionByApacheCommonLib(originalFilename);
        this.fullName = title + "." + extension;
        this.mediaType = contentType;
        this.length = size;

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void makeId()
    {
        this.id = UUID.randomUUID();
    }

    public String getFullName()
    {
        if( fullName == null )
        {
            fullName = title + "." + extension;
        }
        return fullName;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UUID getAuthor() {
        return author;
    }

    public void setAuthor(UUID author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public UUID getNarrator() {
        return narrator;
    }

    public void setNarrator(UUID narrator) {
        this.narrator = narrator;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public long getDownloadCounter() {
        return downloadCounter;
    }

    public void setDownloadCounter(long downloadCounter) {
        this.downloadCounter = downloadCounter;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    private Optional<String> getNameByStringHandling(String filename )
    {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(0, filename.indexOf(".")));
    }


    public String getExtensionByApacheCommonLib(String filename) {
        return FilenameUtils.getExtension(filename);
    }

    public static String removeFileExtension(String filename, boolean removeAllExtensions) {
        if (filename == null || filename.isEmpty()) {
            return filename;
        }

        String extPattern = "(?<!^)[.]" + (removeAllExtensions ? ".*" : "[^.]*$");
        return filename.replaceAll(extPattern, "");
    }

    //https://www.baeldung.com/java-file-extension
    private Optional<String> getExtensionByStringHandling( String filename )
    {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }


}
