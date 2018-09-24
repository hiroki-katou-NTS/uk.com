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
				new BigDecimal(emHealthInsurancePremium),new BigDecimal(emNursingCare),new BigDecimal(emSpecInsurancePremium),new BigDecimal(emBasicInsurancePremium),
						new BigDecimal (inHealthInsurancePremium),new BigDecimal (inNursingCare),new BigDecimal (inSpecInsurancePremium) ,new BigDecimal (inBasicInsurancePremium));
	}
	
}
