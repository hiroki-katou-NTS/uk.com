package nts.uk.ctx.pr.file.app.core.socialinsurance.salaryhealth_healthinsur;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.pr.core.app.command.socialinsurance.salaryhealth.dto.CusHealthInsuDto;
import nts.uk.ctx.pr.core.app.command.socialinsurance.salaryhealth.dto.SalaryHealthInsurancePremiumRateDto;
import nts.uk.ctx.pr.core.dom.socialinsurance.healthinsurance.HealthContributionRate;
import nts.uk.ctx.pr.core.dom.socialinsurance.healthinsurance.HealthInsuranceContributionFee;
import nts.uk.ctx.pr.core.dom.socialinsurance.healthinsurance.HealthInsuranceStandardGradePerMonth;
import nts.uk.ctx.pr.core.dom.socialinsurance.welfarepensioninsurance.EmployeeShareAmountMethod;
import nts.uk.ctx.pr.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionGradePerRewardMonthlyRange;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalaryHealthInsurExportData {

    private List<CusHealthInsuDto> cusDataDtos;
    private SalaryHealthInsurancePremiumRateDto premiumRate;
    private String officeCode;
    private String socialInsuranceName;
    private String displayStart;
    private String displayEnd;
}
