package nts.uk.ctx.at.shared.dom.worktime.commonsetting;

import java.util.ArrayList;
import java.util.List;

import lombok.val;
import nts.uk.ctx.at.shared.dom.common.time.HasTimeSpanList;
import nts.uk.ctx.at.shared.dom.worktimeset_old.Timezone;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 現在の勤務回数に合致する勤務Noをもつ所定時間の保持クラス
 * @author keisuke_hoshina
 *
 */
public class TimeSheetList implements HasTimeSpanList<Timezone>  {

	private final List<Timezone> timeSheets;
	
	public TimeSheetList(List<Timezone> timeSheets) {
		this.timeSheets = new ArrayList<>(timeSheets);
	}
	
	@Override
	public List<Timezone> getTimeSpanList() {
		return this.timeSheets;
	}
	
	public void correctTimeSheet(TimeWithDayAttr start, TimeWithDayAttr end) {
		val corrected = this.extractBetween(start, end);
		this.timeSheets.clear();
		this.timeSheets.addAll(corrected);
	}
	
	public TimeWithDayAttr startOfDay(int count) {
		return this.timeSheets.stream().filter(tc -> tc.getWorkNo() == count).findFirst().get().getStart();
	}
	
	public TimeWithDayAttr endOfDay(int count) {
		return this.timeSheets.stream().filter(tc -> tc.getWorkNo() == count).findFirst().get().getEnd();
	}		
}
