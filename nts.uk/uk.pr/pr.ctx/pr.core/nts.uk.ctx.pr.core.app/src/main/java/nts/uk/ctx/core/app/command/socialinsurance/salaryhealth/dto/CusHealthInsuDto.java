package nts.uk.ctx.core.app.command.socialinsurance.salaryhealth.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.HealthInsurancePerGradeFee;

@Data
@AllArgsConstructor
public class CusHealthInsuDto {
	
	private int healthInsuranceGrade;
	private long standardMonthlyFee;
	private Long rewardMonthlyLowerLimit;
	private Long rewardMonthlyUpperLimit;
	private String  inHealthInsurancePremium;
	private String  inNursingCare;
	private String  inSpecInsurancePremium;
	private String  inBasicInsurancePremium;
	private String  emHealthInsurancePremium;
	private String  emNursingCare;
	private String  emSpecInsurancePremium;
	private String  emBasicInsurancePremium;
	
	public HealthInsurancePerGradeFee fromCommandToDomain (){
		return new HealthInsurancePerGradeFee(this.healthInsuranceGrade,
				 fromString(emHealthInsurancePremium),fromString(emNursingCare),fromString(emSpecInsurancePremium),fromString(emBasicInsurancePremium),
				fromString(inHealthInsurancePremium),fromString(inNursingCare),fromString(inSpecInsurancePremium) ,fromString(inBasicInsurancePremium));
	}

	public BigDecimal fromString(String pa) {
		return new BigDecimal(pa).setScale(2, BigDecimal.ROUND_HALF_EVEN);
	}

}
