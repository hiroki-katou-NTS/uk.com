/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.repository.classification.affiliate;

import nts.uk.ctx.bs.employee.dom.classification.affiliate.AffClassHistoryGetMemento;
import nts.uk.ctx.bs.employee.dom.classification.affiliate.ClassificationCode;
import nts.uk.ctx.bs.employee.dom.common.history.Period;
import nts.uk.ctx.bs.employee.infra.entity.classification.affiliate.KmnmtAffiliClassificationHist;
import nts.uk.ctx.bs.employee.infra.entity.classification.affiliate.KmnmtAffiliClassificationHistPK;

/**
 * The Class JpaAffClassHistoryGetMemento.
 */
public class JpaAffClassHistoryGetMemento implements AffClassHistoryGetMemento {
	
	/** The classification history. */
	private KmnmtAffiliClassificationHist classificationHistory;
	
	/**
	 * Instantiates a new jpa classification history get memento.
	 *
	 * @param classificationHistory the classification history
	 */
	public JpaAffClassHistoryGetMemento(KmnmtAffiliClassificationHist classificationHistory) {
		if (classificationHistory.getKmnmtClassificationHistPK() == null) {
			classificationHistory.setKmnmtClassificationHistPK(new KmnmtAffiliClassificationHistPK());
		}
		this.classificationHistory = classificationHistory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.classification.history.
	 * ClassificationHistoryGetMemento#getClassificationCode()
	 */
	@Override
	public ClassificationCode getClassificationCode() {
		return new ClassificationCode(
				this.classificationHistory.getKmnmtClassificationHistPK().getClscd());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.classification.history.
	 * ClassificationHistoryGetMemento#getPeriod()
	 */
	@Override
	public Period getPeriod() {
		return new Period(this.classificationHistory.getKmnmtClassificationHistPK().getStrD(),
				this.classificationHistory.getEndD());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.classification.history.
	 * ClassificationHistoryGetMemento#getEmployeeId()
	 */
	@Override
	public String getEmployeeId() {
		return this.classificationHistory.getKmnmtClassificationHistPK().getEmpId();
	}

}
