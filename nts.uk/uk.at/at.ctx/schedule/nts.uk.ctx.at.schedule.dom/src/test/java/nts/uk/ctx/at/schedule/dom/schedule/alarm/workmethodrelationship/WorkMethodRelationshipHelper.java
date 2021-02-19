package nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship;

import java.util.Arrays;

import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;

public class WorkMethodRelationshipHelper {
	public final static WorkMethodRelationship DUMMY = new WorkMethodRelationship(
			new WorkMethodAttendance(new WorkTimeCode("001")), 
			Arrays.asList(new WorkMethodHoliday()),
			RelationshipSpecifiedMethod.ALLOW_SPECIFY_WORK_DAY
			);
	
}
