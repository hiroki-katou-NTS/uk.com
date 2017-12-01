/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.flowset.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class FlowWorkHolidayTimeZoneDto.
 */
@Getter
@Setter
public class FlowWorkHolidayTimeZoneDto {

	/** The worktime no. */
	private Integer worktimeNo;

	/** The use in legal break restrict time. */
	private boolean useInLegalBreakRestrictTime;

	/** The in legal break frame no. */
	private BigDecimal inLegalBreakFrameNo;

	/** The use out legal break restrict time. */
	private boolean useOutLegalBreakRestrictTime;

	/** The out legal break frame no. */
	private BigDecimal outLegalBreakFrameNo;

	/** The use out legal pub hol restrict time. */
	private boolean useOutLegalPubHolRestrictTime;

	/** The out legal pub hol frame no. */
	private BigDecimal outLegalPubHolFrameNo;

	/** The flow time setting. */
	private FlowTimeSettingDto flowTimeSetting;
}
