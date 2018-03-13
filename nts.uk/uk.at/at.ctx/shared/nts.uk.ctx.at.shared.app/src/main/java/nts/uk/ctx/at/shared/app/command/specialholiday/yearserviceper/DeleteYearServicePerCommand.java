package nts.uk.ctx.at.shared.app.command.specialholiday.yearserviceper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteYearServicePerCommand {
	public int specialHolidayCode;
	public String yearServiceCode;
}
