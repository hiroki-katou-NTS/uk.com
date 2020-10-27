/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.totaltimes.memento;

import nts.uk.ctx.at.shared.dom.scherec.totaltimes.CountAtr;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.SummaryAtr;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.SummaryList;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalCondition;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesABName;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesName;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.UseAtr;

/**
 * The Interface TotalTimesGetMemento.
 */
public interface TotalTimesGetMemento {

	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	String getCompanyId();

	/**
	 * Gets the total count no.
	 *
	 * @return the total count no
	 */
	Integer getTotalCountNo();

	/**
	 * Gets the count atr.
	 *
	 * @return the count atr
	 */
	CountAtr getCountAtr();

	/**
	 * Gets the use atr.
	 *
	 * @return the use atr
	 */
	UseAtr getUseAtr();

	/**
	 * Gets the total times name.
	 *
	 * @return the total times name
	 */
	TotalTimesName getTotalTimesName();

	/**
	 * Gets the total times AB name.
	 *
	 * @return the total times AB name
	 */
	TotalTimesABName getTotalTimesABName();

	/**
	 * Gets the summary atr.
	 *
	 * @return the summary atr
	 */
	SummaryAtr getSummaryAtr();

	/**
	 * Gets the total condition.
	 *
	 * @return the total condition
	 */
	TotalCondition getTotalCondition();

	/**
	 * Gets the total subjects.
	 *
	 * @return the total subjects
	 */
	SummaryList getSummaryList();

}
