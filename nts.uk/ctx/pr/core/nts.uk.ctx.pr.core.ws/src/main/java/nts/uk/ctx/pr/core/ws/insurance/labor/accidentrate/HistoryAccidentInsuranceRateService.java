/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.ws.insurance.labor.accidentrate;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.arc.time.YearMonth;
import nts.uk.ctx.core.app.insurance.labor.accidentrate.AccidentInsuranceRateDto;
import nts.uk.ctx.core.app.insurance.labor.accidentrate.HistoryAccidentInsuranceRateDto;
import nts.uk.ctx.core.app.insurance.labor.accidentrate.InsuBizRateItemDto;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.insurance.RoundingMethod;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.InsuBizRateItem;
import nts.uk.ctx.pr.core.dom.insurance.labor.businesstype.BusinessTypeEnum;
import nts.uk.ctx.pr.core.ws.insurance.labor.HistoryInsurance;

@Path("pr/insurance/labor/accidentrate/history")
@Produces("application/json")
public class HistoryAccidentInsuranceRateService extends WebService {

	/**
	 * Find all history.
	 *
	 * @return the list
	 */
	@POST
	@Path("findall")
	public List<HistoryAccidentInsuranceRateDto> findAllHistory() {
		List<HistoryAccidentInsuranceRateDto> lstHistoryAccidentInsuranceRate = new ArrayList<HistoryAccidentInsuranceRateDto>();
		HistoryAccidentInsuranceRateDto historyAccidentInsuranceRate006 = new HistoryAccidentInsuranceRateDto();
		MonthRange monthRange006 = MonthRange.range(new YearMonth(2016 * 100 + 4), new YearMonth(9999 * 100 + 12));
		// historyAccidentInsuranceRate006.setMonthRage(monthRange006);
		historyAccidentInsuranceRate006.setHistoryId("historyId006");
		historyAccidentInsuranceRate006.setStartMonthRage(HistoryInsurance.convertMonth(monthRange006.getStartMonth()));
		historyAccidentInsuranceRate006.setEndMonthRage(HistoryInsurance.convertMonth(monthRange006.getEndMonth()));
		historyAccidentInsuranceRate006.setInforMonthRage(historyAccidentInsuranceRate006.getStartMonthRage() + " ~ "
				+ historyAccidentInsuranceRate006.getEndMonthRage());
		lstHistoryAccidentInsuranceRate.add(historyAccidentInsuranceRate006);
		HistoryAccidentInsuranceRateDto historyAccidentInsuranceRate005 = new HistoryAccidentInsuranceRateDto();
		MonthRange monthRange005 = MonthRange.range(new YearMonth(2015 * 100 + 10), new YearMonth(2016 * 100 + 3));
		// historyAccidentInsuranceRate006.setMonthRage(monthRange006);
		historyAccidentInsuranceRate005.setHistoryId("historyId005");
		historyAccidentInsuranceRate005.setStartMonthRage(HistoryInsurance.convertMonth(monthRange005.getStartMonth()));
		historyAccidentInsuranceRate005.setEndMonthRage(HistoryInsurance.convertMonth(monthRange005.getEndMonth()));
		historyAccidentInsuranceRate005.setInforMonthRage(historyAccidentInsuranceRate005.getStartMonthRage() + " ~ "
				+ historyAccidentInsuranceRate005.getEndMonthRage());
		lstHistoryAccidentInsuranceRate.add(historyAccidentInsuranceRate005);
		HistoryAccidentInsuranceRateDto historyAccidentInsuranceRate004 = new HistoryAccidentInsuranceRateDto();
		MonthRange monthRange004 = MonthRange.range(new YearMonth(2015 * 100 + 4), new YearMonth(2015 * 100 + 9));
		// historyAccidentInsuranceRate006.setMonthRage(monthRange006);
		historyAccidentInsuranceRate004.setHistoryId("historyId004");
		historyAccidentInsuranceRate004.setStartMonthRage(HistoryInsurance.convertMonth(monthRange004.getStartMonth()));
		historyAccidentInsuranceRate004.setEndMonthRage(HistoryInsurance.convertMonth(monthRange004.getEndMonth()));
		historyAccidentInsuranceRate004.setInforMonthRage(historyAccidentInsuranceRate004.getStartMonthRage() + " ~ "
				+ historyAccidentInsuranceRate004.getEndMonthRage());
		lstHistoryAccidentInsuranceRate.add(historyAccidentInsuranceRate004);
		HistoryAccidentInsuranceRateDto historyAccidentInsuranceRate003 = new HistoryAccidentInsuranceRateDto();
		MonthRange monthRange003 = MonthRange.range(new YearMonth(2014 * 100 + 9), new YearMonth(2015 * 100 + 3));
		// historyAccidentInsuranceRate006.setMonthRage(monthRange006);
		historyAccidentInsuranceRate003.setHistoryId("historyId003");
		historyAccidentInsuranceRate003.setStartMonthRage(HistoryInsurance.convertMonth(monthRange003.getStartMonth()));
		historyAccidentInsuranceRate003.setEndMonthRage(HistoryInsurance.convertMonth(monthRange003.getEndMonth()));
		historyAccidentInsuranceRate003.setInforMonthRage(historyAccidentInsuranceRate003.getStartMonthRage() + " ~ "
				+ historyAccidentInsuranceRate003.getEndMonthRage());
		lstHistoryAccidentInsuranceRate.add(historyAccidentInsuranceRate003);
		HistoryAccidentInsuranceRateDto historyAccidentInsuranceRate002 = new HistoryAccidentInsuranceRateDto();
		MonthRange monthRange002 = MonthRange.range(new YearMonth(2014 * 100 + 4), new YearMonth(2014 * 100 + 8));
		// historyAccidentInsuranceRate006.setMonthRage(monthRange006);
		historyAccidentInsuranceRate002.setHistoryId("historyId002");
		historyAccidentInsuranceRate002.setStartMonthRage(HistoryInsurance.convertMonth(monthRange002.getStartMonth()));
		historyAccidentInsuranceRate002.setEndMonthRage(HistoryInsurance.convertMonth(monthRange002.getEndMonth()));
		historyAccidentInsuranceRate002.setInforMonthRage(historyAccidentInsuranceRate002.getStartMonthRage() + " ~ "
				+ historyAccidentInsuranceRate002.getEndMonthRage());
		lstHistoryAccidentInsuranceRate.add(historyAccidentInsuranceRate002);
		HistoryAccidentInsuranceRateDto historyAccidentInsuranceRate001 = new HistoryAccidentInsuranceRateDto();
		MonthRange monthRange001 = MonthRange.range(new YearMonth(2013 * 100 + 4), new YearMonth(2014 * 100 + 3));
		// historyAccidentInsuranceRate006.setMonthRage(monthRange006);
		historyAccidentInsuranceRate001.setHistoryId("historyId001");
		historyAccidentInsuranceRate001.setStartMonthRage(HistoryInsurance.convertMonth(monthRange001.getStartMonth()));
		historyAccidentInsuranceRate001.setEndMonthRage(HistoryInsurance.convertMonth(monthRange001.getEndMonth()));
		historyAccidentInsuranceRate001.setInforMonthRage(historyAccidentInsuranceRate001.getStartMonthRage() + " ~ "
				+ historyAccidentInsuranceRate001.getEndMonthRage());
		lstHistoryAccidentInsuranceRate.add(historyAccidentInsuranceRate001);
		return lstHistoryAccidentInsuranceRate;
	}

	/**
	 * Find history.
	 *
	 * @param historyId
	 *            the history id
	 * @return the history accident insurance rate dto
	 */
	@POST
	@Path("find/{historyId}")
	public HistoryAccidentInsuranceRateDto findHistory(@PathParam("historyId") String historyId) {
		HistoryAccidentInsuranceRateDto historyAccidentInsuranceRate = new HistoryAccidentInsuranceRateDto();
		List<HistoryAccidentInsuranceRateDto> lstHistoryAccidentInsuranceRate = findAllHistory();
		for (HistoryAccidentInsuranceRateDto history : lstHistoryAccidentInsuranceRate) {
			if (history.getHistoryId().equals(historyId)) {
				historyAccidentInsuranceRate = history;
			}
		}
		return historyAccidentInsuranceRate;
	}

	/**
	 * Detail hitory.
	 *
	 * @param historyId
	 *            the history id
	 * @return the accident insurance rate dto
	 */
	@POST
	@Path("detail/{historyId}")
	public AccidentInsuranceRateDto detailHitory(@PathParam("historyId") String historyId) {
		AccidentInsuranceRateDto accidentInsuranceRate = new AccidentInsuranceRateDto();
		HistoryAccidentInsuranceRateDto historyAccidentInsuranceRate = new HistoryAccidentInsuranceRateDto();
		historyAccidentInsuranceRate.setHistoryId(historyId);
		historyAccidentInsuranceRate.setStartMonthRage("2001/01");
		historyAccidentInsuranceRate.setEndMonthRage("9999/12");
		accidentInsuranceRate.setHistoryInsurance(historyAccidentInsuranceRate);
		List<InsuBizRateItemDto> rateItems = new ArrayList<>();
		InsuBizRateItemDto insuBizRateItemBiz1St = new InsuBizRateItemDto();
		insuBizRateItemBiz1St.setInsuBizType(1);// BusinessTypeEnum.Biz1St
		insuBizRateItemBiz1St.setInsuRate(50.5);
		insuBizRateItemBiz1St.setInsuRound(2);// RoundingMethod.RoundDown
		insuBizRateItemBiz1St.setInsuranceBusinessType("事業種類名1");
		rateItems.add(insuBizRateItemBiz1St);
		InsuBizRateItemDto insuBizRateItemBiz2Nd = new InsuBizRateItemDto();
		insuBizRateItemBiz2Nd.setInsuBizType(2);// BusinessTypeEnum.Biz2Nd
		insuBizRateItemBiz2Nd.setInsuRate(50.9);
		insuBizRateItemBiz2Nd.setInsuRound(2);// RoundingMethod.RoundDown
		insuBizRateItemBiz2Nd.setInsuranceBusinessType("事業種類名2");
		rateItems.add(insuBizRateItemBiz2Nd);
		InsuBizRateItemDto insuBizRateItemBiz3Rd = new InsuBizRateItemDto();
		insuBizRateItemBiz3Rd.setInsuBizType(3);// BusinessTypeEnum.Biz3Rd
		insuBizRateItemBiz3Rd.setInsuRate(50.9);// RoundingMethod.RoundDown
		insuBizRateItemBiz3Rd.setInsuRound(2);
		insuBizRateItemBiz3Rd.setInsuranceBusinessType("事業種類名3");
		rateItems.add(insuBizRateItemBiz3Rd);
		InsuBizRateItemDto insuBizRateItemBiz4Th = new InsuBizRateItemDto();
		insuBizRateItemBiz4Th.setInsuBizType(4);// BusinessTypeEnum.Biz4Th
		insuBizRateItemBiz4Th.setInsuRate(50.9);
		insuBizRateItemBiz4Th.setInsuRound(2);// RoundingMethod.RoundDown
		insuBizRateItemBiz4Th.setInsuranceBusinessType("事業種類名4");
		rateItems.add(insuBizRateItemBiz4Th);
		InsuBizRateItemDto insuBizRateItemBiz5Th = new InsuBizRateItemDto();
		insuBizRateItemBiz5Th.setInsuBizType(5);// BusinessTypeEnum.Biz5Th
		insuBizRateItemBiz5Th.setInsuRate(50.9);
		insuBizRateItemBiz5Th.setInsuRound(2);// RoundingMethod.RoundDown
		insuBizRateItemBiz5Th.setInsuranceBusinessType("事業種類名5");
		rateItems.add(insuBizRateItemBiz5Th);
		InsuBizRateItemDto insuBizRateItemBiz6Th = new InsuBizRateItemDto();
		insuBizRateItemBiz6Th.setInsuBizType(6);// BusinessTypeEnum.Biz6Th
		insuBizRateItemBiz6Th.setInsuRate(50.9);
		insuBizRateItemBiz6Th.setInsuRound(2);// RoundingMethod.RoundDown
		insuBizRateItemBiz6Th.setInsuranceBusinessType("事業種類名6");
		rateItems.add(insuBizRateItemBiz6Th);
		InsuBizRateItemDto insuBizRateItemBiz7Th = new InsuBizRateItemDto();
		insuBizRateItemBiz7Th.setInsuBizType(7);// BusinessTypeEnum.Biz7Th
		insuBizRateItemBiz7Th.setInsuRate(50.9);
		insuBizRateItemBiz7Th.setInsuRound(2);// RoundingMethod.RoundDown
		insuBizRateItemBiz7Th.setInsuranceBusinessType("事業種類名7");
		rateItems.add(insuBizRateItemBiz7Th);
		InsuBizRateItemDto insuBizRateItemBiz8Th = new InsuBizRateItemDto();
		insuBizRateItemBiz8Th.setInsuBizType(8);// BusinessTypeEnum.Biz8Th
		insuBizRateItemBiz8Th.setInsuRate(50.9);
		insuBizRateItemBiz8Th.setInsuRound(2);// RoundingMethod.RoundDown
		insuBizRateItemBiz8Th.setInsuranceBusinessType("事業種類名8");
		rateItems.add(insuBizRateItemBiz8Th);
		InsuBizRateItemDto insuBizRateItemBiz9Th = new InsuBizRateItemDto();
		insuBizRateItemBiz9Th.setInsuBizType(9);// BusinessTypeEnum.Biz9Th
		insuBizRateItemBiz9Th.setInsuRate(50.9);
		insuBizRateItemBiz9Th.setInsuRound(2);// RoundingMethod.RoundDown
		insuBizRateItemBiz9Th.setInsuranceBusinessType("事業種類名9");
		rateItems.add(insuBizRateItemBiz9Th);
		InsuBizRateItemDto insuBizRateItemBiz10Th = new InsuBizRateItemDto();
		insuBizRateItemBiz10Th.setInsuBizType(10);// BusinessTypeEnum.Biz10Th
		insuBizRateItemBiz10Th.setInsuRate(50.9);
		insuBizRateItemBiz10Th.setInsuRound(2);// RoundingMethod.RoundDown
		insuBizRateItemBiz10Th.setInsuranceBusinessType("事業種類名10");
		rateItems.add(insuBizRateItemBiz10Th);
		accidentInsuranceRate.setRateItems(rateItems);
		return accidentInsuranceRate;
	}
}
