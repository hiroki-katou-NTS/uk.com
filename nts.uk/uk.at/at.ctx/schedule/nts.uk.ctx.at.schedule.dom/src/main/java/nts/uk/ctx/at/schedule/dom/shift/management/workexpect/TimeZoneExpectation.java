package nts.uk.ctx.at.schedule.dom.shift.management.workexpect;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanDuplication;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;

/**
 * 時間帯の勤務希望
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.シフト管理.シフト勤務.勤務希望
 * @author dan_pv
 */
@AllArgsConstructor
public class TimeZoneExpectation implements WorkExpectation{
	
	/**
	 * 勤務可能な時間帯のリスト
	 */
	@Getter
	private List<TimeSpanForCalc> workableTimeZoneList;
	
	public static TimeZoneExpectation create(List<TimeSpanForCalc> workableTimeZoneList) {
		return new TimeZoneExpectation(workableTimeZoneList);
	}

	@Override
	public AssignmentMethod getAssignmentMethod() {
		return AssignmentMethod.TIME_ZONE;
	}

	@Override
	public boolean isHolidayExpectation() {
		return false;
	}

	@Override
	public boolean isMatchingExpectation(Require require, WorkInformation workInformation,
			List<TimeSpanForCalc> timeZoneList) {
		
		if (timeZoneList.isEmpty()) {
			return false;
		}
		
		return timeZoneList.stream().allMatch( timeZone -> 
			
			 this.workableTimeZoneList.stream()
					.anyMatch( workableTimeZone -> this.isScheduleTimeZoneInsideExpectedTimeZone(workableTimeZone, timeZone))
		);
		
	}

	@Override
	public WorkExpectDisplayInfo getDisplayInformation(Require require) {
		return new WorkExpectDisplayInfo(AssignmentMethod.TIME_ZONE, new ArrayList<>(), this.workableTimeZoneList);
	}
	
	private boolean isScheduleTimeZoneInsideExpectedTimeZone(TimeSpanForCalc expectedTimeZone , TimeSpanForCalc scheduleTimeZone) {
		return expectedTimeZone.checkDuplication(scheduleTimeZone) == TimeSpanDuplication.CONTAINS;
	}

}
