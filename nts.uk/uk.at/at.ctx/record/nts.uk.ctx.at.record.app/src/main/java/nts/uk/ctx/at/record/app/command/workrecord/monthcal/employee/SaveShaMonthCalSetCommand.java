/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.workrecord.monthcal.employee;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.app.command.workrecord.monthcal.common.FlexMonthWorkTimeAggrSetDto;

/**
 * The Class CompanyCalMonthlyFlexCommand.
 */
@Getter
@Setter
public class SaveShaMonthCalSetCommand {

	/** The sid. */
	private String sid;

	/** The aggr setting. */
	private FlexMonthWorkTimeAggrSetDto aggrSetting;

}
