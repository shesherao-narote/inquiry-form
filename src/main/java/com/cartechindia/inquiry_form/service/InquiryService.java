package com.cartechindia.inquiry_form.service;

import com.cartechindia.inquiry_form.DTO.InquiryRequestDTO;
import com.cartechindia.inquiry_form.DTO.InquiryResponseDTO;
import java.time.LocalDate;
import java.util.List;

public interface InquiryService {

    InquiryResponseDTO postInquiry(InquiryRequestDTO inquiryRequestDTO);

    InquiryResponseDTO updateInquiry(Long inquiryId, InquiryRequestDTO inquiryRequestDTO);

    InquiryResponseDTO getInquiryByInquiryId(Long inquiryId);

    InquiryResponseDTO deleteInquiryById(Long inquiryId);

    List<InquiryResponseDTO> getAllInquiries();

    List<InquiryResponseDTO> getInquiryByStatus(String status);

    List<InquiryResponseDTO> getByDateRange(LocalDate start, LocalDate end);

    List<InquiryResponseDTO> getByUserId(Long userId);

}
