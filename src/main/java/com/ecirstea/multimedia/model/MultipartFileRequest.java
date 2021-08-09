package com.ecirstea.multimedia.model;

import io.swagger.annotations.ApiModelProperty;

import java.util.UUID;

public class MultipartFileRequest {
    @ApiModelProperty(position = 1)
    private UUID author;

    @ApiModelProperty(position = 2)
    private UUID narrator;
}
