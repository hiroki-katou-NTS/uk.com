package nts.uk.ctx.at.shared.app.command.specialholiday.grantinformation;

import lombok.NoArgsConstructor;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SpecialVacationDeadline {

	/** 月数 */
	private int months;
	
	/** 月数 */
	private int years;
}
