package nts.uk.ctx.pr.core.app.find.insurance.social.healthrate;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HealthInsuranceRateDto {
	/** The history id. */
	private String historyId;

	/** The company code. */
	private String companyCode;

	/** The office code. */
	private String officeCode;

	/** The start month. */
	private String startMonth;
	
	/** The end month. */
	private String endMonth;

	/** The auto calculate. */
	private Boolean autoCalculate;

	/** The max amount. */
	private Long maxAmount;

	/** The rate items. */
	private List<InsuranceRateItemDto> rateItems;

	/** The rounding methods. */
	private List<HealthInsuranceRoundingDto> roundingMethods;
}
