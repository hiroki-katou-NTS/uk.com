package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.outsideworktime;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalOvertimeSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.TimeLimitUpperLimitSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeDivergenceWithCalculation;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;

/**
 * @author thanh_nx
 *
 *         残業枠時間一覧
 */
@AllArgsConstructor
public class OverTimeFrameTimeList {

	//一覧
	private List<OverTimeFrameTime> calcOverTimeWorkTimeList;
	
	/**
	 * 事前申請上限制御処理
	 * 
	 * @param calcOverTimeWorkTimeList 残業時間枠リスト
	 * @param autoCalcSet              残業時間の自動計算設定
	 */
	public List<OverTimeFrameTime> afterUpperControl(AutoCalOvertimeSetting autoCalcSet,
			List<OverTimeFrameNo> statutoryFrameNoList) {
		List<OverTimeFrameTime> returnList = new ArrayList<>();
		for (OverTimeFrameTime loopOverTimeFrame : this.calcOverTimeWorkTimeList) {

			TimeLimitUpperLimitSetting autoSet = autoCalcSet.getNormalOtTime().getUpLimitORtSet();
			if (statutoryFrameNoList != null && statutoryFrameNoList.contains(loopOverTimeFrame.getOverWorkFrameNo()))
				autoSet = autoCalcSet.getLegalOtTime().getUpLimitORtSet();

			// 時間の上限時間算出
			AttendanceTime upperTime = desictionUseUppserTime(autoSet, loopOverTimeFrame,
					loopOverTimeFrame.getOverTimeWork().getTime());
			// 計算時間の上限算出
//			AttendanceTime upperCalcTime = desictionUseUppserTime(autoSet,loopOverTimeFrame,  loopOverTimeFrame.getOverTimeWork().getCalcTime());
			// 振替処理
			loopOverTimeFrame = loopOverTimeFrame.changeOverTime(TimeDivergenceWithCalculation
					.createTimeWithCalculation(upperTime.greaterThan(loopOverTimeFrame.getOverTimeWork().getTime())
							? loopOverTimeFrame.getOverTimeWork().getTime()
							: upperTime,
//																														 upperCalcTime.greaterThan(loopOverTimeFrame.getOverTimeWork().getCalcTime())?loopOverTimeFrame.getOverTimeWork().getCalcTime():upperCalcTime)
							loopOverTimeFrame.getOverTimeWork().getCalcTime()));
			returnList.add(loopOverTimeFrame);
		}
		return returnList;
	}
	
	//時間の上限時間算出
	private AttendanceTime desictionUseUppserTime(TimeLimitUpperLimitSetting autoSet, OverTimeFrameTime loopOverTimeFrame, AttendanceTime attendanceTime) {
		switch(autoSet) {
		//上限なし
		case NOUPPERLIMIT:
			return attendanceTime;
		//指示時間を上限とする
		case INDICATEDYIMEUPPERLIMIT:
			return loopOverTimeFrame.getOrderTime();
		//事前申請を上限とする
		case LIMITNUMBERAPPLICATION:
			return loopOverTimeFrame.getBeforeApplicationTime();
		default:
			throw new RuntimeException("uknown AutoCalcAtr Over Time When Ot After Upper Control");
		}
	}
}
