package nts.uk.ctx.pr.core.app.insurance.labor.unemployeerate.find.dto;

import java.util.Set;

import lombok.Data;
import nts.arc.time.YearMonth;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRateItem;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRateSetMemento;

@Data
public class HistoryUnemployeeInsuranceFindOutDto implements UnemployeeInsuranceRateSetMemento {
	private String historyId;
	private String startMonthRage;
	private String endMonthRage;
	private String inforMonthRage;

	public static String convertMonth(YearMonth yearMonth) {
		String convert = "";
		String mounth = "";
		if (yearMonth.month() < 10) {
			mounth = "0" + yearMonth.month();
		} else
			mounth = String.valueOf(yearMonth.month());
		convert = yearMonth.year() + "/" + mounth;
		return convert;
	}


	@Override
	public void setCompanyCode(CompanyCode companyCode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setApplyRange(MonthRange applyRange) {
		// TODO Auto-generated method stub
		this.endMonthRage = convertMonth(applyRange.getEndMonth());
		this.startMonthRage = convertMonth(applyRange.getStartMonth());
		this.inforMonthRage = this.startMonthRage + " ~ " + this.endMonthRage;

	}

	@Override
	public void setRateItems(Set<UnemployeeInsuranceRateItem> rateItems) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setHistoryId(String historyId) {
		this.historyId = historyId;
	}
}
