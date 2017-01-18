package nts.uk.ctx.core.app.insurance.info;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;

@Setter
@Getter
public class InfoHistoryInsuranceRateDto {
	private String historyId;
	private String companyCode;
	private MonthRange monthRage;

}
