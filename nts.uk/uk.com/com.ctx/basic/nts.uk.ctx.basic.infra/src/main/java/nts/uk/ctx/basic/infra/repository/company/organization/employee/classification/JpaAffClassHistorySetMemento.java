/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.repository.company.organization.employee.classification;

import nts.uk.ctx.basic.dom.common.history.Period;
import nts.uk.ctx.basic.dom.company.organization.classification.ClassificationCode;
import nts.uk.ctx.basic.dom.company.organization.employee.classification.AffClassHistorySetMemento;
import nts.uk.ctx.basic.infra.entity.company.organization.employee.classification.KmnmtAffiliClassificationHist;
import nts.uk.ctx.basic.infra.entity.company.organization.employee.classification.KmnmtAffiliClassificationHistPK;

/**
 * The Class JpaAffClassHistorySetMemento.
 */
public class JpaAffClassHistorySetMemento implements AffClassHistorySetMemento {
	
	/** The classifcation history. */
	private KmnmtAffiliClassificationHist classifcationHistory;
	
	
	/**
	 * Instantiates a new jpa aff class history set memento.
	 *
	 * @param classifcationHistory the classifcation history
	 */
	public JpaAffClassHistorySetMemento(KmnmtAffiliClassificationHist classifcationHistory) {
		if (classifcationHistory.getKmnmtClassificationHistPK() == null) {
			classifcationHistory.setKmnmtClassificationHistPK(new KmnmtAffiliClassificationHistPK());
		}
		this.classifcationHistory = classifcationHistory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.employee.classification.
	 * AffClassHistorySetMemento#setClassificationCode(nts.uk.ctx.basic.dom.
	 * company.organization.classification.ClassificationCode)
	 */
	@Override
	public void setClassificationCode(ClassificationCode classificationCode) {
		this.classifcationHistory.getKmnmtClassificationHistPK().setClscd(classificationCode.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.employee.classification.
	 * AffClassHistorySetMemento#setPeriod(nts.uk.ctx.basic.dom.common.history.
	 * Period)
	 */
	@Override
	public void setPeriod(Period period) {
		this.classifcationHistory.getKmnmtClassificationHistPK().setStrD(period.getStartDate());
		this.classifcationHistory.setEndD(period.getEndDate());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.employee.classification.
	 * AffClassHistorySetMemento#setEmployeeId(java.lang.String)
	 */
	@Override
	public void setEmployeeId(String employeeId) {
		this.classifcationHistory.getKmnmtClassificationHistPK().setEmpId(employeeId);
	}

}
