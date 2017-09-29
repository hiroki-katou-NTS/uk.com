/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workrule.closure;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.YearMonth;

/**
 * The Class CurrentMonth.
 */
// 当月
@Getter
public class CurrentMonth extends DomainObject {

	/** The processing date. */
	// 処理年月
	private YearMonth processingYm;

	/**
	 * Instantiates a new current month.
	 *
	 * @param value the value
	 */
	public CurrentMonth(Integer value) {
		this.processingYm = YearMonth.of(value);
	}

}
