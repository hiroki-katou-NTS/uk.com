/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.pattern.daily;

import java.util.List;

import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Interface DailyPatternGetMemento.
 */
public interface DailyPatternGetMemento {

	
	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	CompanyId getCompanyId();
	
	/**
	 * Gets the pattern code.
	 *
	 * @return the pattern code
	 */
	PatternCode getPatternCode();
	
	/**
	 * Gets the pattern name.
	 *
	 * @return the pattern name
	 */
	PatternName getPatternName();
	
	/**
	 * Gets the list daily pattern val.
	 *
	 * @return the list daily pattern val
	 */
	List<DailyPatternVal> getListDailyPatternVal();
	
}
