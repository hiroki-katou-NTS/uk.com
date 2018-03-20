package nts.uk.ctx.at.request.app.command.application.holidayshipment;

import lombok.Getter;

@Getter
public class AbsenceLeaveAppCommand {
	private String appDate;
	private String wkTypeCD;
	private int changeWorkHoursType;
	private String workLocationCD;
	private WkTimeCommand wkTime1;
	private WkTimeCommand wkTime2;
}
