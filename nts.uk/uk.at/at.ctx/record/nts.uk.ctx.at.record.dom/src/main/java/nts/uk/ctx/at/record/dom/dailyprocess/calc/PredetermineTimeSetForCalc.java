package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.List;

import lombok.Value;

import nts.uk.ctx.at.shared.dom.worktime.CommomSetting.SetAdditionToWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.CommomSetting.TimeSheetWithUseAtr;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 所定時間設定(計算用クラス)
 * @author ken_takasu
 *
 */
@Value
public class PredetermineTimeSetForCalc {
	
	List<TimeSheetWithUseAtr> timeSheets;

	private final TimeWithDayAttr AMEndTime;

	private final TimeWithDayAttr PMStartTime;
	
	private SetAdditionToWorkTime additionSet;

	/**
	 * 所定時間帯の時間を更新する
	 * @param dateStartTime
	 * @param rangeTimeDay
	 * @param predetermineTimeSheet
	 * @param additionSet
	 * @param timeSheets
	 */
	public PredetermineTimeSetForCalc(
			SetAdditionToWorkTime additionSet,
			List<TimeSheetWithUseAtr> timeSheets,
			TimeWithDayAttr AMEndTime,
			TimeWithDayAttr PMStartTime) {
		this.timeSheets = timeSheets;
		this.AMEndTime = AMEndTime;
		this.PMStartTime = PMStartTime;
		this.additionSet = additionSet;	
	}
	
	
	
		
}
