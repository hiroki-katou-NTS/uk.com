package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.ArrayList;
import java.util.List;

import lombok.Value;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * 残業枠時間を計算するためのクラス
 * @author keisuke_hoshina
 *
 */
@Value
public class ControlOverFrameTime {
	private List<OverTimeWorkFrameTime> overTimeWorkFrameTime;
	
	/**
	 * 残業枠時間の残業・振替時間の集計を行う
	 */
	public void correct() {
		List<OverTimeWorkFrameTime> returnList = new ArrayList<>(10);
		for(int overTimeWorkNo = 0 ; overTimeWorkNo < overTimeWorkFrameTime.size() ; overTimeWorkNo++) {
			OverTimeWorkFrameTime a  = returnList.get(overTimeWorkFrameTime.get(overTimeWorkNo).getOverWorkFrameNo().v().intValue());
			
			TimeWithCalculation ove_a = TimeWithCalculation.createTimeWithCalculation( new AttendanceTime(a.getOverTimeWork().getTime().valueAsMinutes()+overTimeWorkFrameTime.get(overTimeWorkNo).getOverTimeWork().getTime().valueAsMinutes())
															  ,new AttendanceTime(a.getOverTimeWork().getCalcTime().valueAsMinutes()+overTimeWorkFrameTime.get(overTimeWorkNo).getOverTimeWork().getCalcTime().valueAsMinutes()));
			TimeWithCalculation tran_a = TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(a.getTransferTime().getTime().valueAsMinutes()+overTimeWorkFrameTime.get(overTimeWorkNo).getTransferTime().getTime().valueAsMinutes())
					  										  ,new AttendanceTime(a.getTransferTime().getCalcTime().valueAsMinutes()+overTimeWorkFrameTime.get(overTimeWorkNo).getTransferTime().getCalcTime().valueAsMinutes()));
			
			
			
			OverTimeWorkFrameTime b  = new OverTimeWorkFrameTime(a.getOverWorkFrameNo()
																,ove_a
					   											,tran_a
					   											,a.getBeforeApplicationTime());
			returnList.set(overTimeWorkFrameTime.get(overTimeWorkNo).getOverWorkFrameNo().v().intValue(),b);
		}
	}
}
