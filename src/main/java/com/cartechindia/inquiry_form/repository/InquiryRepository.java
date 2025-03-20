package com.cartechindia.inquiry_form.repository;

import com.cartechindia.inquiry_form.entity.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry,Long> {

    Optional<List<Inquiry>> findByStatus(String status);

    Optional<List<Inquiry>> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    Optional<List<Inquiry>> findByUserId(Long userId);

}
