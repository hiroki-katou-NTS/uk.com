package nts.uk.ctx.core.app.insurance;

import lombok.Data;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;

@Data
public class HistoryInsuranceRateDto{
	private String historyId;
	private String companyCode;
	//private MonthRange monthRage;
	private String startMonthRage;
	private String endMonthRage;
	private String inforMonthRage;
}
