package nts.uk.ctx.at.shared.dom.worktime.CommonSetting;

import java.util.ArrayList;
import java.util.List;

import lombok.val;
import nts.uk.ctx.at.shared.dom.common.time.HasTimeSpanList;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 現在の勤務回数に合致する勤務Noをもつ所定時間の保持クラス
 * @author keisuke_hoshina
 *
 */
public class TimeSheetList implements HasTimeSpanList<TimeSheetWithUseAtr>  {

	private final List<TimeSheetWithUseAtr> timeSheets;
	
	public TimeSheetList(List<TimeSheetWithUseAtr> timeSheets) {
		this.timeSheets = new ArrayList<>(timeSheets);
	}
	
	@Override
	public List<TimeSheetWithUseAtr> getTimeSpanList() {
		return this.timeSheets;
	}
	
	public void correctTimeSheet(TimeWithDayAttr start, TimeWithDayAttr end) {
		val corrected = this.extractBetween(start, end);
		this.timeSheets.clear();
		this.timeSheets.addAll(corrected);
	}
	
	public TimeWithDayAttr startOfDay(int count) {
		return this.timeSheets.stream().filter(tc -> tc.getCount() == count).findFirst().get().getStartTime();
	}
	
	public TimeWithDayAttr endOfDay(int count) {
		return this.timeSheets.stream().filter(tc -> tc.getCount() == count).findFirst().get().getEndTime();
	}		
}
