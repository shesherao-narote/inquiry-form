package com.cartechindia.inquiry_form.service;

import com.cartechindia.inquiry_form.DTO.InquiryRequestDTO;
import com.cartechindia.inquiry_form.DTO.InquiryResponseDTO;
import com.cartechindia.inquiry_form.entity.Inquiry;
import com.cartechindia.inquiry_form.repository.InquiryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InquiryServiceTest {

    private InquiryResponseDTO inquiryResponseDTO;

    private Inquiry inquiry;

    @Mock
    private InquiryRequestDTO inquiryRequestDTO;

    @Mock
    private InquiryRepository inquiryRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private InquiryService inquiryService;


    @BeforeEach
    void setUp() {
        inquiry = new Inquiry();
        inquiry.setInquiryId(1L);
        inquiry.setSubject("Testing Subject");
        inquiry.setMessage("Testing Message");

        inquiryResponseDTO = new InquiryResponseDTO();
        inquiryResponseDTO.setInquiryId(1L);
        inquiryResponseDTO.setSubject("Testing Subject");
        inquiryResponseDTO.setMessage("Testing Message");

    }


    @Test
    void postInquiry_ValidRequest_ReturnsResponseDTO() {


        when(inquiryRepository.save(any(Inquiry.class))).thenReturn(inquiry);

        Inquiry savedInquiry = inquiryRepository.save(inquiry);

        assertNotNull(savedInquiry);

        assertEquals(1L, savedInquiry.getInquiryId());

        verify(inquiryRepository, times(1)).save(any(Inquiry.class));
    }



    @Test
    void getInquiryByInquiryId_ValidRequest_ReturnsResponseDTO() {

        Long inquiryId = 1L;

        when(inquiryService.getInquiryByInquiryId(inquiryId)).thenReturn(inquiryResponseDTO);

        InquiryResponseDTO fetchedInquiries = inquiryService.getInquiryByInquiryId(inquiryId);

        assertNotNull(fetchedInquiries);

        assertEquals(1L, fetchedInquiries.getInquiryId());

        verify(inquiryService, times(1)).getInquiryByInquiryId(any(Long.class));
    }



    @Test
    void deleteInquiryById_ValidRequest_ReturnsResponseDTO() {

        Long inquiryId = 1L;

        when(inquiryRepository.findById(inquiryId)).thenReturn(Optional.of(inquiry));
        when(inquiryService.deleteInquiryById(inquiryId)).thenReturn(inquiryResponseDTO);

        Inquiry existedInquiry = inquiryRepository.findById(inquiryId).get();
        InquiryResponseDTO deletedInquiry = inquiryService.deleteInquiryById(inquiryId);

        assertNotNull(deletedInquiry);
        assertEquals(inquiryId, deletedInquiry.getInquiryId());
        assertEquals("Testing Message", deletedInquiry.getMessage());

        verify(inquiryRepository, times(1)).findById(inquiryId);
        verify(inquiryService, times(1)).deleteInquiryById(inquiryId);
    }



}