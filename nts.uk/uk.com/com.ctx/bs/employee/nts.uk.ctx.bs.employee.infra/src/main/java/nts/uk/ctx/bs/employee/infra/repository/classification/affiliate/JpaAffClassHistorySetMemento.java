/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.repository.classification.affiliate;

import nts.uk.ctx.bs.employee.dom.classification.ClassificationCode;
import nts.uk.ctx.bs.employee.dom.classification.affiliate.AffClassHistorySetMemento;
import nts.uk.ctx.bs.employee.infra.entity.classification.affiliate.KmnmtAffiliClassificationHist;
import nts.uk.ctx.bs.employee.infra.entity.classification.affiliate.KmnmtAffiliClassificationHistPK;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

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
	public void setPeriod(DatePeriod period) {
		this.classifcationHistory.getKmnmtClassificationHistPK().setStrD(period.start());
		this.classifcationHistory.setEndD(period.end());
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
