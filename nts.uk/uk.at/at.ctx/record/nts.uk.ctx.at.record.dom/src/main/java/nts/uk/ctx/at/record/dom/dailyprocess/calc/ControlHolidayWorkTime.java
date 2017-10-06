package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.ArrayList;
import java.util.List;

import lombok.Value;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;
import nts.uk.ctx.at.record.dom.daily.holidaywork.HolidayWorkFrameTime;

/**
 * 休出枠時間を計算するためのクラス
 * @author keisuke_hoshina
 *
 */
@Value
public class ControlHolidayWorkTime {
	private List<HolidayWorkFrameTime> holidayWorkFrame;
	
	/**
	 * 残業枠時間の残業・振替時間の集計を行う
	 */
	public void correct() {
		List<HolidayWorkFrameTime> returnList = new ArrayList<>(10);
		for(int holidayWorkNo = 0 ; holidayWorkNo < holidayWorkFrame.size() ; holidayWorkNo++) {
			HolidayWorkFrameTime a  = returnList.get(holidayWorkFrame.get(holidayWorkNo).getHolidayFrameNo().v());
			
			TimeWithCalculation ove_a = TimeWithCalculation.of(a.getHolidayWorkTime().getTime()+holidayWorkFrame.get(holidayWorkNo).getHolidayWorkTime().getTime()
															  ,a.getHolidayWorkTime().getCalcTime()+holidayWorkFrame.get(holidayWorkNo).getHolidayWorkTime().getCalcTime());
			TimeWithCalculation tran_a = TimeWithCalculation.of(a.getTransferTime().getTime()+holidayWorkFrame.get(holidayWorkNo).getTransferTime().getTime()
					  										  ,a.getTransferTime().getCalcTime()+holidayWorkFrame.get(holidayWorkNo).getTransferTime().getCalcTime());
			
			
			
			HolidayWorkFrameTime b  = new HolidayWorkFrameTime(a.getHolidayFrameNo()
																,ove_a
					   											,tran_a
					   											,a.getBeforeApplicationTime());
			returnList.set(holidayWorkFrame.get(holidayWorkNo).getHolidayFrameNo().v(),b);
		}
	}
}
