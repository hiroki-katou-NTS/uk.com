/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.repository.company.organization.jobtitle;

import nts.uk.ctx.basic.dom.company.organization.jobtitle.CompanyId;
import nts.uk.ctx.basic.dom.company.organization.jobtitle.SequenceCode;
import nts.uk.ctx.basic.dom.company.organization.jobtitle.SequenceMasterSetMemento;
import nts.uk.ctx.basic.dom.company.organization.jobtitle.SequenceName;
import nts.uk.ctx.basic.infra.entity.company.organization.jobtitle.CsqmtSequenceMaster;
import nts.uk.ctx.basic.infra.entity.company.organization.jobtitle.CsqmtSequenceMasterPK;

/**
 * The Class JpaSequenceMasterSetMemento.
 */
public class JpaSequenceMasterSetMemento implements SequenceMasterSetMemento {

	/** The type value. */
	private CsqmtSequenceMaster typeValue;

	/**
	 * Instantiates a new jpa sequence master set memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaSequenceMasterSetMemento(CsqmtSequenceMaster typeValue) {
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.jobtitle.
	 * SequenceMasterSetMemento#setOrder(int)
	 */
	@Override
	public void setOrder(int order) {
		this.typeValue.setOrder(order);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.jobtitle.
	 * SequenceMasterSetMemento#setCompanyId(nts.uk.ctx.basic.dom.company.
	 * organization.jobtitle.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		CsqmtSequenceMasterPK pk = new CsqmtSequenceMasterPK();
		pk.setCompanyId(companyId.v());
		this.typeValue.setCsqmtSequenceMasterPK(pk);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.jobtitle.
	 * SequenceMasterSetMemento#setSequenceCode(nts.uk.ctx.basic.dom.company.
	 * organization.jobtitle.SequenceCode)
	 */
	@Override
	public void setSequenceCode(SequenceCode sequenceCode) {
		CsqmtSequenceMasterPK pk = this.typeValue.getCsqmtSequenceMasterPK();
		pk.setSequenceCode(sequenceCode.v());
		this.typeValue.setCsqmtSequenceMasterPK(pk);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.jobtitle.
	 * SequenceMasterSetMemento#setSequenceName(nts.uk.ctx.basic.dom.company.
	 * organization.jobtitle.SequenceName)
	 */
	@Override
	public void setSequenceName(SequenceName sequenceName) {
		this.typeValue.setSequenceName(sequenceName.v());
	}

}
