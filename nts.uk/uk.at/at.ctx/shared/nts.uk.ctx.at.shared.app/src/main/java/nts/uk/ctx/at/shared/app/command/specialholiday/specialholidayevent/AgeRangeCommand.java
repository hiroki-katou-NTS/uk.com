package nts.uk.ctx.at.shared.app.command.specialholiday.specialholidayevent;

import lombok.Data;

@Data
public class AgeRangeCommand {
	
	/** 年齢下限 */
	private Integer ageLowerLimit;

	/** 年齢上限 */
	private Integer ageHigherLimit;
}
