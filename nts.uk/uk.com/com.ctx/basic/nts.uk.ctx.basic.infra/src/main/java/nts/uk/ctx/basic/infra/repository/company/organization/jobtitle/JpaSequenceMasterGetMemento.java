/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.repository.company.organization.jobtitle;

import nts.uk.ctx.basic.dom.company.organization.jobtitle.CompanyId;
import nts.uk.ctx.basic.dom.company.organization.jobtitle.SequenceCode;
import nts.uk.ctx.basic.dom.company.organization.jobtitle.SequenceMasterGetMemento;
import nts.uk.ctx.basic.dom.company.organization.jobtitle.SequenceName;
import nts.uk.ctx.basic.infra.entity.company.organization.jobtitle.CsqmtSequenceMaster;

/**
 * The Class JpaSequenceMasterGetMemento.
 */
public class JpaSequenceMasterGetMemento implements SequenceMasterGetMemento {

	/** The type value. */
	private CsqmtSequenceMaster typeValue;

	/**
	 * Instantiates a new jpa sequence master get memento.
	 *
	 * @param typeValue the type value
	 */
	public JpaSequenceMasterGetMemento(CsqmtSequenceMaster typeValue) {
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.jobtitle.
	 * SequenceMasterGetMemento#getOrder()
	 */
	@Override
	public int getOrder() {
		return this.typeValue.getOrder();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.jobtitle.
	 * SequenceMasterGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.typeValue.getCsqmtSequenceMasterPK().getCompanyId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.jobtitle.
	 * SequenceMasterGetMemento#getSequenceCode()
	 */
	@Override
	public SequenceCode getSequenceCode() {
		return new SequenceCode(this.typeValue.getCsqmtSequenceMasterPK().getSequenceCode());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.jobtitle.
	 * SequenceMasterGetMemento#getSequenceName()
	 */
	@Override
	public SequenceName getSequenceName() {
		return new SequenceName(this.typeValue.getSequenceName());
	}

}
