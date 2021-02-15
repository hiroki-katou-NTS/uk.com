/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.outsideot.overtime.language;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.OvertimeName;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.OvertimeNo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.language.LanguageId;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.language.OvertimeNameLangSetMemento;
import nts.uk.ctx.at.shared.infra.entity.outside.overtime.language.KshstOverTimeNameLang;
import nts.uk.ctx.at.shared.infra.entity.outside.overtime.language.KshstOverTimeNameLangPK;

/**
 * The Class JpaOvertimeNameLangSetMemento.
 */
public class JpaOvertimeNameLangSetMemento implements OvertimeNameLangSetMemento {
	
	/** The entity. */
	private KshstOverTimeNameLang entity;
	
	/**
	 * Instantiates a new jpa overtime lang name set memento.
	 *
	 * @param entity the entity
	 */
	public JpaOvertimeNameLangSetMemento(KshstOverTimeNameLang entity) {
		if(entity.getKshstOverTimeNameLangPK() == null){
			entity.setKshstOverTimeNameLangPK(new KshstOverTimeNameLangPK());
		}
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.language.OvertimeNameLangSetMemento#
	 * setCompanyId(nts.uk.ctx.at.shared.dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		this.entity.getKshstOverTimeNameLangPK().setCid(companyId.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.language.OvertimeNameLangSetMemento#
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
	 * nts.uk.ctx.at.shared.dom.overtime.language.OvertimeNameLangSetMemento#
	 * setLanguageId(nts.uk.ctx.at.shared.dom.overtime.language.LanguageId)
	 */
	@Override
	public void setLanguageId(LanguageId languageId) {
		this.entity.getKshstOverTimeNameLangPK().setLanguageId(languageId.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.language.OvertimeNameLangSetMemento#
	 * setOvertimeNo(nts.uk.ctx.at.shared.dom.overtime.OvertimeNo)
	 */
	@Override
	public void setOvertimeNo(OvertimeNo overtimeNo) {
		this.entity.getKshstOverTimeNameLangPK().setOverTimeNo(overtimeNo.value);
	}
	
}
