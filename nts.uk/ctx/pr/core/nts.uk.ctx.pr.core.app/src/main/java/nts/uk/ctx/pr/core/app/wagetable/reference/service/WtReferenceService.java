/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.wagetable.reference.service;

import java.math.BigDecimal;
import java.util.List;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.app.wagetable.reference.service.dto.EleHistItemDto;
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
	 * @param companyCode the company code
	 * @param type the type
	 * @param refNo the ref no
	 * @param codeItems the code items
	 * @return the list
	 */
	List<EleHistItemDto> generateCodeItems(CompanyCode companyCode, ElementType type, WtElementRefNo refNo, List<CodeItem> codeItems);

	/**
	 * Generate range items.
	 *
	 * @param lowerLimit the lower limit
	 * @param upperLimit the upper limit
	 * @param interval the interval
	 * @param rangeItems the range items
	 * @return the list
	 */
	List<EleHistItemDto> generateRangeItems(BigDecimal lowerLimit, BigDecimal upperLimit, BigDecimal interval, List<RangeItem> rangeItems);

}
