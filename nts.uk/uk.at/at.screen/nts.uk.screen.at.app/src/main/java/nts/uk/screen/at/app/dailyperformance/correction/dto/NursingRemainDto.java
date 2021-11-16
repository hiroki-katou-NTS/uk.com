package nts.uk.screen.at.app.dailyperformance.correction.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NursingRemainDto {

    // 子の看護残数
    NursingVacationDto childCareVacation;
    
    // 介護残数
    NursingVacationDto longTermCareVacation;
}
