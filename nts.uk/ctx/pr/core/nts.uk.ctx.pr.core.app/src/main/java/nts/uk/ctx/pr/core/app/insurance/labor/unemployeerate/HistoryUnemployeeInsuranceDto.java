package nts.uk.ctx.pr.core.app.insurance.labor.unemployeerate;

import nts.uk.ctx.pr.core.app.insurance.HistoryInsuranceDto;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRate;

public class HistoryUnemployeeInsuranceDto extends HistoryInsuranceDto {
	public static HistoryUnemployeeInsuranceDto fromDomain(UnemployeeInsuranceRate domain) {
		HistoryUnemployeeInsuranceDto historyUnemployeeInsuranceDto = new HistoryUnemployeeInsuranceDto();
		historyUnemployeeInsuranceDto.setEndMonthRage(convertMonth(domain.getApplyRange().getEndMonth()));
		historyUnemployeeInsuranceDto.setHistoryId(domain.getHistoryId());
		historyUnemployeeInsuranceDto.setStartMonthRage(convertMonth(domain.getApplyRange().getStartMonth()));
		historyUnemployeeInsuranceDto.setInforMonthRage(convertMonth(domain.getApplyRange().getStartMonth()) + " ~ "
				+ convertMonth(domain.getApplyRange().getEndMonth()));
		return historyUnemployeeInsuranceDto;
	}
}
