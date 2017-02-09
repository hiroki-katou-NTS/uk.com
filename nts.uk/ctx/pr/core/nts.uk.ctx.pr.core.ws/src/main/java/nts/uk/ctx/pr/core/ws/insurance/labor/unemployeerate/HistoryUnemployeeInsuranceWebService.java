/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.ws.insurance.labor.unemployeerate;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.app.insurance.labor.unemployeerate.HistoryUnemployeeInsuranceDto;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.ws.insurance.labor.HistoryInsurance;

@Path("pr/insurance/labor/unemployeerate/history")
@Produces("application/json")
public class HistoryUnemployeeInsuranceWebService extends WebService {

	/**
	 * Find all history.
	 *
	 * @return the list
	 */
	// get History UnemployeeInsuranceRate
	@POST
	@Path("findall")
	public List<HistoryUnemployeeInsuranceDto> findAllHistory() {
		List<HistoryUnemployeeInsuranceDto> lstHistoryUnemployeeInsuranceRate = new ArrayList<HistoryUnemployeeInsuranceDto>();
		HistoryUnemployeeInsuranceDto historyUnemployeeInsuranceRate006 = new HistoryUnemployeeInsuranceDto();
		MonthRange monthRange006 = MonthRange.range(new YearMonth(2016 * 100 + 4), new YearMonth(9999 * 100 + 12));
		// historyUnemployeeInsuranceRate006.setMonthRage(monthRange006);
		historyUnemployeeInsuranceRate006.setHistoryId("historyId006");
		historyUnemployeeInsuranceRate006
				.setStartMonthRage(HistoryInsurance.convertMonth(monthRange006.getStartMonth()));
		historyUnemployeeInsuranceRate006.setEndMonthRage(HistoryInsurance.convertMonth(monthRange006.getEndMonth()));
		historyUnemployeeInsuranceRate006.setInforMonthRage(historyUnemployeeInsuranceRate006.getStartMonthRage()
				+ " ~ " + historyUnemployeeInsuranceRate006.getEndMonthRage());
		lstHistoryUnemployeeInsuranceRate.add(historyUnemployeeInsuranceRate006);
		HistoryUnemployeeInsuranceDto historyUnemployeeInsuranceRate005 = new HistoryUnemployeeInsuranceDto();
		MonthRange monthRange005 = MonthRange.range(new YearMonth(2015 * 100 + 10), new YearMonth(2016 * 100 + 3));
		// historyUnemployeeInsuranceRate006.setMonthRage(monthRange006);
		historyUnemployeeInsuranceRate005.setHistoryId("historyId005");
		historyUnemployeeInsuranceRate005
				.setStartMonthRage(HistoryInsurance.convertMonth(monthRange005.getStartMonth()));
		historyUnemployeeInsuranceRate005.setEndMonthRage(HistoryInsurance.convertMonth(monthRange005.getEndMonth()));
		historyUnemployeeInsuranceRate005.setInforMonthRage(historyUnemployeeInsuranceRate005.getStartMonthRage()
				+ " ~ " + historyUnemployeeInsuranceRate005.getEndMonthRage());
		lstHistoryUnemployeeInsuranceRate.add(historyUnemployeeInsuranceRate005);
		HistoryUnemployeeInsuranceDto historyUnemployeeInsuranceRate004 = new HistoryUnemployeeInsuranceDto();
		MonthRange monthRange004 = MonthRange.range(new YearMonth(2015 * 100 + 4), new YearMonth(2015 * 100 + 9));
		// historyUnemployeeInsuranceRate006.setMonthRage(monthRange006);
		historyUnemployeeInsuranceRate004.setHistoryId("historyId004");
		historyUnemployeeInsuranceRate004
				.setStartMonthRage(HistoryInsurance.convertMonth(monthRange004.getStartMonth()));
		historyUnemployeeInsuranceRate004.setEndMonthRage(HistoryInsurance.convertMonth(monthRange004.getEndMonth()));
		historyUnemployeeInsuranceRate004.setInforMonthRage(historyUnemployeeInsuranceRate004.getStartMonthRage()
				+ " ~ " + historyUnemployeeInsuranceRate004.getEndMonthRage());
		lstHistoryUnemployeeInsuranceRate.add(historyUnemployeeInsuranceRate004);
		HistoryUnemployeeInsuranceDto historyUnemployeeInsuranceRate003 = new HistoryUnemployeeInsuranceDto();
		MonthRange monthRange003 = MonthRange.range(new YearMonth(2014 * 100 + 9), new YearMonth(2015 * 100 + 3));
		// historyUnemployeeInsuranceRate006.setMonthRage(monthRange006);
		historyUnemployeeInsuranceRate003.setHistoryId("historyId003");
		historyUnemployeeInsuranceRate003
				.setStartMonthRage(HistoryInsurance.convertMonth(monthRange003.getStartMonth()));
		historyUnemployeeInsuranceRate003.setEndMonthRage(HistoryInsurance.convertMonth(monthRange003.getEndMonth()));
		historyUnemployeeInsuranceRate003.setInforMonthRage(historyUnemployeeInsuranceRate003.getStartMonthRage()
				+ " ~ " + historyUnemployeeInsuranceRate003.getEndMonthRage());
		lstHistoryUnemployeeInsuranceRate.add(historyUnemployeeInsuranceRate003);
		HistoryUnemployeeInsuranceDto historyUnemployeeInsuranceRate002 = new HistoryUnemployeeInsuranceDto();
		MonthRange monthRange002 = MonthRange.range(new YearMonth(2014 * 100 + 4), new YearMonth(2014 * 100 + 8));
		// historyUnemployeeInsuranceRate006.setMonthRage(monthRange006);
		historyUnemployeeInsuranceRate002.setHistoryId("historyId002");
		historyUnemployeeInsuranceRate002
				.setStartMonthRage(HistoryInsurance.convertMonth(monthRange002.getStartMonth()));
		historyUnemployeeInsuranceRate002.setEndMonthRage(HistoryInsurance.convertMonth(monthRange002.getEndMonth()));
		historyUnemployeeInsuranceRate002.setInforMonthRage(historyUnemployeeInsuranceRate002.getStartMonthRage()
				+ " ~ " + historyUnemployeeInsuranceRate002.getEndMonthRage());
		lstHistoryUnemployeeInsuranceRate.add(historyUnemployeeInsuranceRate002);
		HistoryUnemployeeInsuranceDto historyUnemployeeInsuranceRate001 = new HistoryUnemployeeInsuranceDto();
		MonthRange monthRange001 = MonthRange.range(new YearMonth(2013 * 100 + 4), new YearMonth(2014 * 100 + 3));
		// historyUnemployeeInsuranceRate006.setMonthRage(monthRange006);
		historyUnemployeeInsuranceRate001.setHistoryId("historyId001");
		historyUnemployeeInsuranceRate001
				.setStartMonthRage(HistoryInsurance.convertMonth(monthRange001.getStartMonth()));
		historyUnemployeeInsuranceRate001.setEndMonthRage(HistoryInsurance.convertMonth(monthRange001.getEndMonth()));
		historyUnemployeeInsuranceRate001.setInforMonthRage(historyUnemployeeInsuranceRate001.getStartMonthRage()
				+ " ~ " + historyUnemployeeInsuranceRate001.getEndMonthRage());
		lstHistoryUnemployeeInsuranceRate.add(historyUnemployeeInsuranceRate001);
		return lstHistoryUnemployeeInsuranceRate;
	}

	/**
	 * Find history.
	 *
	 * @param historyId
	 *            the history id
	 * @return the history unemployee insurance dto
	 */
	@POST
	@Path("find/{historyId}")
	public HistoryUnemployeeInsuranceDto findHistory(@PathParam("historyId") String historyId) {
		HistoryUnemployeeInsuranceDto historyUnemployeeInsuranceRateDto = new HistoryUnemployeeInsuranceDto();
		List<HistoryUnemployeeInsuranceDto> lstHistoryUnemployeeInsuranceRate = findAllHistory();
		for (HistoryUnemployeeInsuranceDto history : lstHistoryUnemployeeInsuranceRate) {
			if (history.getHistoryId().equals(historyId)) {
				historyUnemployeeInsuranceRateDto = history;
			}
		}
		return historyUnemployeeInsuranceRateDto;
	}
}
