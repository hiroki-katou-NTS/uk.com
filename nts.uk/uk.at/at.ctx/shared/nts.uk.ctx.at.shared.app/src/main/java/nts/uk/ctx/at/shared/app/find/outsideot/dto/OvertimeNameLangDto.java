/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.outsideot.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.OvertimeName;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.OvertimeNo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.language.LanguageId;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.language.OvertimeNameLangSetMemento;

/**
 * The Class OvertimeNameLangDto.
 */
@Getter
@Setter
public class OvertimeNameLangDto implements OvertimeNameLangSetMemento {

	/** The name. */
	private String name;

	/** The language id. */
	private String languageId;

	/** The overtime no. */
	private Integer overtimeNo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.language.OvertimeLangNameSetMemento#
	 * setCompanyId(nts.uk.ctx.at.shared.dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		// No thing code
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.language.OvertimeLangNameSetMemento#
	 * setName(nts.uk.ctx.at.shared.dom.overtime.OvertimeName)
	 */
	@Override
	public void setName(OvertimeName name) {
		this.name = name.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.language.OvertimeLangNameSetMemento#
	 * setLanguageId(nts.uk.ctx.at.shared.dom.overtime.language.LanguageId)
	 */
	@Override
	public void setLanguageId(LanguageId languageId) {
		this.languageId = languageId.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.language.OvertimeLangNameSetMemento#
	 * setOvertimeNo(nts.uk.ctx.at.shared.dom.overtime.OvertimeNo)
	 */
	@Override
	public void setOvertimeNo(OvertimeNo overtimeNo) {
		this.overtimeNo = overtimeNo.value;
	}

}
