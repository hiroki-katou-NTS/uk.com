package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.premiumtarget.getvacationaddtime;

import java.util.Map;
import java.util.Optional;

import lombok.val;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.MonthlyAggregationErrorInfo;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageContent;
import nts.uk.ctx.at.shared.dom.calculation.holiday.HolidayAddtionSet;
import nts.uk.ctx.at.shared.dom.calculation.holiday.WorkDeformedLaborAdditionSet;
import nts.uk.ctx.at.shared.dom.calculation.holiday.WorkFlexAdditionSet;
import nts.uk.ctx.at.shared.dom.calculation.holiday.WorkRegularAdditionSet;
import nts.uk.ctx.at.shared.dom.calculation.holiday.kmk013_splitdomain.HolidayCalcMethodSet;
import nts.uk.ctx.at.shared.dom.calculation.holiday.kmk013_splitdomain.ENUM.CalcurationByActualTimeAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.i18n.TextResource;

/**
 * 加算設定を取得する
 * @author shuichi_ishida
 */
public class GetAddSet {

	/**
	 * 取得
	 * @param workingsystem 労働制
	 * @param premiumAtr 割増区分
	 * @param holidayAdditionMap 休暇加算時間設定
	 * @return 加算設定
	 */
	public static AddSet get(WorkingSystem workingsystem, PremiumAtr premiumAtr, Map<String, AggregateRoot> holidayAdditionMap){
		
		AddSet resultNotExist = new AddSet();

		// 休暇加算時間設定　取得
		HolidayAddtionSet holidayAddition = null;
		if (holidayAdditionMap.containsKey("holidayAddtionSet")) {
			holidayAddition = (HolidayAddtionSet)holidayAdditionMap.get("holidayAddtionSet");
		}

		// 休暇の計算方法の設定を確認する
		HolidayCalcMethodSet holidayCalcMethodSet = null;
		switch (workingsystem){
		case REGULAR_WORK:
			resultNotExist = AddSet.of(false, false, false, Optional.of(new MonthlyAggregationErrorInfo(
					"019", new ErrMessageContent(TextResource.localize("Msg_1413", "通常勤務")))));
			if (!holidayAdditionMap.containsKey("regularWork")) break;
			val setOfRegular = (WorkRegularAdditionSet)holidayAdditionMap.get("regularWork");
			holidayCalcMethodSet = setOfRegular.getVacationCalcMethodSet();
			break;
		case VARIABLE_WORKING_TIME_WORK:
			resultNotExist = AddSet.of(false, false, false, Optional.of(new MonthlyAggregationErrorInfo(
					"019", new ErrMessageContent(TextResource.localize("Msg_1413", "変形労働勤務")))));
			if (!holidayAdditionMap.containsKey("irregularWork")) break;
			val setOfIrregular = (WorkDeformedLaborAdditionSet)holidayAdditionMap.get("irregularWork");
			holidayCalcMethodSet = setOfIrregular.getVacationCalcMethodSet();
			break;
		case FLEX_TIME_WORK:
			resultNotExist = AddSet.of(false, false, false, Optional.of(new MonthlyAggregationErrorInfo(
					"019", new ErrMessageContent(TextResource.localize("Msg_1413", "フレックス勤務")))));
			if (!holidayAdditionMap.containsKey("flexWork")) break;
			val setOfFlex = (WorkFlexAdditionSet)holidayAdditionMap.get("flexWork");
			holidayCalcMethodSet = setOfFlex.getVacationCalcMethodSet();
			break;
		default:
			resultNotExist = AddSet.of(false, false, false, Optional.of(new MonthlyAggregationErrorInfo(
					"019", new ErrMessageContent(TextResource.localize("Msg_1413", "計算対象外")))));
			break;
		}
		if (holidayCalcMethodSet == null) return resultNotExist;
		
		// 休暇加算時間設定を適用するか
		boolean isApplyHolidayAddition = false;
		
		// 割増区分を確認する
		switch (premiumAtr){
		case PREMIUM:
			// 割増時は、割増計算方法を確認する
			
			// 「実働のみで加算する」なら、加算しない
			val premiumCalcMethod = holidayCalcMethodSet.getPremiumCalcMethodOfHoliday();
			if (premiumCalcMethod.getCalculateActualOperation() == CalcurationByActualTimeAtr.CALCULATION_BY_ACTUAL_TIME) break;
			if (!premiumCalcMethod.getAdvanceSet().isPresent()) break;
			val premiumAdvanceSet = premiumCalcMethod.getAdvanceSet().get();
			
			// 休暇分を含め「加算する」なら、休暇加算時間設定を適用する
			if (premiumAdvanceSet.getIncludeVacationSet().getAddition() == NotUseAtr.NOT_USE) break;
			isApplyHolidayAddition = true;
			break;
			
		case WHEN_SHORTAGE:
			// 不足時は、就業時間計算方法を確認する
			
			// 「実働のみで加算する」なら、加算しない
			val workTimeCalcMethod = holidayCalcMethodSet.getWorkTimeCalcMethodOfHoliday();
			if (workTimeCalcMethod.getCalculateActualOperation() == CalcurationByActualTimeAtr.CALCULATION_BY_ACTUAL_TIME) break;
			if (!workTimeCalcMethod.getAdvancedSet().isPresent()) break;
			val workTimeAdvanceSet = workTimeCalcMethod.getAdvancedSet().get();
			
			// 休暇分を含め「加算する」なら、休暇加算時間設定を適用する
			if (workTimeAdvanceSet.getIncludeVacationSet().getAddition() == NotUseAtr.NOT_USE) break;
			isApplyHolidayAddition = true;
			break;
			
		case ONLY_LEGAL:
			// 法定内のみ時は、就業時間計算方法．月次法定内のみ加算を確認する
			
			// 「実働のみで加算する」なら、加算しない
			val legalCalcMethod = holidayCalcMethodSet.getWorkTimeCalcMethodOfHoliday();
			if (legalCalcMethod.getCalculateActualOperation() == CalcurationByActualTimeAtr.CALCULATION_BY_ACTUAL_TIME) break;
			if (!legalCalcMethod.getAdvancedSet().isPresent()) break;
			val legalAdvanceSet = legalCalcMethod.getAdvancedSet().get();
			
			// 「月次法定内のみ加算」＝「する」なら、休暇加算時間設定を適用する
			val addWithinMonthlyStatutory = legalAdvanceSet.getIncludeVacationSet().getAdditionWithinMonthlyStatutory();
			if (!addWithinMonthlyStatutory.isPresent()) break;
			if (addWithinMonthlyStatutory.get() == NotUseAtr.NOT_USE) break;
			isApplyHolidayAddition = true;
			break;
		}
		
		// 休暇加算時間設定を適用する
		if (isApplyHolidayAddition){
			if (holidayAddition == null) return resultNotExist;
			
			return AddSet.of(
					(holidayAddition.getAdditionVacationSet().getAnnualHoliday() == NotUseAtr.USE),
					(holidayAddition.getAdditionVacationSet().getYearlyReserved() == NotUseAtr.USE),
					(holidayAddition.getAdditionVacationSet().getSpecialHoliday() == NotUseAtr.USE),
					Optional.empty());
		}

		// 適用しない時は、すべてfalseにする　※　取得できたことにする
		return AddSet.of(false, false, false, Optional.empty());
	}
}
