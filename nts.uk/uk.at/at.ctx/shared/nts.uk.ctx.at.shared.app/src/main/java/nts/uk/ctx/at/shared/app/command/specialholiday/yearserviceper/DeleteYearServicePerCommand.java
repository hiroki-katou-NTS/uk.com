package nts.uk.ctx.at.shared.app.command.specialholiday.yearserviceper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteYearServicePerCommand {
	public String specialHolidayCode;
	public String yearServiceCode;
}
