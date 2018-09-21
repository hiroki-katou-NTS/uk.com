package nts.uk.ctx.core.app.command.socialinsurance.salaryhealth.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.GradeWelfarePensionInsurancePremium;

@Data
@AllArgsConstructor
public class CusWelfarePensionDto {
	
	private int welfarePensionGrade;
	private long standardMonthlyFee;
	private Long rewardMonthlyLowerLimit;
	private Long rewardMonthlyUpperLimit;
	private String inMaleInsurancePremium;
	private String emMaleInsurancePremium;
	private String inMaleExemptionInsurance;
	private String emMaleExemptionInsurance;
	private String inFemaleInsurancePremium;
	private String emFemaleInsurancePremium;
	private String inFemaleExemptionInsurance;
	private String emFemaleExemptionInsurance;
	
	public GradeWelfarePensionInsurancePremium fromToDomain() {
		return new GradeWelfarePensionInsurancePremium(this.welfarePensionGrade,
				new BigDecimal(this.inFemaleInsurancePremium), new BigDecimal(this.inMaleInsurancePremium),
				this.inFemaleExemptionInsurance != null ? new BigDecimal(this.inFemaleExemptionInsurance) : null, this.inMaleExemptionInsurance != null ? new BigDecimal(this.inMaleExemptionInsurance) : null,
				new BigDecimal(this.emFemaleInsurancePremium), new BigDecimal(this.emMaleInsurancePremium),
				this.emFemaleExemptionInsurance != null ? new BigDecimal(this.emFemaleExemptionInsurance) : null , this.emMaleExemptionInsurance != null ?new BigDecimal(this.emMaleExemptionInsurance) : null);
	}
	
	
}
