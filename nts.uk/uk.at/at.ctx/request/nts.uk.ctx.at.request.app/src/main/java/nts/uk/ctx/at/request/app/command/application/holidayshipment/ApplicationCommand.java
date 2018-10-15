package nts.uk.ctx.at.request.app.command.application.holidayshipment;

import lombok.Getter;

@Getter
public class ApplicationCommand {

	private String appReasonText;

	private String applicationReason;

	private int prePostAtr;

	private String employeeID;

	private Long appVersion;
	
	private Integer remainDays;

}
