package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.ArrayList;
import java.util.List;

import lombok.Value;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.record.dom.daily.TimeDivergenceWithCalculation;
import nts.uk.ctx.at.record.dom.daily.holidayworktime.HolidayWorkFrameTime;

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
			HolidayWorkFrameTime holFrame  = returnList.get(holidayWorkFrame.get(holidayWorkNo).getHolidayFrameNo().v().intValue());
			
			TimeDivergenceWithCalculation holidayWorkTime = TimeDivergenceWithCalculation.createTimeWithCalculation(holFrame.getHolidayWorkTime().get().getTime().addMinutes(holidayWorkFrame.get(holidayWorkNo).getHolidayWorkTime().get().getTime().valueAsMinutes())
															  						 ,holFrame.getHolidayWorkTime().get().getCalcTime().addMinutes(holidayWorkFrame.get(holidayWorkNo).getHolidayWorkTime().get().getCalcTime().valueAsMinutes()));
			TimeDivergenceWithCalculation holidayWorkTransTime = TimeDivergenceWithCalculation.createTimeWithCalculation(holFrame.getTransferTime().get().getTime().addMinutes(holidayWorkFrame.get(holidayWorkNo).getTransferTime().get().getTime().valueAsMinutes())
																					 ,holFrame.getTransferTime().get().getCalcTime().addMinutes(holidayWorkFrame.get(holidayWorkNo).getTransferTime().get().getCalcTime().valueAsMinutes()));
			
			
			
			HolidayWorkFrameTime newHolFrame  = new HolidayWorkFrameTime(holFrame.getHolidayFrameNo()
																,Finally.of(holidayWorkTime)
					   											,Finally.of(holidayWorkTransTime)
					   											,holFrame.getBeforeApplicationTime());
			returnList.set(holidayWorkFrame.get(holidayWorkNo).getHolidayFrameNo().v().intValue(),newHolFrame);
		}
	}
}
