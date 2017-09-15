/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.overtime.language;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.outsideot.overtime.OvertimeName;
import nts.uk.ctx.at.shared.dom.outsideot.overtime.OvertimeNo;
import nts.uk.ctx.at.shared.dom.outsideot.overtime.language.LanguageId;
import nts.uk.ctx.at.shared.dom.outsideot.overtime.language.OvertimeNameLangGetMemento;
import nts.uk.ctx.at.shared.infra.entity.outside.overtime.language.KshstOverTimeLangName;

/**
 * The Class JpaOvertimeLangNameGetMemento.
 */
public class JpaOvertimeLangNameGetMemento implements OvertimeNameLangGetMemento{

	/** The entity. */
	private KshstOverTimeLangName entity;
	
	/**
	 * Instantiates a new jpa overtime lang name get memento.
	 *
	 * @param entity the entity
	 */
	public JpaOvertimeLangNameGetMemento(KshstOverTimeLangName entity) {
		this.entity = entity;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.language.OvertimeLangNameGetMemento#
	 * getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.entity.getKshstOverTimeLangNamePK().getCid());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.language.OvertimeLangNameGetMemento#
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
	 * nts.uk.ctx.at.shared.dom.overtime.language.OvertimeLangNameGetMemento#
	 * getLanguageId()
	 */
	@Override
	public LanguageId getLanguageId() {
		return new LanguageId(this.entity.getKshstOverTimeLangNamePK().getLanguageId());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.overtime.language.OvertimeLangNameGetMemento#getOvertimeNo()
	 */
	@Override
	public OvertimeNo getOvertimeNo() {
		return OvertimeNo.valueOf(this.entity.getKshstOverTimeLangNamePK().getOverTimeNo());
	}
	

}
