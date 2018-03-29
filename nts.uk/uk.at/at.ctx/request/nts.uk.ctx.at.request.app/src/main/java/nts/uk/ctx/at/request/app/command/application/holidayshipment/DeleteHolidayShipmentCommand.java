package nts.uk.ctx.at.request.app.command.application.holidayshipment;

import lombok.Getter;

@Getter
public class DeleteHolidayShipmentCommand {
	private String absAppID;
	private String recAppID;
	private Long appVersion;
}
