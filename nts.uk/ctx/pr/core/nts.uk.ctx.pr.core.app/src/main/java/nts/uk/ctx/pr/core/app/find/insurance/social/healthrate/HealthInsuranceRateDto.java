package nts.uk.ctx.pr.core.app.find.insurance.social.healthrate;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.insurance.OfficeCode;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HealthInsuranceRateDto {
	/** The history id. */
	// historyId
	private String historyId;

	/** The company code. */
	private CompanyCode companyCode;

	/** The office code. */
	private OfficeCode officeCode;

	/** The apply range. */
	private MonthRange applyRange;

	/** The auto calculate. */
	private Boolean autoCalculate;

	/** The max amount. */
	private Long maxAmount;

	/** The rate items. */
	private List<InsuranceRateItemDto> rateItems;

	/** The rounding methods. */
	private List<HealthInsuranceRoundingDto> roundingMethods;
}
