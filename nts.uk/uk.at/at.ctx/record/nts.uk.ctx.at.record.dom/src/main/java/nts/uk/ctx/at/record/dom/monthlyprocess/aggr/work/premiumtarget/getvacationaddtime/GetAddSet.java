package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.premiumtarget.getvacationaddtime;

import java.util.Optional;

import lombok.val;
import nts.uk.ctx.at.shared.dom.calculation.holiday.CalcActualOperationAtr;
import nts.uk.ctx.at.shared.dom.calculation.holiday.HolidayAddtionSet;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;

/**
 * 加算設定を取得する
 * @author shuichu_ishida
 */
public class GetAddSet {

	/**
	 * 取得
	 * @param workingsystem 労働制
	 * @param premiumAtr 割増区分
	 * @param holidayAdditionOpt 休暇加算時間設定
	 * @return 加算設定
	 */
	public static AddSet get(WorkingSystem workingsystem, PremiumAtr premiumAtr, Optional<HolidayAddtionSet> holidayAdditionOpt){
		
		AddSet returnAddSet = new AddSet();

		//*****（未）　休暇加算時間設定関連の設計・実装部分の見直しが入るので、一旦、保留。（2018.2.14 shuichi_ishida）
		/*
		// 休暇加算時間設定がなければ、全てfalseで返却
		if (!holidayAdditionOpt.isPresent()) return returnAddSet;
		val holidayAddition = holidayAdditionOpt.get();

		// 休暇加算時間設定を適用するか
		boolean isApplyHolidayAddition = false;
		
		// 割増区分を確認する
		switch (premiumAtr){
		case PREMIUM:
			switch (workingsystem){
			case REGULAR_WORK:
				val addSetOfReg = holidayAddition.getRegularWork();
				// 割増計算方法．実働のみで計算するしない区分
				if (addSetOfReg.getCalcActualOperation1() == CalcActualOperationAtr.CALC_OTHER_ACTUAL_TIME){
					// 加算する＝「する(1)」
					if (addSetOfReg.getAdditionTime1() == 1) isApplyHolidayAddition = true;
				}
				break;
			case VARIABLE_WORKING_TIME_WORK:
				val addSetOfIrg = holidayAddition.getIrregularWork();
				// 割増計算方法．実働のみで計算するしない区分
				if (addSetOfIrg.getCalcActualOperation1() == CalcActualOperationAtr.CALC_OTHER_ACTUAL_TIME){
					// 加算する＝「する(1)」
					if (addSetOfIrg.getAdditionTime1() == 1) isApplyHolidayAddition = true;
				}
				break;
			case FLEX_TIME_WORK:
				val addSetOfFlx = holidayAddition.getFlexWork();
				// 割増計算方法．実働のみで計算するしない区分
				if (addSetOfFlx.getCalcActualOperation1() == CalcActualOperationAtr.CALC_OTHER_ACTUAL_TIME){
					// 加算する＝「する(1)」
					if (addSetOfFlx.getAdditionTime1() == 1) isApplyHolidayAddition = true;
				}
				break;
			default:
				break;
			}
			break;
		case WHEN_SHORTAGE:
			switch (workingsystem){
			case REGULAR_WORK:
				val addSetOfReg = holidayAddition.getRegularWork();
				// 就業時間計算方法．実働のみで計算するしない区分
				if (addSetOfReg.getCalcActualOperation2() == CalcActualOperationAtr.CALC_OTHER_ACTUAL_TIME){
					// 加算する＝「する(1)」
					if (addSetOfReg.getAdditionTime2() == 1) isApplyHolidayAddition = true;
				}
				break;
			case VARIABLE_WORKING_TIME_WORK:
				val addSetOfIrg = holidayAddition.getIrregularWork();
				// 就業時間計算方法．実働のみで計算するしない区分
				if (addSetOfIrg.getCalcActualOperation2() == CalcActualOperationAtr.CALC_OTHER_ACTUAL_TIME){
					// 加算する＝「する(1)」
					if (addSetOfIrg.getAdditionTime2() == 1) isApplyHolidayAddition = true;
				}
				break;
			case FLEX_TIME_WORK:
				val addSetOfFlx = holidayAddition.getFlexWork();
				// 就業時間計算方法．実働のみで計算するしない区分
				if (addSetOfFlx.getCalcActualOperation2() == CalcActualOperationAtr.CALC_OTHER_ACTUAL_TIME){
					// 加算する＝「する(1)」
					if (addSetOfFlx.getAdditionTime2() == 1) isApplyHolidayAddition = true;
				}
				break;
			default:
				break;
			}
			break;
		case ONLY_LEGAL:
			val addSetOfFlx = holidayAddition.getFlexWork();
			// 加算する＝「する(1)」
			//*****（未）　加算する判定は属性「月次法定内のみ加算」で判定する設計だが、実装がまだ
			if (addSetOfFlx.getAdditionTime2() == 1) isApplyHolidayAddition = true;
			break;
		}
		
		// 休暇加算時間設定を適用する
		if (isApplyHolidayAddition){
			returnAddSet = AddSet.of(
					(holidayAddition.getAnnualHoliday() == 1),
					(holidayAddition.getYearlyReserved() == 1),
					(holidayAddition.getSpecialHoliday() == 1));
		}
		*/
		
		return returnAddSet;
	}
}
