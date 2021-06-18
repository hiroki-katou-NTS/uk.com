package nts.uk.ctx.at.shared.app.command.specialholiday.grantinformation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ElapseYearMonthCommand {

	/** 年 */
	private int year; 
	
	/** 月 */
	private int month; 
}
