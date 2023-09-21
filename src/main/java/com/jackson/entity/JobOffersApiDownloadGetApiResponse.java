package com.jackson.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JobOffersApiDownloadGetApiResponse {

    @JsonProperty("Offer_Id")
    private Integer id;

    @JsonProperty("Offer_Name")
    private String name;

    @JsonProperty("Offer_Code")
    private String code;
}
