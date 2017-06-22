/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.app.find.company.organization.classification.history.dto;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.basic.dom.common.history.Period;
import nts.uk.ctx.basic.dom.company.organization.classification.ClassificationCode;
import nts.uk.ctx.basic.dom.company.organization.classification.history.ClassificationHistorySetMemento;
import nts.uk.ctx.basic.dom.company.organization.employee.EmployeeId;

/**
 * The Class WorkplaceHistoryDto.
 */

@Getter
@Setter
public class ClassificationHistoryDto implements ClassificationHistorySetMemento{
	
	/** The star date. */
	private GeneralDate starDate;
	
	/** The end date. */
	private GeneralDate endDate;

	/** The employee id. */
	private String employeeId;

	/** The classification code. */
	private String classificationCode;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.classification.history.
	 * ClassificationHistorySetMemento#setClassificationCode(nts.uk.ctx.basic.
	 * dom.company.organization.classification.ClassificationCode)
	 */
	@Override
	public void setClassificationCode(ClassificationCode classificationCode) {
		this.classificationCode = classificationCode.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.classification.history.
	 * ClassificationHistorySetMemento#setPeriod(nts.uk.ctx.basic.dom.common.
	 * history.Period)
	 */
	@Override
	public void setPeriod(Period period) {
		this.starDate = period.getStartDate();
		this.endDate = period.getEndDate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.classification.history.
	 * ClassificationHistorySetMemento#setEmployeeId(nts.uk.ctx.basic.dom.
	 * company.organization.employee.EmployeeId)
	 */
	@Override
	public void setEmployeeId(EmployeeId employeeId) {
		this.employeeId = employeeId.v();
	}


}
