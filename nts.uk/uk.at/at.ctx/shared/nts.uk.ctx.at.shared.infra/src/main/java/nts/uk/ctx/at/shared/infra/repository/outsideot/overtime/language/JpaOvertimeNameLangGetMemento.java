/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.outsideot.overtime.language;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.OvertimeName;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.OvertimeNo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.language.LanguageId;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.language.OvertimeNameLangGetMemento;
import nts.uk.ctx.at.shared.infra.entity.outside.overtime.language.KshstOverTimeNameLang;

/**
 * The Class JpaOvertimeNameLangGetMemento.
 */
public class JpaOvertimeNameLangGetMemento implements OvertimeNameLangGetMemento{

	/** The entity. */
	private KshstOverTimeNameLang entity;
	
	/**
	 * Instantiates a new jpa overtime name lang get memento.
	 *
	 * @param entity the entity
	 */
	public JpaOvertimeNameLangGetMemento(KshstOverTimeNameLang entity) {
		this.entity = entity;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.language.OvertimeNameLangGetMemento#
	 * getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.entity.getKshstOverTimeNameLangPK().getCid());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.language.OvertimeNameLangGetMemento#
	 * getName()
	 */
	@Override
	public OvertimeName getName() {
		return new OvertimeName(this.entity.getName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.language.OvertimeNameLangGetMemento#
	 * getLanguageId()
	 */
	@Override
	public LanguageId getLanguageId() {
		return new LanguageId(this.entity.getKshstOverTimeNameLangPK().getLanguageId());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.overtime.language.OvertimeNameLangGetMemento#getOvertimeNo()
	 */
	@Override
	public OvertimeNo getOvertimeNo() {
		return OvertimeNo.valueOf(this.entity.getKshstOverTimeNameLangPK().getOverTimeNo());
	}
	

}
