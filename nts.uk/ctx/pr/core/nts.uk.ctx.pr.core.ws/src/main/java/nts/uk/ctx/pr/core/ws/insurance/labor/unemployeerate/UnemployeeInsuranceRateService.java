package nts.uk.ctx.pr.core.ws.insurance.labor.unemployeerate;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.arc.time.YearMonth;
import nts.uk.ctx.core.app.insurance.labor.unemployeerate.HistoryUnemployeeInsuranceRateDto;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;


@Path("pr/insurance/labor/unemployeerate")
@Produces("application/json")
public class UnemployeeInsuranceRateService extends WebService {
	// get History UnemployeeInsuranceRate
	@POST
	@Path("findallHistory")
	public List<HistoryUnemployeeInsuranceRateDto> findAllHistory() {
		List<HistoryUnemployeeInsuranceRateDto> lstHistoryUnemployeeInsuranceRate = new ArrayList<HistoryUnemployeeInsuranceRateDto>();
		HistoryUnemployeeInsuranceRateDto historyUnemployeeInsuranceRate006 = new HistoryUnemployeeInsuranceRateDto();
		historyUnemployeeInsuranceRate006.setCompanyCode("companyCode006");
		MonthRange monthRange006 = new MonthRange();
		monthRange006.setStartMonth(new YearMonth(2016 * 100 + 4));
		monthRange006.setEndMonth(new YearMonth(9999 * 100 + 12));
		//historyUnemployeeInsuranceRate006.setMonthRage(monthRange006);
		historyUnemployeeInsuranceRate006.setHistoryId("historyId006");
		historyUnemployeeInsuranceRate006.setStartMonthRage(convertMonth(monthRange006.getStartMonth()));
		historyUnemployeeInsuranceRate006.setEndMonthRage(convertMonth(monthRange006.getEndMonth()));
		historyUnemployeeInsuranceRate006.setInforMonthRage(historyUnemployeeInsuranceRate006.getStartMonthRage()
				+ " ~ " + historyUnemployeeInsuranceRate006.getEndMonthRage());
		lstHistoryUnemployeeInsuranceRate.add(historyUnemployeeInsuranceRate006);
		return lstHistoryUnemployeeInsuranceRate;
	}

	public String convertMonth(YearMonth yearMonth) {
		String convert = "";
		String mounth = "";
		if (yearMonth.month() < 10) {
			mounth = "0" + yearMonth.month();
		} else
			mounth = String.valueOf(yearMonth.month());
		convert = yearMonth.year() + "/" + mounth;
		return convert;
	}
}
