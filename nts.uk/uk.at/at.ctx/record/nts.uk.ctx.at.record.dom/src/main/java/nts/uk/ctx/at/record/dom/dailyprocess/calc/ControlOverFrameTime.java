package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Value;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;

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
			OverTimeWorkFrameTime a  = returnList.get(overTimeWorkFrameTime.get(overTimeWorkNo).getOverWorkFrameNo());
			
			TimeWithCalculation ove_a = TimeWithCalculation.of(a.getOverTimeWork().getTime()+overTimeWorkFrameTime.get(overTimeWorkNo).getOverTimeWork().getTime()
															  ,a.getOverTimeWork().getCalcTime()+overTimeWorkFrameTime.get(overTimeWorkNo).getOverTimeWork().getCalcTime());
			TimeWithCalculation tran_a = TimeWithCalculation.of(a.getTransferTime().getTime()+overTimeWorkFrameTime.get(overTimeWorkNo).getTransferTime().getTime()
					  										  ,a.getTransferTime().getCalcTime()+overTimeWorkFrameTime.get(overTimeWorkNo).getTransferTime().getCalcTime());
			
			
			
			OverTimeWorkFrameTime b  = new OverTimeWorkFrameTime(a.getOverWorkFrameNo()
																,ove_a
					   											,tran_a
					   											,a.getBeforeApplicationTime());
			returnList.set(overTimeWorkFrameTime.get(overTimeWorkNo).getOverWorkFrameNo(),b);
		}
	}
}
