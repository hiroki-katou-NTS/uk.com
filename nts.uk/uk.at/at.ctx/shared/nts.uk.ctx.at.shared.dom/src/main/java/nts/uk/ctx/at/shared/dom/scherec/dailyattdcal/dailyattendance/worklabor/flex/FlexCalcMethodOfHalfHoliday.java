package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worklabor.flex;

import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.BreakDownTimeDay;
import nts.uk.ctx.at.shared.dom.worktype.WorkAtr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;

/**
 * フレックス勤務時の半日休日の計算方法
 * @author shuichi_ishida
 */
@Getter
@NoArgsConstructor
public class FlexCalcMethodOfHalfHoliday {

	/** 不足計算 */
	private FlexCalcMethod calcLack = FlexCalcMethod.PREDETERMINED_TIME_HALF_DAY;
	/** 割増計算 */
	private FlexCalcMethod calcPremium = FlexCalcMethod.PREDETERMINED_TIME_HALF_DAY;
	
	/**
	 * ファクトリー
	 * @param calcLack 不足計算
	 * @param calcPremium 割増計算
	 * @return フレックス勤務時の半日休日の計算方法
	 */
	public static FlexCalcMethodOfHalfHoliday createJavaType(int calcLack, int calcPremium){
		FlexCalcMethodOfHalfHoliday myclass = new FlexCalcMethodOfHalfHoliday();
		myclass.calcLack = EnumAdaptor.valueOf(calcLack, FlexCalcMethod.class);
		myclass.calcPremium = EnumAdaptor.valueOf(calcPremium, FlexCalcMethod.class);
		return myclass;
	}
	
	/**
	 * 所定控除時間の計算
	 * @param workType 勤務種類
	 * @param workTypeClass 勤務種類の分類
	 * @param isCalcPremium 割増計算かどうか
	 * @param breakDownOfPredTime 所定時間の内訳
	 * @return 所定控除時間
	 */
	public AttendanceTime calcDeductPredeterminedTime(
			WorkType workType,
			WorkTypeClassification workTypeClass,
			boolean isCalcPremium,
			BreakDownTimeDay breakDownOfPredTime){
		
		Optional<WorkAtr> workAtrOpt = workType.getWorkAtr(workTypeClass);		// 1日午前午後区分
		if (!workAtrOpt.isPresent()) return AttendanceTime.ZERO;
		WorkAtr workAtr = workAtrOpt.get();
		if (workAtr.isMorning() || workAtr.isAfterNoon()){
			FlexCalcMethod flexCalcMethod = isCalcPremium ? this.calcPremium : this.calcLack;	// フレックス計算方法
			if (flexCalcMethod == FlexCalcMethod.PREDETERMINED_TIME_ONE_DAY){
				return breakDownOfPredTime.get(WorkAtr.OneDay);
			}
		}
		return breakDownOfPredTime.get(workAtr);
	}
}
