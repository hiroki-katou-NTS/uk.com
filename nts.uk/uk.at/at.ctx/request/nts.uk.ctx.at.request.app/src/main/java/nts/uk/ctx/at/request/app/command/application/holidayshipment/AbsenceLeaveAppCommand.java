package nts.uk.ctx.at.request.app.command.application.holidayshipment;

import lombok.Getter;

@Getter
public class AbsenceLeaveAppCommand {
	private String appDate;
	private String wkTypeCD;
	private int changeWorkHoursType;
	private WkTimeCommand wkTime1;
	private WkTimeCommand wkTime2;
	private String appID;
}
