/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.outsideot.breakdown.language;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.BreakdownItemName;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.BreakdownItemNo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.language.OutsideOTBRDItemLangGetMemento;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.language.LanguageId;
import nts.uk.ctx.at.shared.infra.entity.outsideot.breakdown.language.KshmtOutsideDetailLang;

/**
 * The Class JpaOutsideOTBRDItemLangGetMemento.
 */
public class JpaOutsideOTBRDItemLangGetMemento implements OutsideOTBRDItemLangGetMemento {

	
	/** The entity. */
	private KshmtOutsideDetailLang entity;
	
	/**
	 * Instantiates a new jpa overtime lang BRD item get memento.
	 *
	 * @param entity the entity
	 */
	public JpaOutsideOTBRDItemLangGetMemento(KshmtOutsideDetailLang entity) {
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
		return new CompanyId(this.entity.getKshmtOutsideDetailLangPK().getCid());
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
		return new LanguageId(this.entity.getKshmtOutsideDetailLangPK().getLanguageId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.overtime.breakdown.language.
	 * OvertimeLangBRDItemGetMemento#getBreakdownItemNo()
	 */
	@Override
	public BreakdownItemNo getBreakdownItemNo() {
		return BreakdownItemNo.valueOf(this.entity.getKshmtOutsideDetailLangPK().getBrdItemNo());
	}

}
