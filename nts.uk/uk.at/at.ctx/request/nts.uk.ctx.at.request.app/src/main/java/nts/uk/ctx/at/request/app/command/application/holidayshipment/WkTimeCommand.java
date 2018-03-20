package nts.uk.ctx.at.request.app.command.application.holidayshipment;

import lombok.Getter;

@Getter
public class WkTimeCommand {
	private int startTime;
	private int startType;
	private int endTime;
	private int endType;
	private String wkTimeCD;
}
