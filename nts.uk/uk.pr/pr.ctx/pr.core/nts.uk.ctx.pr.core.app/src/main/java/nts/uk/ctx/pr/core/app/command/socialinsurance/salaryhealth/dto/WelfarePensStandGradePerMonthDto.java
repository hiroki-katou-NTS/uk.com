package nts.uk.ctx.pr.core.app.command.socialinsurance.salaryhealth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WelfarePensStandGradePerMonthDto {

    private Integer welfarePensionGrade;
    private Long standardMonthlyFee;
}
