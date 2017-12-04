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
 * The Class StampReflectTimezone.
 */
@Getter
@Setter
public class StampReflectTimezoneDto extends DomainObject {

	/** The work no. */
	private BigDecimal workNo;

	/** The classification. */
	private Integer classification;

	/** The end time. */
	private Integer endTime;

	/** The start time. */
	private Integer startTime;
}
