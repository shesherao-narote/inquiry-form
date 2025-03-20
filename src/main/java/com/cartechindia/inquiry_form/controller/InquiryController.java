package com.cartechindia.inquiry_form.controller;

import com.cartechindia.inquiry_form.DTO.InquiryRequestDTO;
import com.cartechindia.inquiry_form.DTO.InquiryResponseDTO;
import com.cartechindia.inquiry_form.exception.CustomException;
import com.cartechindia.inquiry_form.service.InquiryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/inquiry")
public class InquiryController {

    private final InquiryService inquiryService;

    public InquiryController(InquiryService inquiryService) {
        this.inquiryService = inquiryService;
    }

    @PostMapping("/post")
    public ResponseEntity<InquiryResponseDTO> postInquiry(@RequestBody InquiryRequestDTO inquiryRequestDTO) {

        if (inquiryRequestDTO != null) {
                try {
                    InquiryResponseDTO postedInquiry = inquiryService.postInquiry(inquiryRequestDTO);
                    if (postedInquiry != null) {
                        return new ResponseEntity<>(postedInquiry, HttpStatus.CREATED);
                    } else {
                        throw new CustomException("Inquiry Not Posted...!");
                    }
                }catch (Exception ex) {
                    throw new CustomException("You Are Sending Invalid Data...!");
                }
        }
        throw  new CustomException("You Are Sending Empty Data...!");
    }


    @PatchMapping("/update/{inquiryId}")
    public ResponseEntity<InquiryResponseDTO> updateInquiry(@PathVariable Long inquiryId
            ,@RequestBody InquiryRequestDTO inquiryRequestDTO) {

        if (inquiryId != null && inquiryRequestDTO != null) {
            try {
                InquiryResponseDTO updatedInquiry = inquiryService.updateInquiry(inquiryId, inquiryRequestDTO);
                if (updatedInquiry != null) {
                    return new ResponseEntity<>(updatedInquiry, HttpStatus.CREATED);
                }else {
                    throw new CustomException("Inquiry Not Updated...!");
                }
            }catch (Exception ex) {
                throw new CustomException("Inquiry Not Updated...!");
            }
        }
        throw new CustomException("Invalid Data or Empty Data...!");
    }


    @GetMapping("/get/{inquiryId}")
    public ResponseEntity<InquiryResponseDTO> getInquiry(@PathVariable Long inquiryId) {
        if (inquiryId != null) {
            try {
                InquiryResponseDTO inquiry = inquiryService.getInquiryByInquiryId(inquiryId);
                if (inquiry != null) {
                    return new ResponseEntity<>(inquiry, HttpStatus.OK);
                }else {
                    throw new  CustomException("Inquiry Not Available...!");
                }
            }catch (Exception ex) {
                throw new CustomException("Inquiry Not Available...!");
            }
        }
        throw new CustomException("Please Enter Inquiry Id...!");
    }


    @DeleteMapping("/delete/{inquiryId}")
    public ResponseEntity<InquiryResponseDTO> deleteInquiry(@PathVariable Long inquiryId) {
        if (inquiryId != null) {
            try {
                InquiryResponseDTO deletedInquiry = inquiryService.deleteInquiryById(inquiryId);
                if (deletedInquiry != null) {
                    return new ResponseEntity<>(deletedInquiry, HttpStatus.OK);
                }else {
                    throw new CustomException("Failed to Delete Inquiry or Inquiry Not Found...!");
                }
            }catch (Exception ex) {
                throw new CustomException("Failed to Delete Inquiry or Inquiry Not Found...!");
            }
        }
        throw new CustomException("Inquiry Id is Empty or Invalid...!");
    }


    @GetMapping("/getAll")
    public ResponseEntity<List<InquiryResponseDTO>> getAll() {
        try {
            List<InquiryResponseDTO> inquiries = inquiryService.getAllInquiries();
            if (!inquiries.isEmpty()) {
                return new ResponseEntity<>(inquiries, HttpStatus.OK);
            }
        }catch (Exception ex) {
            throw new CustomException("Inquiries Not Found or Empty...!");
        }
        throw new CustomException("Inquiries Not Found or Empty...!");
    }


    @GetMapping("/getByStatus/{status}")
    public ResponseEntity<List<InquiryResponseDTO>> getByStatus(@PathVariable String status) {
        if (status != null) {
            try {
                List<InquiryResponseDTO> inquiries = inquiryService.getInquiryByStatus(status);
                if (!inquiries.isEmpty()) {
                    return new ResponseEntity<>(inquiries, HttpStatus.OK);
                }else {
                    throw new CustomException("Inquiries Not Found...!");
                }
            }catch (Exception ex) {
                throw new CustomException("Inquiries Not Found...!");
            }
        }
        throw new CustomException("Status data is empty or invalid...!");
    }


    @GetMapping("/getByDate")
    public ResponseEntity<List<InquiryResponseDTO>> getInquiriesByDateRange(@RequestParam LocalDate startDate
            ,@RequestParam LocalDate endDate) {

        if (startDate != null && endDate != null) {
            try {
                List<InquiryResponseDTO> inquiries = inquiryService.getByDateRange(startDate, endDate);
                if (!inquiries.isEmpty()) {
                    return new ResponseEntity<>(inquiries, HttpStatus.OK);
                }else {
                    throw new CustomException("Inquiries Not Found...!");
                }
        }catch(Exception ex){
            throw new CustomException("Inquiries Not Found...!");
        }
    }
        throw new CustomException("Date is empty or Invalid...!");
    }


    @GetMapping("/getByUserId/{userId}")
    public ResponseEntity<List<InquiryResponseDTO>> getInquiriesByUserId(@PathVariable Long userId) {
        if (userId != null) {
            try {
                List<InquiryResponseDTO> inquiries = inquiryService.getByUserId(userId);
                if (!inquiries.isEmpty()) {
                    return new ResponseEntity<>(inquiries, HttpStatus.OK);
                } else {
                    throw new CustomException("Inquiries Not Found...!");
                }
            } catch (Exception ex) {
                throw new CustomException("Inquiries Not Found...!");
            }
        }
        throw new CustomException("Empty or Invalid User Id...!");
    }

}
