/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.repository.company.organization.jobtitle;

import nts.uk.ctx.basic.dom.common.history.Period;
import nts.uk.ctx.basic.dom.company.organization.jobtitle.CompanyId;
import nts.uk.ctx.basic.dom.company.organization.jobtitle.JobTitleSetMemento;
import nts.uk.ctx.basic.dom.company.organization.jobtitle.PositionCode;
import nts.uk.ctx.basic.dom.company.organization.jobtitle.PositionId;
import nts.uk.ctx.basic.dom.company.organization.jobtitle.PositionName;
import nts.uk.ctx.basic.dom.company.organization.jobtitle.SequenceCode;
import nts.uk.ctx.basic.infra.entity.company.organization.jobtitle.CjtmtJobTitle;
import nts.uk.ctx.basic.infra.entity.company.organization.jobtitle.CjtmtJobTitlePK;

/**
 * The Class JpaJobTitleSetMemento.
 */
public class JpaJobTitleSetMemento implements JobTitleSetMemento {

	/** The type value. */
	CjtmtJobTitle typeValue;

	/**
	 * Instantiates a new jpa job title set memento.
	 *
	 * @param typeValue the type value
	 */
	public JpaJobTitleSetMemento(CjtmtJobTitle typeValue) {
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.basic.dom.company.organization.jobtitle.JobTitleSetMemento#
	 * setPositionId(nts.uk.ctx.basic.dom.company.organization.jobtitle.
	 * PositionId)
	 */
	@Override
	public void setPositionId(PositionId positionId) {
		CjtmtJobTitlePK pk = this.typeValue.getCjmtJobTitlePK();
		pk.setJobId(positionId.v());
		this.typeValue.setCjmtJobTitlePK(pk);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.basic.dom.company.organization.jobtitle.JobTitleSetMemento#
	 * setCompanyId(nts.uk.ctx.basic.dom.company.organization.jobtitle.
	 * CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		CjtmtJobTitlePK pk = new CjtmtJobTitlePK();
		pk.setCompanyId(companyId.v());
		this.typeValue.setCjmtJobTitlePK(pk);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.basic.dom.company.organization.jobtitle.JobTitleSetMemento#
	 * setSequenceCode(nts.uk.ctx.basic.dom.company.organization.jobtitle.
	 * SequenceCode)
	 */
	@Override
	public void setSequenceCode(SequenceCode sequenceCode) {
		this.typeValue.setSequenceCode(sequenceCode.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.basic.dom.company.organization.jobtitle.JobTitleSetMemento#
	 * setPeriod(nts.uk.ctx.basic.dom.common.history.Period)
	 */
	@Override
	public void setPeriod(Period period) {
		this.typeValue.setStartDate(period.getStartDate());
		this.typeValue.setEndDate(period.getEndDate());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.basic.dom.company.organization.jobtitle.JobTitleSetMemento#
	 * setPositionCode(nts.uk.ctx.basic.dom.company.organization.jobtitle.
	 * PositionCode)
	 */
	@Override
	public void setPositionCode(PositionCode positionCode) {
		CjtmtJobTitlePK pk = this.typeValue.getCjmtJobTitlePK();
		pk.setJobCode(positionCode.v());
		this.typeValue.setCjmtJobTitlePK(pk);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.basic.dom.company.organization.jobtitle.JobTitleSetMemento#
	 * setPositionName(nts.uk.ctx.basic.dom.company.organization.jobtitle.
	 * PositionName)
	 */
	@Override
	public void setPositionName(PositionName positionName) {
		this.typeValue.setJobName(positionName.v());
	}

}
