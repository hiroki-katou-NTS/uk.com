/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.app.find.company.organization.jobtitle.dto;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.basic.dom.common.history.Period;
import nts.uk.ctx.basic.dom.company.organization.jobtitle.CompanyId;
import nts.uk.ctx.basic.dom.company.organization.jobtitle.JobTitleSetMemento;
import nts.uk.ctx.basic.dom.company.organization.jobtitle.PositionCode;
import nts.uk.ctx.basic.dom.company.organization.jobtitle.PositionId;
import nts.uk.ctx.basic.dom.company.organization.jobtitle.PositionName;
import nts.uk.ctx.basic.dom.company.organization.jobtitle.SequenceCode;

/**
 * Instantiates a new job title dto.
 */
@Data
public class JobTitleDto implements JobTitleSetMemento {

	/** The code. */
	private String code;

	/** The name. */
	private String name;

	/** The sequence code. */
	private String sequenceCode;

	/** The id. */
	private String id;

	/** The start date. */
	private GeneralDate startDate;

	/** The end date. */
	private GeneralDate endDate;

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
		this.id = positionId.v();
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
		// TODO Auto-generated method stub

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
		this.sequenceCode = sequenceCode.v();
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
		this.startDate = period.getStartDate();
		this.endDate = period.getEndDate();
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
		this.code = positionCode.v();
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
		this.name = positionName.v();
	}
}
