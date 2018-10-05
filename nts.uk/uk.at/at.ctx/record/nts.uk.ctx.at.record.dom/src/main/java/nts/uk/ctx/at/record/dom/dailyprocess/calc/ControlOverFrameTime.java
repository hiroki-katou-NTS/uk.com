package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.ArrayList;
import java.util.List;

import lombok.Value;
import nts.uk.ctx.at.record.dom.daily.TimeDivergenceWithCalculation;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * 残業枠時間を計算するためのクラス
 * @author keisuke_hoshina
 *
 */
@Value
public class ControlOverFrameTime {
	private List<OverTimeFrameTime> overTimeWorkFrameTime;
	
	/**
	 * 残業枠時間の残業・振替時間の集計を行う
	 */
	public void correct() {
		List<OverTimeFrameTime> returnList = new ArrayList<>(10);
		for(int overTimeWorkNo = 0 ; overTimeWorkNo < overTimeWorkFrameTime.size() ; overTimeWorkNo++) {
			OverTimeFrameTime overFrame  = returnList.get(overTimeWorkFrameTime.get(overTimeWorkNo).getOverWorkFrameNo().v().intValue());
			
			TimeDivergenceWithCalculation overTime = TimeDivergenceWithCalculation.createTimeWithCalculation( new AttendanceTime(overFrame.getOverTimeWork().getTime().valueAsMinutes()+overTimeWorkFrameTime.get(overTimeWorkNo).getOverTimeWork().getTime().valueAsMinutes())
															  ,new AttendanceTime(overFrame.getOverTimeWork().getCalcTime().valueAsMinutes()+overTimeWorkFrameTime.get(overTimeWorkNo).getOverTimeWork().getCalcTime().valueAsMinutes()));
			TimeDivergenceWithCalculation overTransTime = TimeDivergenceWithCalculation.createTimeWithCalculation(new AttendanceTime(overFrame.getTransferTime().getTime().valueAsMinutes()+overTimeWorkFrameTime.get(overTimeWorkNo).getTransferTime().getTime().valueAsMinutes())
					  										  ,new AttendanceTime(overFrame.getTransferTime().getCalcTime().valueAsMinutes()+overTimeWorkFrameTime.get(overTimeWorkNo).getTransferTime().getCalcTime().valueAsMinutes()));
			
			OverTimeFrameTime newOverFrame  = new OverTimeFrameTime(overFrame.getOverWorkFrameNo()
																,overTime
					   											,overTransTime
					   											,overFrame.getBeforeApplicationTime()
					   											//TODO: 指示時間 渡す
					   											,new AttendanceTime(0));
			returnList.set(overTimeWorkFrameTime.get(overTimeWorkNo).getOverWorkFrameNo().v().intValue(),newOverFrame);
		}
	}
}
