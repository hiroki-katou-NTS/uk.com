/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.repository.jobtitle_old;

import nts.uk.ctx.bs.employee.dom.common.CompanyId;
import nts.uk.ctx.bs.employee.dom.jobtitle_old.JobTitleSetMemento;
import nts.uk.ctx.bs.employee.dom.jobtitle_old.PositionCode;
import nts.uk.ctx.bs.employee.dom.jobtitle_old.PositionId;
import nts.uk.ctx.bs.employee.dom.jobtitle_old.PositionName;
import nts.uk.ctx.bs.employee.dom.jobtitle_old.SequenceCode;
import nts.uk.ctx.bs.employee.infra.entity.jobtitle_old.CjtmtJobTitle;
import nts.uk.ctx.bs.employee.infra.entity.jobtitle_old.CjtmtJobTitlePK;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class JpaJobTitleSetMemento.
 */
public class JpaJobTitleSetMemento implements JobTitleSetMemento {

	/** The type value. */
	private CjtmtJobTitle typeValue;

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
		CjtmtJobTitlePK pk = this.typeValue.getCjtmtJobTitlePK();
		pk.setJobId(positionId.v());
		this.typeValue.setCjtmtJobTitlePK(pk);
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
		this.typeValue.setCjtmtJobTitlePK(pk);
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
	public void setPeriod(DatePeriod period) {
		this.typeValue.setStartDate(period.start());
		this.typeValue.setEndDate(period.end());
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
		CjtmtJobTitlePK pk = this.typeValue.getCjtmtJobTitlePK();
		pk.setJobCode(positionCode.v());
		this.typeValue.setCjtmtJobTitlePK(pk);
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
