package nts.uk.ctx.core.app.command.socialinsurance.salaryhealth.dto;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.GradeWelfarePensionInsurancePremium;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionInsuranceClassification;

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

	public static GradeWelfarePensionInsurancePremium fromToDomain(Optional<WelfarePensionInsuranceClassification> data, CusWelfarePensionDto dto) {
		if(data.get().getFundClassification().value == 1) {
			return new GradeWelfarePensionInsurancePremium(dto.welfarePensionGrade,
					fromString(dto.getInFemaleInsurancePremium()).add(fromString(dto.inFemaleExemptionInsurance)), fromString(dto.inMaleInsurancePremium).add(fromString(dto.inMaleExemptionInsurance)),
					dto.inFemaleExemptionInsurance != null ? fromString(dto.inFemaleExemptionInsurance) : null, dto.inMaleExemptionInsurance != null ? fromString(dto.inMaleExemptionInsurance) : null,
					fromString(dto.emFemaleInsurancePremium).add(fromString(dto.emFemaleExemptionInsurance)), fromString(dto.emMaleInsurancePremium).add(fromString(dto.emMaleExemptionInsurance)),
					dto.emFemaleExemptionInsurance != null ? fromString(dto.emFemaleExemptionInsurance) : null , dto.emMaleExemptionInsurance != null ?fromString(dto.emMaleExemptionInsurance) : null);
		} else {
			return new GradeWelfarePensionInsurancePremium(dto.welfarePensionGrade,
					fromString(dto.inFemaleInsurancePremium), fromString(dto.inMaleInsurancePremium),
					dto.inFemaleExemptionInsurance != null ? fromString(dto.inFemaleExemptionInsurance) : null, dto.inMaleExemptionInsurance != null ? fromString(dto.inMaleExemptionInsurance) : null,
					fromString(dto.emFemaleInsurancePremium), fromString(dto.emMaleInsurancePremium),
					dto.emFemaleExemptionInsurance != null ? fromString(dto.emFemaleExemptionInsurance) : null , dto.emMaleExemptionInsurance != null ?fromString(dto.emMaleExemptionInsurance) : null);
		}
	}

	public static BigDecimal fromString(String pa) {
		return new BigDecimal(pa).setScale(2, BigDecimal.ROUND_HALF_EVEN);
	}
}
