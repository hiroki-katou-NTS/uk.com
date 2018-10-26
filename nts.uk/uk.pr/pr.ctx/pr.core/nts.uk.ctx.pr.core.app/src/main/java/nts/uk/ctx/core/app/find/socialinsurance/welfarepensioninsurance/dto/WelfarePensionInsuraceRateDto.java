package nts.uk.ctx.core.app.find.socialinsurance.welfarepensioninsurance.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.BonusEmployeePensionInsuranceRate;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.EmployeesPensionMonthlyInsuranceFee;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionInsuranceClassification;

@Data
@AllArgsConstructor
public class WelfarePensionInsuraceRateDto {
	private BonusEmployeePensionInsuranceRateDto bonusEmployeePensionInsuranceRate;
	private EmployeesPensionMonthlyInsuranceFeeDto employeesPensionMonthlyInsuranceFee;
	private WelfarePensionInsuranceClassificationDto welfarePensionInsuranceClassification;
	
	public WelfarePensionInsuraceRateDto (Optional<BonusEmployeePensionInsuranceRate> bonusEmployeePension, Optional<EmployeesPensionMonthlyInsuranceFee> employeePensonMonthly, Optional<WelfarePensionInsuranceClassification> welfarePensionClassification) {
		bonusEmployeePension.ifPresent(item -> {
			this.bonusEmployeePensionInsuranceRate = BonusEmployeePensionInsuranceRateDto.fromDomainToDto(item); 
		});
		employeePensonMonthly.ifPresent(item -> {
			this.employeesPensionMonthlyInsuranceFee = EmployeesPensionMonthlyInsuranceFeeDto.fromDomainToDto(item); 
		});
		welfarePensionClassification.ifPresent(item -> {
			this.welfarePensionInsuranceClassification = WelfarePensionInsuranceClassificationDto.fromDomainToDto(item); 
		});
	}
}
