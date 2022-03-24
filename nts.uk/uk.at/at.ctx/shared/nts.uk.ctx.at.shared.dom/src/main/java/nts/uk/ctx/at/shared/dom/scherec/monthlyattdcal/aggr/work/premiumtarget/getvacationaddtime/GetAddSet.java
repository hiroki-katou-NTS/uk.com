package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.premiumtarget.getvacationaddtime;

import java.util.Map;
import java.util.Optional;

import lombok.val;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayAddtionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.AddSettingOfWorkingTime;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkDeformedLaborAdditionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkFlexAdditionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkRegularAdditionSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.MonthlyAggregationErrorInfo;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageContent;
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

		// 労働時間の加算設定を確認する
		AddSettingOfWorkingTime addSetOfWorkTime = null;
		switch (workingsystem){
		case REGULAR_WORK:
			resultNotExist = AddSet.of(false, false, false, Optional.of(new MonthlyAggregationErrorInfo(
					"019", new ErrMessageContent(TextResource.localize("Msg_1413", "通常勤務")))));
			if (!holidayAdditionMap.containsKey("regularWork")) break;
			val setOfRegular = (WorkRegularAdditionSet)holidayAdditionMap.get("regularWork");
			addSetOfWorkTime = setOfRegular.getAddSetOfWorkingTime();
			break;
		case VARIABLE_WORKING_TIME_WORK:
			resultNotExist = AddSet.of(false, false, false, Optional.of(new MonthlyAggregationErrorInfo(
					"019", new ErrMessageContent(TextResource.localize("Msg_1413", "変形労働勤務")))));
			if (!holidayAdditionMap.containsKey("irregularWork")) break;
			val setOfIrregular = (WorkDeformedLaborAdditionSet)holidayAdditionMap.get("irregularWork");
			addSetOfWorkTime = setOfIrregular.getAddSetOfWorkingTime();
			break;
		case FLEX_TIME_WORK:
			resultNotExist = AddSet.of(false, false, false, Optional.of(new MonthlyAggregationErrorInfo(
					"019", new ErrMessageContent(TextResource.localize("Msg_1413", "フレックス勤務")))));
			if (!holidayAdditionMap.containsKey("flexWork")) break;
			val setOfFlex = (WorkFlexAdditionSet)holidayAdditionMap.get("flexWork");
			addSetOfWorkTime = setOfFlex.getAddSetOfWorkingTime();
			break;
		default:
			resultNotExist = AddSet.of(false, false, false, Optional.of(new MonthlyAggregationErrorInfo(
					"019", new ErrMessageContent(TextResource.localize("Msg_1413", "計算対象外")))));
			break;
		}
		if (addSetOfWorkTime == null) return resultNotExist;
		
		// 休暇加算時間設定を適用するか
		boolean isApplyHolidayAddition = false;
		
		// 割増区分を確認する
		switch (premiumAtr){
		case PREMIUM:
			// 割増時は、割増計算方法を確認する
			
			// 休暇分を含め「加算する」なら、休暇加算時間設定を適用する
			if (addSetOfWorkTime.isAddVacation(nts.uk.ctx.at.shared.dom.PremiumAtr.Premium).isNotUse()) break;
			isApplyHolidayAddition = true;
			break;
			
		case WHEN_SHORTAGE:
			// 不足時は、就業時間計算方法を確認する
			
			// 休暇分を含め「加算する」なら、休暇加算時間設定を適用する
			if (addSetOfWorkTime.isAddVacation(nts.uk.ctx.at.shared.dom.PremiumAtr.RegularWork).isNotUse()) break;
			isApplyHolidayAddition = true;
			break;
			
		case ONLY_LEGAL:
			// 法定内のみ時は、就業時間計算方法．月次法定内のみ加算を確認する
			
			// 「月次法定内のみ加算」＝「する」なら、休暇加算時間設定を適用する
			if (addSetOfWorkTime.getAddSetOfWorkTime().isAdditionWithinMonthlyStatutory() == false) break;
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
