package nts.uk.ctx.core.app.command.socialinsurance.salaryhealth.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseHealthAndMonthly {
	private int healthInsuranceGrade;
	private Long rewardMonthlyLowerLimit;
	private Long rewardMonthlyUpperLimit;
	private Long standardMonthlyFee;
	private BigDecimal  inHealthInsurancePremium;
	private BigDecimal  inNursingCare;
	private BigDecimal  inSpecInsurancePremium;
	private BigDecimal  inBasicInsurancePremium;
	private BigDecimal  emHealthInsurancePremium;
	private BigDecimal  emNursingCare;
	private BigDecimal  emSpecInsurancePremium;
	private BigDecimal  emBasicInsurancePremium;
}
