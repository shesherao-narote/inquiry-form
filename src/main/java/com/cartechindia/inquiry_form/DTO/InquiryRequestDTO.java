package com.cartechindia.inquiry_form.DTO;

import lombok.Data;


@Data
public class InquiryRequestDTO {

    private String subject;

    private String message;

    private Long userId;
}
