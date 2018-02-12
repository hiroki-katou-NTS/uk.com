/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.pattern.monthly.setting;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class MonthlyPatternSettingFindAllDto.
 */
@Getter
@Setter
public class MonthlyPatternSettingFindAllDto {

	/** The employee ids. */
	private List<String> employeeIds;
	
	/** The monthly pattern codes. */
	private List<String> monthlyPatternCodes;
}
