/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.dom.statement;

import java.util.List;

import nts.uk.ctx.at.function.dom.statement.dtoimport.WkpHistWithPeriodImport;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Interface WkpHistWithPeriodAdapter.
 */
public interface WkpHistWithPeriodAdapter {
	
	/**
	 * Gets the lst hist by wkps and period.
	 *
	 * @param wkpIds the wkp ids
	 * @param period the period
	 * @return the lst hist by wkps and period
	 */
	public List<WkpHistWithPeriodImport> getLstHistByWkpsAndPeriod(List<String> wkpIds, DatePeriod period); 
}
