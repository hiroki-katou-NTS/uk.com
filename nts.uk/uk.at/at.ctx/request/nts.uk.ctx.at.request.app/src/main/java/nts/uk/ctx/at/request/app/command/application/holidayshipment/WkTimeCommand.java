package nts.uk.ctx.at.request.app.command.application.holidayshipment;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WkTimeCommand {
	private Integer startTime;
	private Integer startType;
	private Integer endTime;
	private Integer endType;
}
