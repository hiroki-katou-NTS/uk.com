/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.workrecord.monthcal.common;

import lombok.Getter;

/**
 * The Class AggrSettingMonthlyOfFlxNew.
 */
@Getter
public class FlexMonthWorkTimeAggrSetDto {

	/** The aggregate method. */
	private Integer aggrMethod;

	/** The shortage set. */
	private Integer insufficSet;

	/** The legal aggregate set. */
	private Integer legalAggrSet;

	/** The include over time. */
	private Integer includeOverTime;

}
