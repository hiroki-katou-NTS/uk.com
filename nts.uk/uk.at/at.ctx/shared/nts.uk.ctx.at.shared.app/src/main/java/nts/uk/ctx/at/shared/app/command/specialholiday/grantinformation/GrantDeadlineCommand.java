package nts.uk.ctx.at.shared.app.command.specialholiday.grantinformation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.app.command.specialholiday.periodinformation.SpecialVacationDeadlineCommand;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GrantDeadlineCommand {

	/** 期限指定方法 */
	private int timeSpecifyMethod;
	
	/** 有効期限 */
	private SpecialVacationDeadlineCommand expirationDate;
	
	/** 蓄積上限 */
	private LimitAccumulationDaysCommand limitAccumulationDays;
}
