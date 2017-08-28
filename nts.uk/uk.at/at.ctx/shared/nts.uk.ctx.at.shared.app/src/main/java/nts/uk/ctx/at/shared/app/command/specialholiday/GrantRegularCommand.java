package nts.uk.ctx.at.shared.app.command.specialholiday;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GrantRegularCommand {
	
	private String specialHolidayCode;
	
	private String grantStartDate;
	
	private int months;
	
	private int years;
	
	private int grantRegularMethod;
	
}
