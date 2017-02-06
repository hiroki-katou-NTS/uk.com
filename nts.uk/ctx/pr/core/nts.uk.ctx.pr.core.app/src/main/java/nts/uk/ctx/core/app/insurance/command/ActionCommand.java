package nts.uk.ctx.core.app.insurance.command;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.GenerationType;

import nts.arc.time.YearMonth;
import nts.uk.ctx.core.app.insurance.labor.unemployeerate.UnemployeeInsuranceRateDto;
import nts.uk.ctx.core.app.insurance.labor.unemployeerate.UnemployeeInsuranceRateItemDto;
import nts.uk.ctx.core.app.insurance.labor.unemployeerate.UnemployeeInsuranceRateItemSettingDto;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.BusinessName;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.insurance.RoundingMethod;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.InsuBizRateItem;
import nts.uk.ctx.pr.core.dom.insurance.labor.businesstype.BusinessTypeEnum;
import nts.uk.ctx.pr.core.dom.insurance.labor.businesstype.InsuranceBusinessType;
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

	// Default SetUnemployeeInsuranceRateItem
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

	// Convert UnemployeeInsuranceRateItem
	public static UnemployeeInsuranceRateItem convertUnemployeeInsuranceRateItem(
			UnemployeeInsuranceRateItemDto unemployeeInsuranceRateItemDto) {
		UnemployeeInsuranceRateItem unemployeeInsuranceRateItem = new UnemployeeInsuranceRateItem();
		if (unemployeeInsuranceRateItemDto.getCareerGroup().equals(0)) {
			unemployeeInsuranceRateItem.setCareerGroup(CareerGroup.Agroforestry);
		}
		if (unemployeeInsuranceRateItemDto.getCareerGroup().equals(1)) {
			unemployeeInsuranceRateItem.setCareerGroup(CareerGroup.Contruction);
		}
		if (unemployeeInsuranceRateItemDto.getCareerGroup().equals(2)) {
			unemployeeInsuranceRateItem.setCareerGroup(CareerGroup.Other);
		}
		return unemployeeInsuranceRateItem;
	}

	// Convert UnemployeeInsuranceRateItemSetting
	public static UnemployeeInsuranceRateItemSetting convertUnemployeeInsuranceRateItemSetting(
			UnemployeeInsuranceRateItemSettingDto unemployeeInsuranceRateItemSettingDto) {
		UnemployeeInsuranceRateItemSetting unemployeeInsuranceRateItemSetting = new UnemployeeInsuranceRateItemSetting();
		unemployeeInsuranceRateItemSetting.setRate(unemployeeInsuranceRateItemSettingDto.getRate());
		unemployeeInsuranceRateItemSetting
				.setRoundAtr(convertRoundingMethod(unemployeeInsuranceRateItemSettingDto.getRoundAtr()));
		return unemployeeInsuranceRateItemSetting;
	}

	// Convert RoundingMethod
	public static RoundingMethod convertRoundingMethod(Integer round) {

		if (round == 0) {
			return RoundingMethod.RoundUp;
		}
		if (round == 1) {
			return RoundingMethod.Truncation;
		}
		if (round == 2) {
			return RoundingMethod.RoundDown;
		}
		if (round == 3) {
			return RoundingMethod.Down5_Up6;
		}
		if (round == 4) {
			return RoundingMethod.Down4_Up5;
		}
		return RoundingMethod.RoundUp;
	}

	// Convert SetUnemployeeInsuranceRateItem
	public static Set<UnemployeeInsuranceRateItem> defaultSetUnemployeeInsuranceRateItem(
			UnemployeeInsuranceRateDto unemployeeInsuranceRate) {
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

	// Default UnemployeeInsuranceRateItem
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

	// Default Set InsuBizRateItem
	public static Set<InsuBizRateItem> defaultSetInsuBizRateItem() {
		Set<InsuBizRateItem> setInsuBizRateItem = new HashSet<InsuBizRateItem>();
		InsuBizRateItem insuBizRateItemBiz1St = defaultInsuBizRateItem(BusinessTypeEnum.Biz1St);
		setInsuBizRateItem.add(insuBizRateItemBiz1St);
		InsuBizRateItem insuBizRateItemBiz2Nd = defaultInsuBizRateItem(BusinessTypeEnum.Biz2Nd);
		setInsuBizRateItem.add(insuBizRateItemBiz2Nd);
		InsuBizRateItem insuBizRateItemBiz3Rd = defaultInsuBizRateItem(BusinessTypeEnum.Biz3Rd);
		setInsuBizRateItem.add(insuBizRateItemBiz3Rd);
		InsuBizRateItem insuBizRateItemBiz4Th = defaultInsuBizRateItem(BusinessTypeEnum.Biz4Th);
		setInsuBizRateItem.add(insuBizRateItemBiz4Th);
		InsuBizRateItem insuBizRateItemBiz5Th = defaultInsuBizRateItem(BusinessTypeEnum.Biz5Th);
		setInsuBizRateItem.add(insuBizRateItemBiz5Th);
		InsuBizRateItem insuBizRateItemBiz6Th = defaultInsuBizRateItem(BusinessTypeEnum.Biz6Th);
		setInsuBizRateItem.add(insuBizRateItemBiz6Th);
		InsuBizRateItem insuBizRateItemBiz7Th = defaultInsuBizRateItem(BusinessTypeEnum.Biz7Th);
		setInsuBizRateItem.add(insuBizRateItemBiz7Th);
		InsuBizRateItem insuBizRateItemBiz8Th = defaultInsuBizRateItem(BusinessTypeEnum.Biz8Th);
		setInsuBizRateItem.add(insuBizRateItemBiz8Th);
		InsuBizRateItem insuBizRateItemBiz9Th = defaultInsuBizRateItem(BusinessTypeEnum.Biz9Th);
		setInsuBizRateItem.add(insuBizRateItemBiz9Th);
		InsuBizRateItem insuBizRateItemBiz10Th = defaultInsuBizRateItem(BusinessTypeEnum.Biz10Th);
		setInsuBizRateItem.add(insuBizRateItemBiz10Th);
		return setInsuBizRateItem;
	}

	// Default InsuBizRateItem
	public static InsuBizRateItem defaultInsuBizRateItem(BusinessTypeEnum businessTypeEnum) {
		InsuBizRateItem insuBizRateItem = new InsuBizRateItem();
		insuBizRateItem.setInsuBizType(businessTypeEnum);
		insuBizRateItem.setInsuRate(0.00d);
		insuBizRateItem.setInsuRound(RoundingMethod.RoundUp);
		return insuBizRateItem;
	}

	// Default InsuranceBusinessType
	public static InsuranceBusinessType detaultInsuranceBusinessType(String companyCode, String bizName,
			BusinessTypeEnum bizOrder) {
		InsuranceBusinessType insuranceBusinessType = new InsuranceBusinessType();
		insuranceBusinessType.setBizOrder(bizOrder);
		insuranceBusinessType.setBizName(new BusinessName(bizName));
		insuranceBusinessType.setCompanyCode(new CompanyCode(companyCode));
		return insuranceBusinessType;
	}
}
