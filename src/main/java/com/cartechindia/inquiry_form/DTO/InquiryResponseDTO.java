package com.cartechindia.inquiry_form.DTO;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class InquiryResponseDTO {

    private Long inquiryId;

    private String subject;

    private String message;

    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Long userId;
}
