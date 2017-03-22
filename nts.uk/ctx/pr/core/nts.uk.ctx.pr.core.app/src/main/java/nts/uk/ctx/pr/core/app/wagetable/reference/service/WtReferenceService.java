/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.wagetable.reference.service;

import java.math.BigDecimal;
import java.util.List;

import nts.uk.ctx.pr.core.dom.wagetable.ElementType;
import nts.uk.ctx.pr.core.dom.wagetable.WtElementRefNo;
import nts.uk.ctx.pr.core.dom.wagetable.history.element.item.CodeItem;
import nts.uk.ctx.pr.core.dom.wagetable.history.element.item.RangeItem;

/**
 * The Interface WtReferenceService.
 */
public interface WtReferenceService {

	/**
	 * Generate code items.
	 *
	 * @param type the type
	 * @param refNo the ref no
	 * @return the list
	 */
	List<CodeItem> generateCodeItems(ElementType type, WtElementRefNo refNo);

	/**
	 * Generate range items.
	 *
	 * @param lowerLimit the lower limit
	 * @param upperLimit the upper limit
	 * @param interval the interval
	 * @return the list
	 */
	List<RangeItem> generateRangeItems(BigDecimal lowerLimit, BigDecimal upperLimit, BigDecimal interval);

}
