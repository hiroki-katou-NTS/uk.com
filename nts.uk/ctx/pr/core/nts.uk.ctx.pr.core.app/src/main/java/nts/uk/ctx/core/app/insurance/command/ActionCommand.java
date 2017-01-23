package nts.uk.ctx.core.app.insurance.command;

import java.util.HashSet;
import java.util.Set;

import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.insurance.RoundingItem;
import nts.uk.ctx.pr.core.dom.insurance.RoundingMethod;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.CareerGroup;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRateItem;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRateItemSetting;

//class auto convert object a to b
public class ActionCommand {
	// String => YearMonth
	public static YearMonth convertYearMonth(String monthyear) {
		// Format mm/YYYY => String mm and YYYY
		String outmonthyear[] = monthyear.split("/");
		return YearMonth.of(Integer.parseInt(outmonthyear[0]), Integer.parseInt(outmonthyear[1]));
	}

	// String, String => MonthRange
	public static MonthRange convertMonthRange(String historyStar, String historyEnd) {
		MonthRange monthRange = new MonthRange();
		monthRange.setEndMonth(convertYearMonth(historyEnd));
		monthRange.setStartMonth(convertYearMonth(historyStar));
		return monthRange;
	}

	// Default UnemployeeInsuranceRateItem
	public static Set<UnemployeeInsuranceRateItem> defaultSetUnemployeeInsuranceRateItem() {
		Set<UnemployeeInsuranceRateItem> setUnemployeeInsuranceRateItem = new HashSet<UnemployeeInsuranceRateItem>();
		UnemployeeInsuranceRateItem unemployeeInsuranceRateItemAgroforestry = defaultUnemployeeInsuranceRateItem(
				CareerGroup.Agroforestry);
		setUnemployeeInsuranceRateItem.add(unemployeeInsuranceRateItemAgroforestry);
		UnemployeeInsuranceRateItem unemployeeInsuranceRateItemContruction = defaultUnemployeeInsuranceRateItem(
				CareerGroup.Contruction);
		setUnemployeeInsuranceRateItem.add(unemployeeInsuranceRateItemContruction);
		UnemployeeInsuranceRateItem unemployeeInsuranceRateItemOther = defaultUnemployeeInsuranceRateItem(
				CareerGroup.Other);
		setUnemployeeInsuranceRateItem.add(unemployeeInsuranceRateItemOther);
		return setUnemployeeInsuranceRateItem;
	}

	public static UnemployeeInsuranceRateItem defaultUnemployeeInsuranceRateItem(CareerGroup careerGroup) {
		UnemployeeInsuranceRateItem unemployeeInsuranceRateItem = new UnemployeeInsuranceRateItem();
		unemployeeInsuranceRateItem.setCareerGroup(careerGroup);
		UnemployeeInsuranceRateItemSetting companySetting = new UnemployeeInsuranceRateItemSetting();
		companySetting.setRate(0.00d);
		companySetting.setRoundAtr(RoundingMethod.RoundUp);
		UnemployeeInsuranceRateItemSetting personalSetting = new UnemployeeInsuranceRateItemSetting();
		personalSetting.setRate(0.00d);
		personalSetting.setRoundAtr(RoundingMethod.RoundUp);
		unemployeeInsuranceRateItem.setCompanySetting(companySetting);
		unemployeeInsuranceRateItem.setPersonalSetting(personalSetting);
		return unemployeeInsuranceRateItem;
	}
}
