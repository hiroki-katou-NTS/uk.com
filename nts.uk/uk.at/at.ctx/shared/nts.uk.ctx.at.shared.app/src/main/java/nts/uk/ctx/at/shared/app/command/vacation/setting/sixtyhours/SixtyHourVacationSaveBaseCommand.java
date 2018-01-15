/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.vacation.setting.sixtyhours;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class SubstVacationDto.
 */
@Getter
@Setter
public class SixtyHourVacationSaveBaseCommand {

	/** The is manage. */
	private Integer isManage;

	/** The expiration date. */
	private Integer sixtyHourExtra;

	/** The allow prepaid leave. */
	private Integer digestiveUnit;

}
