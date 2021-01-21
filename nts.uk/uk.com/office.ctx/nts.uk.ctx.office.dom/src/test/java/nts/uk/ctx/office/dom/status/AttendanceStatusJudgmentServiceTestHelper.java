package nts.uk.ctx.office.dom.status;

import nts.uk.ctx.office.dom.goout.GoOutEmployeeInformation;


public class AttendanceStatusJudgmentServiceTestHelper {
	public static GoOutEmployeeInformation getGoOutInfo(Integer goOutTime, Integer comback) {
		GoOutEmployeeInformationDto dto = new GoOutEmployeeInformationDto();
		dto.setComebackTime(comback);
		dto.setGoOutTime(goOutTime);
		GoOutEmployeeInformation domain = GoOutEmployeeInformation.createFromMemento(dto);
		return domain;
	}
	
	public static ActivityStatus getStatus(Integer activity) {
		ActivityStatusDto dto = new ActivityStatusDto();
		dto.setActivity(activity);
		ActivityStatus domain = ActivityStatus.createFromMemento(dto); 
		return domain;
	}
}
