package nts.uk.ctx.at.request.app.command.application.holidayshipment;

import lombok.Getter;

@Getter
public class HolidayShipmentCommand {
	private String absAppID;
	private String recAppID;
	private Long appVersion;
	private String memo;
}
