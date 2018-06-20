/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.totaltimes;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Interface TotalTimesSetMemento.
 */
public interface TotalTimesSetMemento {

	/**
	 * Sets the company id.
	 *
	 * @param setCompanyId the new company id
	 */
	void  setCompanyId(CompanyId companyId);

	/**
	 * Sets the total count no.
	 *
	 * @param setTotalCountNo the new total count no
	 */
	void  setTotalCountNo(Integer totalCountNo);

	/**
	 * Sets the count atr.
	 *
	 * @param setCountAtr the new count atr
	 */
	void  setCountAtr(CountAtr countAtr);

	/**
	 * Sets the use atr.
	 *
	 * @param setUseAtr the new use atr
	 */
	void  setUseAtr(UseAtr useAtr);

	/**
	 * Sets the total times name.
	 *
	 * @param setTotalTimesName the new total times name
	 */
	void  setTotalTimesName(TotalTimesName totalTimesName);

	/**
	 * Sets the total times AB name.
	 *
	 * @param setTotalTimesABName the new total times AB name
	 */
	void  setTotalTimesABName(TotalTimesABName totalTimesABName);

	/**
	 * Sets the summary atr.
	 *
	 * @param setSummaryAtr the new summary atr
	 */
	void  setSummaryAtr(SummaryAtr summaryAtr);

	/**
	 * Sets the total condition.
	 *
	 * @param setTotalCondition the new total condition
	 */
	void  setTotalCondition(TotalCondition totalCondition);

	/**
	 * Sets the summary list.
	 *
	 * @param summaryList the new summary list
	 */
	void  setSummaryList(Optional<SummaryList> summaryList);

}
