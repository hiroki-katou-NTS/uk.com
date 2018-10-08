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
				fromString(this.inFemaleInsurancePremium), fromString(this.inMaleInsurancePremium),
				this.inFemaleExemptionInsurance != null ? fromString(this.inFemaleExemptionInsurance) : null, this.inMaleExemptionInsurance != null ? fromString(this.inMaleExemptionInsurance) : null,
				fromString(this.emFemaleInsurancePremium), fromString(this.emMaleInsurancePremium),
				this.emFemaleExemptionInsurance != null ? fromString(this.emFemaleExemptionInsurance) : null , this.emMaleExemptionInsurance != null ?fromString(this.emMaleExemptionInsurance) : null);
	}

	public BigDecimal fromString(String pa) {
		return new BigDecimal(pa).setScale(2, BigDecimal.ROUND_HALF_EVEN);
	}
}
