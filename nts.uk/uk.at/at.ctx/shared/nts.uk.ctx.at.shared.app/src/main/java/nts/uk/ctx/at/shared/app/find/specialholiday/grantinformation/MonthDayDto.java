package nts.uk.ctx.at.shared.app.find.specialholiday.grantinformation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MonthDayDto {

	/** number (1-12) */
	private int month;
	
	/** day (1-31) */
	private int day;
}
