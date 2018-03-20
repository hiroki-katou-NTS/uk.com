package nts.uk.ctx.at.request.app.command.application.holidayshipment;

import lombok.Getter;

@Getter
public class RecruitmentAppCommand {

	private String appDate;
	private String wkTypeCD;
	private String workLocationCD;
	private WkTimeCommand wkTime1;
	private WkTimeCommand wkTime2;

}
