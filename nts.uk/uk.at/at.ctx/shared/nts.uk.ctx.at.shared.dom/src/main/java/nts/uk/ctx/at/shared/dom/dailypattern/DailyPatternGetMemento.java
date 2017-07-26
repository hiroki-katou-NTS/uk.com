/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.dailypattern;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Interface DailyPatternGetMemento.
 */
public interface DailyPatternGetMemento {

	
	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	String getCompanyId();
	
	/**
	 * Gets the pattern code.
	 *
	 * @return the pattern code
	 */
	String getPatternCode();
	
	/**
	 * Gets the pattern name.
	 *
	 * @return the pattern name
	 */
	String getPatternName();
	
	/**
	 * Gets the list daily pattern val.
	 *
	 * @return the list daily pattern val
	 */
	List<DailyPatternVal> getListDailyPatternVal();
	
	
	
}
