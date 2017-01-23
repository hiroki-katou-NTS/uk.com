/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.YearMonth;

/**
 * The Class MonthRange.
 */
@Getter
@Setter
public class MonthRange implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4492995458816732355L;

	/** The start month. */
	public YearMonth startMonth;

	/** The end month. */
	public YearMonth endMonth;
	

}
