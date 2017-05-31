/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.repository.company.organization.jobtitle;

import nts.uk.ctx.basic.dom.common.history.Period;
import nts.uk.ctx.basic.dom.company.organization.jobtitle.CompanyId;
import nts.uk.ctx.basic.dom.company.organization.jobtitle.JobTitleGetMemento;
import nts.uk.ctx.basic.dom.company.organization.jobtitle.PositionCode;
import nts.uk.ctx.basic.dom.company.organization.jobtitle.PositionId;
import nts.uk.ctx.basic.dom.company.organization.jobtitle.PositionName;
import nts.uk.ctx.basic.dom.company.organization.jobtitle.SequenceCode;
import nts.uk.ctx.basic.infra.entity.company.organization.jobtitle.CjtmtJobTitle;

/**
 * The Class JpaJobTitleGetMemento.
 */
public class JpaJobTitleGetMemento implements JobTitleGetMemento {

	/** The type value. */
	CjtmtJobTitle typeValue;

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
