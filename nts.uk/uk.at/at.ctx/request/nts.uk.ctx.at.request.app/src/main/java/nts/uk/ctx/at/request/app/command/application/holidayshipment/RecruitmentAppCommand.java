package nts.uk.ctx.at.request.app.command.application.holidayshipment;

import lombok.Getter;
import nts.arc.time.GeneralDate;

@Getter
public class RecruitmentAppCommand {

	private GeneralDate appDate;
	private String wkTypeCD;
	private WkTimeCommand wkTime1;
	private WkTimeCommand wkTime2;
	private String appID;

}
