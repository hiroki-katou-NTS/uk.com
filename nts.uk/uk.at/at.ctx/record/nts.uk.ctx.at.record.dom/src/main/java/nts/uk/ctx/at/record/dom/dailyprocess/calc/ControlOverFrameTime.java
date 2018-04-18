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
			OverTimeFrameTime a  = returnList.get(overTimeWorkFrameTime.get(overTimeWorkNo).getOverWorkFrameNo().v().intValue());
			
			TimeDivergenceWithCalculation ove_a = TimeDivergenceWithCalculation.createTimeWithCalculation( new AttendanceTime(a.getOverTimeWork().getTime().valueAsMinutes()+overTimeWorkFrameTime.get(overTimeWorkNo).getOverTimeWork().getTime().valueAsMinutes())
															  ,new AttendanceTime(a.getOverTimeWork().getCalcTime().valueAsMinutes()+overTimeWorkFrameTime.get(overTimeWorkNo).getOverTimeWork().getCalcTime().valueAsMinutes()));
			TimeDivergenceWithCalculation tran_a = TimeDivergenceWithCalculation.createTimeWithCalculation(new AttendanceTime(a.getTransferTime().getTime().valueAsMinutes()+overTimeWorkFrameTime.get(overTimeWorkNo).getTransferTime().getTime().valueAsMinutes())
					  										  ,new AttendanceTime(a.getTransferTime().getCalcTime().valueAsMinutes()+overTimeWorkFrameTime.get(overTimeWorkNo).getTransferTime().getCalcTime().valueAsMinutes()));
			
			
			
			OverTimeFrameTime b  = new OverTimeFrameTime(a.getOverWorkFrameNo()
																,ove_a
					   											,tran_a
					   											,a.getBeforeApplicationTime()
					   											//TODO: 指示時間 渡す
					   											,new AttendanceTime(0));
			returnList.set(overTimeWorkFrameTime.get(overTimeWorkNo).getOverWorkFrameNo().v().intValue(),b);
		}
	}
}
