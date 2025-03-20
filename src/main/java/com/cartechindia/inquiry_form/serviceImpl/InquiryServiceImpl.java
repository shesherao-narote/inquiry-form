package com.cartechindia.inquiry_form.serviceImpl;

import com.cartechindia.inquiry_form.DTO.InquiryRequestDTO;
import com.cartechindia.inquiry_form.DTO.InquiryResponseDTO;
import com.cartechindia.inquiry_form.entity.Inquiry;
import com.cartechindia.inquiry_form.exception.CustomException;
import com.cartechindia.inquiry_form.repository.InquiryRepository;
import com.cartechindia.inquiry_form.service.InquiryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class InquiryServiceImpl implements InquiryService {

    private final ModelMapper modelMapper;

    private final InquiryRepository inquiryRepository;

    public InquiryServiceImpl(ModelMapper modelMapper, InquiryRepository inquiryRepository) {
        this.modelMapper = modelMapper;
        this.inquiryRepository = inquiryRepository;
    }


    @Override
    public InquiryResponseDTO postInquiry(InquiryRequestDTO inquiryRequestDTO) {
        if(inquiryRequestDTO!=null) {
            if ((!inquiryRequestDTO.getSubject().isEmpty()) && (!inquiryRequestDTO.getMessage().isEmpty())) {
                Inquiry inquiry = Inquiry.builder()
                        .message(inquiryRequestDTO.getMessage())
                        .subject(inquiryRequestDTO.getSubject())
                        .userId(inquiryRequestDTO.getUserId())
                        .status("OPEN")
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build();
                Inquiry savedInquiry = inquiryRepository.save(inquiry);
                return modelMapper.map(savedInquiry, InquiryResponseDTO.class);
            }
        }
        return null;
    }


    @Override
    public InquiryResponseDTO updateInquiry(Long inquiryId, InquiryRequestDTO inquiryRequestDTO) {
        Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(()->new CustomException("Inquiry Not Found...!"));

        Field[] fields = InquiryRequestDTO.class.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object newValue = field.get(inquiryRequestDTO);
                if (newValue != null) {
                   Field entityField = ReflectionUtils.findField(Inquiry.class, field.getName());
                       entityField.setAccessible(true);
                       ReflectionUtils.setField(entityField, inquiry, newValue);
                }
            } catch (IllegalAccessException e) {
                throw new CustomException("Illegal access custom");
            }
        }
        inquiry.setUpdatedAt(LocalDateTime.now());
        Inquiry updatedInquiry = inquiryRepository.save(inquiry);
        return modelMapper.map(updatedInquiry, InquiryResponseDTO.class);
    }


    @Override
    public InquiryResponseDTO getInquiryByInquiryId(Long inquiryId) {
        Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(()->new CustomException("Inquiry not found"));
        return modelMapper.map(inquiry, InquiryResponseDTO.class);
    }


    @Override
    public InquiryResponseDTO deleteInquiryById(Long inquiryId) {
       Inquiry inquiry = inquiryRepository.findById(inquiryId)
               .orElseThrow(()->new CustomException("Inquiry not found"));
       inquiryRepository.delete(inquiry);
       return modelMapper.map(inquiry, InquiryResponseDTO.class);
    }

    @Override
    public List<InquiryResponseDTO> getAllInquiries() {
       return inquiryRepository.findAll().stream().map(inquiry -> {
            return modelMapper.map(inquiry, InquiryResponseDTO.class);
        }).collect(Collectors.toList());
    }

    @Override
    public List<InquiryResponseDTO> getInquiryByStatus(String status) {
        List<Inquiry> inquiries = inquiryRepository.findByStatus(status)
                .orElseThrow(()-> new CustomException("Inquiry not found"));
        return inquiries.stream().map(inquiry -> {
            return modelMapper.map(inquiry, InquiryResponseDTO.class);
        }).collect(Collectors.toList());
    }

    @Override
    public List<InquiryResponseDTO> getByDateRange(LocalDate startDate, LocalDate endDate) {

        LocalDateTime startDateTime = startDate.atStartOfDay();  // 00:00:00
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

        List<Inquiry> inquiries = inquiryRepository.findByCreatedAtBetween(startDateTime, endDateTime)
                .orElseThrow(()-> new CustomException("Inquiry not found"));

        return inquiries.stream().map(inquiry -> {
            return modelMapper.map(inquiry, InquiryResponseDTO.class);
        }).collect(Collectors.toList());
    }

    @Override
    public List<InquiryResponseDTO> getByUserId(Long userId) {
        List<Inquiry> inquiries = inquiryRepository.findByUserId(userId)
                .orElseThrow(()->new CustomException("Inquiries Not Found...!"));

        return inquiries.stream().map(inquiry -> {
            return modelMapper.map(inquiry, InquiryResponseDTO.class);
        }).collect(Collectors.toList());
    }
}
