package nts.uk.ctx.at.shared.app.command.specialholiday;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GrantRegularCommand {
	
	private String specialHolidayCode;
	
	private GeneralDate grantStartDate;
	
	private Integer months;
	
	private Integer years;
	
	private int grantRegularMethod;
	
}
