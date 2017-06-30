/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.repository.company.organization.employee.classification;

import nts.uk.ctx.basic.dom.common.history.Period;
import nts.uk.ctx.basic.dom.company.organization.classification.ClassificationCode;
import nts.uk.ctx.basic.dom.company.organization.employee.EmployeeId;
import nts.uk.ctx.basic.dom.company.organization.employee.classification.AffiliationClassificationHistorySetMemento;
import nts.uk.ctx.basic.infra.entity.company.organization.employee.classification.KmnmtAffiliClassificationHist;
import nts.uk.ctx.basic.infra.entity.company.organization.employee.classification.KmnmtAffiliClassificationHistPK;

/**
 * The Class JpaAffiliationClassificationHistorySetMemento.
 */
public class JpaAffiliationClassificationHistorySetMemento
		implements AffiliationClassificationHistorySetMemento {
	
	/** The classifcation history. */
	private KmnmtAffiliClassificationHist classifcationHistory;
	
	
	/**
	 * Instantiates a new jpa classification history set memento.
	 *
	 * @param classifcationHistory the classifcation history
	 */
	public JpaAffiliationClassificationHistorySetMemento(KmnmtAffiliClassificationHist classifcationHistory) {
		if (classifcationHistory.getKmnmtClassificationHistPK() == null) {
			classifcationHistory.setKmnmtClassificationHistPK(new KmnmtAffiliClassificationHistPK());
		}
		this.classifcationHistory = classifcationHistory;
	}

	/**
	 * Sets the classification code.
	 *
	 * @param classificationCode the new classification code
	 */
	@Override
	public void setClassificationCode(ClassificationCode classificationCode) {
		this.classifcationHistory.getKmnmtClassificationHistPK().setClscd(classificationCode.v());
	}

	/**
	 * Sets the period.
	 *
	 * @param period the new period
	 */
	@Override
	public void setPeriod(Period period) {
		this.classifcationHistory.setStrD(period.getStartDate());
		this.classifcationHistory.setEndD(period.getEndDate());
	}

	/**
	 * Sets the employee id.
	 *
	 * @param employeeId the new employee id
	 */
	@Override
	public void setEmployeeId(EmployeeId employeeId) {
		this.classifcationHistory.getKmnmtClassificationHistPK().setEmpId(employeeId.v());
	}

}
