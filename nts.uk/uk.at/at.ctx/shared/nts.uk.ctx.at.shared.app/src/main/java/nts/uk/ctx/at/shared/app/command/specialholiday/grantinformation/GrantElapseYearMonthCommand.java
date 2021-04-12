package nts.uk.ctx.at.shared.app.command.specialholiday.grantinformation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GrantElapseYearMonthCommand {

	/** 付与回数 */
	private int elapseNo; 
	
	/** 付与日数 */
	private int grantedDays; 
	
}
