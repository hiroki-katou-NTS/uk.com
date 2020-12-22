package nts.uk.ctx.at.schedule.dom.shift.management.shifttable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.DayOfWeek;
import nts.uk.ctx.at.schedule.dom.shift.management.workavailability.AssignmentMethod;
import nts.uk.ctx.at.schedule.dom.shift.management.workavailability.WorkAvailabilityMemo;
import nts.uk.ctx.at.schedule.dom.shift.management.workavailability.WorkAvailabilityOfOneDay;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterCode;
import nts.uk.shr.com.time.TimeWithDayAttr;

public class WorkAvailabilityRuleWeekSettingHelper {
	
	public static WorkAvailabilityRuleWeekSetting defaultCreate() {
		return new WorkAvailabilityRuleWeekSetting(DayOfWeek.SUNDAY, new DeadlineDayOfWeek(DeadlineWeekAtr.ONE_WEEK_AGO, DayOfWeek.THURSDAY));
				
	}
	
	public static WorkAvailabilityRuleWeekSetting createWithParam(DayOfWeek firstDayOfWeek, DeadlineWeekAtr weekAtr, DayOfWeek deadlineDayOfWeek) {
		return new WorkAvailabilityRuleWeekSetting(firstDayOfWeek, new DeadlineDayOfWeek(weekAtr, deadlineDayOfWeek));
	}
	
	public static WorkAvailabilityOfOneDay createExpectation(GeneralDate expectingDate, AssignmentMethod assignmentMethod) {
		
		List<ShiftMasterCode> shiftMasterCodeList = assignmentMethod == AssignmentMethod.SHIFT ? 
				Arrays.asList(new ShiftMasterCode("S-01")) : Collections.emptyList();
		
		List<TimeSpanForCalc> timeZoneList = assignmentMethod == AssignmentMethod.TIME_ZONE ?
				Arrays.asList(new TimeSpanForCalc(new TimeWithDayAttr(100), new TimeWithDayAttr(200))) : Collections.emptyList();
		
		return WorkAvailabilityOfOneDay.create("emp-id", 
				expectingDate,
				new WorkAvailabilityMemo("memo"), 
				assignmentMethod, 
				shiftMasterCodeList, 
				timeZoneList);
	}

}
