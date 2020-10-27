/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.repository.jobtitle;

import nts.uk.ctx.bs.employee.dom.common.CompanyId;
import nts.uk.ctx.bs.employee.dom.jobtitle.sequence.SequenceCode;
import nts.uk.ctx.bs.employee.dom.jobtitle.sequence.SequenceMasterSetMemento;
import nts.uk.ctx.bs.employee.dom.jobtitle.sequence.SequenceName;
import nts.uk.ctx.bs.employee.infra.entity.jobtitle.BsymtJobRank;
import nts.uk.ctx.bs.employee.infra.entity.jobtitle.BsymtJobRankPK;

/**
 * The Class JpaSequenceMasterSetMemento.
 */
public class JpaSequenceMasterSetMemento implements SequenceMasterSetMemento {

	/** The type value. */
	private BsymtJobRank typeValue;

	/**
	 * Instantiates a new jpa sequence master set memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaSequenceMasterSetMemento(BsymtJobRank typeValue) {
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
		this.typeValue.setDisporder(order);
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
		BsymtJobRankPK pk = this.typeValue.getBsymtJobRankPK();
		if (pk == null) {
			pk = new BsymtJobRankPK();
		}
		pk.setCid(companyId.v());
		this.typeValue.setBsymtJobRankPK(pk);
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
		BsymtJobRankPK pk = this.typeValue.getBsymtJobRankPK();
		if (pk == null) {
			pk = new BsymtJobRankPK();
		}
		pk.setSeqCd(sequenceCode.v());
		this.typeValue.setBsymtJobRankPK(pk);
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
		this.typeValue.setSeqName(sequenceName.v());
	}

}
