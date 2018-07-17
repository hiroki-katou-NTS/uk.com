package nts.uk.ctx.at.shared.app.command.specialholidaynew.grantinformation;

import lombok.Value;

@Value
public class DeleteGrantDateTblCommand {
	private String specialHolidayCode;

	private String grantDateCode;
}
