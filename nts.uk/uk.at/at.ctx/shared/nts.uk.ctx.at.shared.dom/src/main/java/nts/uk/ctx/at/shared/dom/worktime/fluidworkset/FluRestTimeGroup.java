/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fluidworkset;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;

/**
 * The Class BreakTime.
 */
@Getter
@AllArgsConstructor
// 流動休憩時間帯
public class FluRestTimeGroup {

	// 流動休憩設定
	private List<FluRestTimeSetting> fluidRestTimes;
	// 設定以降の休憩を使用する
	private boolean useHereAfterRestSet;
	//設定以降の休憩設定
	private FluRestTimeSetting hereAfterRestSet;
	
	/**
	 * 設定以降の時間を含めて流動設定を取得
	 * @param attendanceLeaving 日別実績の出退勤　の　出退勤(List部分)
	 * 　　※実装の都合上、ここでは出退勤クラス自体をimportすることが不可能なため、TimeSpanForCalcとして受け取る
	 * @return　流動設定
	 */
	public List<FluRestTimeSetting> getFluRestTimeSetting(List<TimeSpanForCalc> attendanceLeaving) {
		if(useHereAfterRestSet) {
			TimeSpanForCalc maxRange = (attendanceLeaving.size() == 1)?attendanceLeaving.get(0): new TimeSpanForCalc(attendanceLeaving.get(0).getStart(),attendanceLeaving.get(1).getEnd());
			createFluBreakTimeSet(maxRange);
		}
		return this.fluidRestTimes;
	}
	
//	/**
//	 * 指定した経過時間に一致する流動休憩設定を取得する
//	 * @param 経過時間
//	 * @return　流動休憩設定
//	 */
//	public FluRestTimeSetting getMatchElapsedTime(AttendanceTime elapsedTime) {
//		List<FluRestTimeSetting> getList = this.fluidRestTimes.stream().filter(ts -> ts.getFluidElapsedTime()==elapsedTime).collect(Collectors.toList());
//		if(getList.size()>1) {
//			throw new RuntimeException("Exist duplicate elapsedTime : " + elapsedTime);
//		}
//		return getList.get(0);
//	}

	/**
	 * 設定以降の流動休憩設定を作成
	 * @param 範囲時間
	 */
	public void createFluBreakTimeSet(TimeSpanForCalc rangeTime) {
		AttendanceTime maxBreakTime = getMaxRangeBreakTimeSet();
		AttendanceTime BreakTimeRange = new AttendanceTime(rangeTime.lengthAsMinutes() - maxBreakTime.valueAsMinutes());
		int fluBreakTimeCount = BreakTimeRange.valueAsMinutes()/hereAfterRestSet.getFluidElapsedTime().valueAsMinutes();
		//流動休憩設定の作成
		for(int nowBreakTimeCount = 0 ;nowBreakTimeCount < fluBreakTimeCount ; nowBreakTimeCount++) {
			fluidRestTimes.add(new FluRestTimeSetting(hereAfterRestSet.getFluidElapsedTime().addMinutes(maxBreakTime.valueAsMinutes()),
													   hereAfterRestSet.getFluidRestTime()));
			maxBreakTime.addMinutes(hereAfterRestSet.getFluidElapsedTime().valueAsMinutes());
		}
	}
	
	/**
	 * 最大流動休憩設定の取得
	 */
	public AttendanceTime getMaxRangeBreakTimeSet() {
		return this.fluidRestTimes.stream().max((base,compare) -> base.getFluidElapsedTime().compareTo(compare.getFluidElapsedTime())).map(tc -> tc.getFluidRestTime()).get();
	}
}
