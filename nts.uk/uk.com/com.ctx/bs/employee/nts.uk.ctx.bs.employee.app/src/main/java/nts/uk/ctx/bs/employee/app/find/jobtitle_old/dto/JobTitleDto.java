/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.find.jobtitle_old.dto;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.common.CompanyId;
import nts.uk.ctx.bs.employee.dom.jobtitle_old.JobTitleSetMemento;
import nts.uk.ctx.bs.employee.dom.jobtitle_old.PositionCode;
import nts.uk.ctx.bs.employee.dom.jobtitle_old.PositionId;
import nts.uk.ctx.bs.employee.dom.jobtitle_old.PositionName;
import nts.uk.ctx.bs.employee.dom.jobtitle_old.SequenceCode;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

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
	public void setPeriod(DatePeriod period) {
		this.startDate = period.start();
		this.endDate = period.end();
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
