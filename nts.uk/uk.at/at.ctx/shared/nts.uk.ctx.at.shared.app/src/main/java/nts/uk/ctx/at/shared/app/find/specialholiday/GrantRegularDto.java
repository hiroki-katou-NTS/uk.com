package nts.uk.ctx.at.shared.app.find.specialholiday;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GrantRegularDto {
	
	private String specialHolidayCode;
	
	private GeneralDate grantStartDate;
	
	private Integer months;
	
	private Integer years;
	
	private int grantRegularMethod;
	
}
