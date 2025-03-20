package com.cartechindia.inquiry_form.DTO;

import lombok.Data;
import java.time.LocalDate;

@Data
public class DateRangeRequestDTO {

    private LocalDate startDate;
    private LocalDate endDate;

}
