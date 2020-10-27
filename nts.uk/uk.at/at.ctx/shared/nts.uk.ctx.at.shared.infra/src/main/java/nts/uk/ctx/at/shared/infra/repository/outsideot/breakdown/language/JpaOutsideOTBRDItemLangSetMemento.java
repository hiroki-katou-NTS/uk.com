/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.outsideot.breakdown.language;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.BreakdownItemName;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.BreakdownItemNo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.language.OutsideOTBRDItemLangSetMemento;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.language.LanguageId;
import nts.uk.ctx.at.shared.infra.entity.outsideot.breakdown.language.KshmtOutsideDetailLang;
import nts.uk.ctx.at.shared.infra.entity.outsideot.breakdown.language.KshmtOutsideDetailLangPK;

/**
 * The Class JpaOutsideOTBRDItemLangSetMemento.
 */
public class JpaOutsideOTBRDItemLangSetMemento implements OutsideOTBRDItemLangSetMemento {
	
	/** The entity. */
	private KshmtOutsideDetailLang entity;
	
	/**
	 * Instantiates a new jpa overtime lang BRD item set memento.
	 *
	 * @param entity the entity
	 */
	public JpaOutsideOTBRDItemLangSetMemento(KshmtOutsideDetailLang entity) {
		if(entity.getKshmtOutsideDetailLangPK() == null){
			entity.setKshmtOutsideDetailLangPK(new KshmtOutsideDetailLangPK());
		}
		this.entity = entity;
	}

	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		this.entity.getKshmtOutsideDetailLangPK().setCid(companyId.v());
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	@Override
	public void setName(BreakdownItemName name) {
		this.entity.setName(name.v());
	}

	/**
	 * Sets the language id.
	 *
	 * @param languageId the new language id
	 */
	@Override
	public void setLanguageId(LanguageId languageId) {
		this.entity.getKshmtOutsideDetailLangPK().setLanguageId(languageId.v());
	}

	/**
	 * Sets the breakdown item no.
	 *
	 * @param breakdownItemNo the new breakdown item no
	 */
	@Override
	public void setBreakdownItemNo(BreakdownItemNo breakdownItemNo) {
		this.entity.getKshmtOutsideDetailLangPK().setBrdItemNo(breakdownItemNo.value);
		
	}

}
