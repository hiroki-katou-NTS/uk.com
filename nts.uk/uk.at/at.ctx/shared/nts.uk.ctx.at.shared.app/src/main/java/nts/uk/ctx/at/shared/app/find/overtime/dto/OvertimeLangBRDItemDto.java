/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.overtime.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.overtime.breakdown.BreakdownItemName;
import nts.uk.ctx.at.shared.dom.overtime.breakdown.BreakdownItemNo;
import nts.uk.ctx.at.shared.dom.overtime.breakdown.language.OvertimeLangBRDItemSetMemento;
import nts.uk.ctx.at.shared.dom.overtime.language.LanguageId;

/**
 * The Class OvertimeLangBRDItemDto.
 */

@Getter
@Setter
public class OvertimeLangBRDItemDto implements OvertimeLangBRDItemSetMemento {

	/** The name. */
	private String name;

	/** The language id. */
	private String languageId;

	/** The overtime no. */
	private Integer breakdownItemNo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.overtime.breakdown.language.
	 * OvertimeLangBRDItemSetMemento#setCompanyId(nts.uk.ctx.at.shared.dom.
	 * common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		// No thing code

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.overtime.breakdown.language.
	 * OvertimeLangBRDItemSetMemento#setName(nts.uk.ctx.at.shared.dom.overtime.
	 * breakdown.BreakdownItemName)
	 */
	@Override
	public void setName(BreakdownItemName name) {
		this.name = name.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.overtime.breakdown.language.
	 * OvertimeLangBRDItemSetMemento#setLanguageId(nts.uk.ctx.at.shared.dom.
	 * overtime.language.LanguageId)
	 */
	@Override
	public void setLanguageId(LanguageId languageId) {
		this.languageId = languageId.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.overtime.breakdown.language.
	 * OvertimeLangBRDItemSetMemento#setBreakdownItemNo(nts.uk.ctx.at.shared.dom
	 * .overtime.breakdown.BreakdownItemNo)
	 */
	@Override
	public void setBreakdownItemNo(BreakdownItemNo breakdownItemNo) {
		this.breakdownItemNo = breakdownItemNo.value;
	}

	
}
