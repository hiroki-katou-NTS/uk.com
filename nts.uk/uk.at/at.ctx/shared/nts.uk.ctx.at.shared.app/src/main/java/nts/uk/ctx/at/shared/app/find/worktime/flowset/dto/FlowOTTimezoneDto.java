/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.flowset.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class FlowOTTimezoneDto.
 */
@Getter
@Setter
public class FlowOTTimezoneDto extends DomainObject {

	/** The worktime no. */
	private Integer worktimeNo;

	/** The restrict time. */
	private Boolean restrictTime;

	/** The OT frame no. */
	private BigDecimal OTFrameNo;

	/** The flow time setting. */
	private FlowTimeSettingDto flowTimeSetting;

	/** The in legal OT frame no. */
	private BigDecimal inLegalOTFrameNo;

	/** The settlement order. */
	private Integer settlementOrder;
}
