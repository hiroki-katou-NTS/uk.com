/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.workrecord.monthcal.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * The Class FlexMonthWorkTimeAggrSetDto.
 */
@Getter
@Setter
@Builder
public class FlexMonthWorkTimeAggrSetDto {

	/** The aggr method. */
	private Integer aggrMethod;

	/** The insuffic set. */
	private int insufficSet;
	/** 清算期間 */
	private int settlePeriod;
	/** 開始月 */
	private int startMonth;
	/** 期間 */
	private int period;

	/** The legal aggr set. */
	private Integer legalAggrSet;

	/** The include over time. */
	private Integer includeOverTime;

	/** The include illegal holiday work. */
	private Integer includeIllegalHdwk;

}
