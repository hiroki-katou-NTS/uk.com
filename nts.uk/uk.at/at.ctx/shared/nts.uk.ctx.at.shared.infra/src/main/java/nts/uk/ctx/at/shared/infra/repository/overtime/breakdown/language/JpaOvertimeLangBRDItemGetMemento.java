/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.overtime.breakdown.language;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.outsideot.breakdown.BreakdownItemName;
import nts.uk.ctx.at.shared.dom.outsideot.breakdown.BreakdownItemNo;
import nts.uk.ctx.at.shared.dom.outsideot.breakdown.language.OutsideOTBRDItemLangGetMemento;
import nts.uk.ctx.at.shared.dom.outsideot.overtime.language.LanguageId;
import nts.uk.ctx.at.shared.infra.entity.outsideot.breakdown.language.KshstOverTimeLangBrd;

/**
 * The Class JpaOvertimeLangBRDItemGetMemento.
 */
public class JpaOvertimeLangBRDItemGetMemento implements OutsideOTBRDItemLangGetMemento {

	
	/** The entity. */
	private KshstOverTimeLangBrd entity;
	
	/**
	 * Instantiates a new jpa overtime lang BRD item get memento.
	 *
	 * @param entity the entity
	 */
	public JpaOvertimeLangBRDItemGetMemento(KshstOverTimeLangBrd entity) {
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.overtime.breakdown.language.
	 * OvertimeLangBRDItemGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.entity.getKshstOverTimeLangBrdPK().getCid());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.overtime.breakdown.language.
	 * OvertimeLangBRDItemGetMemento#getName()
	 */
	@Override
	public BreakdownItemName getName() {
		return new BreakdownItemName(this.entity.getName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.overtime.breakdown.language.
	 * OvertimeLangBRDItemGetMemento#getLanguageId()
	 */
	@Override
	public LanguageId getLanguageId() {
		return new LanguageId(this.entity.getKshstOverTimeLangBrdPK().getLanguageId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.overtime.breakdown.language.
	 * OvertimeLangBRDItemGetMemento#getBreakdownItemNo()
	 */
	@Override
	public BreakdownItemNo getBreakdownItemNo() {
		return BreakdownItemNo.valueOf(this.entity.getKshstOverTimeLangBrdPK().getBrdItemNo());
	}

}
