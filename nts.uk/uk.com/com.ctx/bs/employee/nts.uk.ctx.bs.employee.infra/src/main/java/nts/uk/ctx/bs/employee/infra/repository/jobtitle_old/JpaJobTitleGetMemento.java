/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.repository.jobtitle_old;

import nts.uk.ctx.bs.employee.dom.common.CompanyId;
import nts.uk.ctx.bs.employee.dom.common.history.Period;
import nts.uk.ctx.bs.employee.dom.jobtitle_old.JobTitleGetMemento;
import nts.uk.ctx.bs.employee.dom.jobtitle_old.PositionCode;
import nts.uk.ctx.bs.employee.dom.jobtitle_old.PositionId;
import nts.uk.ctx.bs.employee.dom.jobtitle_old.PositionName;
import nts.uk.ctx.bs.employee.dom.jobtitle_old.SequenceCode;
import nts.uk.ctx.bs.employee.infra.entity.jobtitle_old.CjtmtJobTitle;

/**
 * The Class JpaJobTitleGetMemento.
 */
public class JpaJobTitleGetMemento implements JobTitleGetMemento {

	/** The type value. */
	private CjtmtJobTitle typeValue;

	/**
	 * Instantiates a new jpa job title get memento.
	 *
	 * @param typeValue the type value
	 */
	public JpaJobTitleGetMemento(CjtmtJobTitle typeValue) {
		this.typeValue = typeValue;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.basic.dom.company.organization.jobtitle.JobTitleGetMemento#getPositionId()
	 */
	@Override
	public PositionId getPositionId() {
		return new PositionId(this.typeValue.getCjtmtJobTitlePK().getJobId());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.basic.dom.company.organization.jobtitle.JobTitleGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.typeValue.getCjtmtJobTitlePK().getCompanyId());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.basic.dom.company.organization.jobtitle.JobTitleGetMemento#getSequenceCode()
	 */
	@Override
	public SequenceCode getSequenceCode() {
		return new SequenceCode(this.typeValue.getSequenceCode());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.basic.dom.company.organization.jobtitle.JobTitleGetMemento#getPeriod()
	 */
	@Override
	public Period getPeriod() {
		return new Period(this.typeValue.getStartDate(), this.typeValue.getEndDate());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.basic.dom.company.organization.jobtitle.JobTitleGetMemento#getPositionCode()
	 */
	@Override
	public PositionCode getPositionCode() {
		return new PositionCode(this.typeValue.getCjtmtJobTitlePK().getJobCode());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.basic.dom.company.organization.jobtitle.JobTitleGetMemento#getPositionName()
	 */
	@Override
	public PositionName getPositionName() {
		return new PositionName(this.typeValue.getJobName());
	}

}
