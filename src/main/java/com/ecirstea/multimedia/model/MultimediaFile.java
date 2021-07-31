package com.ecirstea.multimedia.model;

import io.swagger.annotations.ApiModelProperty;
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
    private String name;

    @ApiModelProperty(position = 2)
    private String extension;

    @ApiModelProperty(position = 3)
    private UUID author;

    @Transient
    @ApiModelProperty(position = 4)
    private String fullName;

    @ApiModelProperty(position = 5)
    private String mediaType;

    @ApiModelProperty(position = 6)
    private long length;

    @ApiModelProperty(position = 7)
    private long downloadCounter;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @ApiModelProperty(position = 7, value = "Field provided by server.")
    private Date modified;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @ApiModelProperty(position = 8, value = "Field provided by server.")
    private Date created;

    public MultimediaFile( String fullName, String author, String mediaType, long length )
    {
        this.name = getNameByStringHandling(fullName).get();
        this.extension = getExtensionByStringHandling(fullName).get();
        this.fullName = name + "." + extension;
        this.mediaType = mediaType;
        this.length = length;
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
            fullName = name + "." + extension;
        }
        return fullName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getAuthor() {
        return author;
    }

    public void setAuthor(UUID author) {
        this.author = author;
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



    //https://www.baeldung.com/java-file-extension
    private Optional<String> getExtensionByStringHandling( String filename )
    {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }


}
