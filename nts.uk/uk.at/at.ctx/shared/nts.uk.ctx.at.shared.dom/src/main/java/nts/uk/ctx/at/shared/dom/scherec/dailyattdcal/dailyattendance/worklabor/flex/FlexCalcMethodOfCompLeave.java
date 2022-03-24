package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worklabor.flex;

import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.BreakDownTimeDay;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.FlexMonthWorkTimeAggrSet;
import nts.uk.ctx.at.shared.dom.worktype.WorkAtr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;

/**
 * フレックス勤務時の代休取得時の計算方法
 * @author shuichi_ishida
 */
@Getter
public class FlexCalcMethodOfCompLeave {

	/** 所定から控除するかどうか */
	private boolean isDeductFromPred;
	/** 割増計算 */
	private FlexCalcMethod calcPremium;
	/** 時間代休時の計算設定 */
	private FlexCalcSetOfTimeCompLeave calcSetOfTimeCompLeave;
	
	/**
	 * コンストラクタ
	 */
	public FlexCalcMethodOfCompLeave(){
		this.isDeductFromPred = true;
		this.calcPremium = FlexCalcMethod.PREDETERMINED_TIME_HALF_DAY;
		this.calcSetOfTimeCompLeave = FlexCalcSetOfTimeCompLeave.DEDUCT_ON_COMPENSATORY_TIME;
	}

	/**
	 * ファクトリー
	 * @param isDeductFromPred 所定から控除するかどうか
	 * @param calcPremium 割増計算
	 * @param calcSetOfTimeCompLeave 時間代休時の計算設定
	 * @return フレックス勤務時の代休取得時の計算方法
	 */
	public static FlexCalcMethodOfCompLeave createJavaType(
			int isDeductFromPred,
			int calcPremium,
			int calcSetOfTimeCompLeave){
		
		FlexCalcMethodOfCompLeave myclass = new FlexCalcMethodOfCompLeave();
		myclass.isDeductFromPred = (isDeductFromPred == 1);
		myclass.calcPremium = EnumAdaptor.valueOf(calcPremium, FlexCalcMethod.class);
		myclass.calcSetOfTimeCompLeave = EnumAdaptor.valueOf(calcSetOfTimeCompLeave, FlexCalcSetOfTimeCompLeave.class);
		return myclass;
	}
	
	/**
	 * 日単位の所定控除時間の計算
	 * @param workType 勤務種類
	 * @param isCalcPremium 割増計算かどうか
	 * @param breakDownOfPredTime 所定時間の内訳
	 * @param flexBasicSet フレックス勤務基本設定
	 * @return 所定控除時間
	 */
	public AttendanceTime calcDeductPredTimeOfDay(
			WorkType workType,
			boolean isCalcPremium,
			BreakDownTimeDay breakDownOfPredTime,
			FlexMonthWorkTimeAggrSet flexBasicSet){
		
		Optional<WorkAtr> workAtrOpt = workType.getWorkAtr(WorkTypeClassification.SubstituteHoliday);	// 1日午前午後区分
		if (!workAtrOpt.isPresent()) return AttendanceTime.ZERO;
		WorkAtr workAtr = workAtrOpt.get();
		if (!this.isDeductFromPred) return AttendanceTime.ZERO;
		if (flexBasicSet.getFlexTimeHandle().isIncludeIllegalHdwk()) return AttendanceTime.ZERO;
		if (workAtr.isMorning() || workAtr.isAfterNoon()){
			if (isCalcPremium && this.calcPremium == FlexCalcMethod.PREDETERMINED_TIME_ONE_DAY){
				return AttendanceTime.ZERO;
			}
		}
		return breakDownOfPredTime.get(workAtr);
	}

	/**
	 * 時間単位の所定控除時間の計算
	 * @param timeCompLeaveOffsetTime 時間代休相殺時間
	 * @param timeCompLeaveUseTime 時間代休使用時間
	 * @param flexBasicSet フレックス勤務基本設定
	 * @return 所定控除時間
	 */
	public AttendanceTime calcDeductPredTimeOfTime(
			AttendanceTime timeCompLeaveOffsetTime,
			AttendanceTime timeCompLeaveUseTime,
			FlexMonthWorkTimeAggrSet flexBasicSet){
		
		if (!this.isDeductFromPred) return AttendanceTime.ZERO;
		if (flexBasicSet.getFlexTimeHandle().isIncludeIllegalHdwk()) return AttendanceTime.ZERO;
		if (this.calcSetOfTimeCompLeave == FlexCalcSetOfTimeCompLeave.DEDUCT_ON_LEAVEEARLY_LATE_OUTING_TIME_ONLY)
			return timeCompLeaveOffsetTime;
		return timeCompLeaveUseTime;
	}
}
