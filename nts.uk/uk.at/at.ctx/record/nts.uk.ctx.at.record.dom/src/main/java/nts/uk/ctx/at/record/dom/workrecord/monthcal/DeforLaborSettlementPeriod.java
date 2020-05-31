/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthcal;

import java.io.Serializable;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.Month;

/**
 * The Class DeforLaborSettlementPeriod.
 */
@Getter
// 変形労働の精算期間
public class DeforLaborSettlementPeriod implements Serializable{

	/** Serializable */
	private static final long serialVersionUID = 1L;

	/** The start month. */
	private Month startMonth;

	/** The period. */
	private Month period;

	/** The repeat atr. */
	private Boolean repeatAtr;

	/**
	 * Instantiates a new defor labor settlement period.
	 *
	 * @param startMonth
	 *            the start month
	 * @param period
	 *            the period
	 * @param repeatAtr
	 *            the repeat atr
	 */
	public DeforLaborSettlementPeriod(Month startMonth, Month period, Boolean repeatAtr) {
		super();
		this.startMonth = startMonth;
		this.period = period;
		this.repeatAtr = repeatAtr;
	}

}
