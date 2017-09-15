/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.outsideot.overtime.language;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.outsideot.overtime.OvertimeName;
import nts.uk.ctx.at.shared.dom.outsideot.overtime.OvertimeNo;
import nts.uk.ctx.at.shared.dom.outsideot.overtime.language.LanguageId;
import nts.uk.ctx.at.shared.dom.outsideot.overtime.language.OvertimeNameLangSetMemento;
import nts.uk.ctx.at.shared.infra.entity.outside.overtime.language.KshstOverTimeLangName;
import nts.uk.ctx.at.shared.infra.entity.outside.overtime.language.KshstOverTimeLangNamePK;

/**
 * The Class JpaOvertimeLangNameSetMemento.
 */
public class JpaOvertimeLangNameSetMemento implements OvertimeNameLangSetMemento {
	
	/** The entity. */
	private KshstOverTimeLangName entity;
	
	/**
	 * Instantiates a new jpa overtime lang name set memento.
	 *
	 * @param entity the entity
	 */
	public JpaOvertimeLangNameSetMemento(KshstOverTimeLangName entity) {
		if(entity.getKshstOverTimeLangNamePK() == null){
			entity.setKshstOverTimeLangNamePK(new KshstOverTimeLangNamePK());
		}
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.language.OvertimeLangNameSetMemento#
	 * setCompanyId(nts.uk.ctx.at.shared.dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		this.entity.getKshstOverTimeLangNamePK().setCid(companyId.v());
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
		this.entity.setName(name.v());
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
		this.entity.getKshstOverTimeLangNamePK().setLanguageId(languageId.v());
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
		this.entity.getKshstOverTimeLangNamePK().setOverTimeNo(overtimeNo.value);
	}
	
}
