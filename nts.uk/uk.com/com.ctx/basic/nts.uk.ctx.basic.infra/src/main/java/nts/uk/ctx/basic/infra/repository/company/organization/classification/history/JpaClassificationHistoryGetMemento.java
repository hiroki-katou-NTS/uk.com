/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.repository.company.organization.classification.history;

import nts.uk.ctx.basic.dom.common.history.Period;
import nts.uk.ctx.basic.dom.company.organization.classification.ClassificationCode;
import nts.uk.ctx.basic.dom.company.organization.classification.history.ClassificationHistoryGetMemento;
import nts.uk.ctx.basic.dom.company.organization.employee.EmployeeId;
import nts.uk.ctx.basic.infra.entity.company.organization.classification.history.KmnmtClassificationHist;
import nts.uk.ctx.basic.infra.entity.company.organization.classification.history.KmnmtClassificationHistPK;

/**
 * The Class JpaClassificationHistoryGetMemento.
 */
public class JpaClassificationHistoryGetMemento implements ClassificationHistoryGetMemento {
	
	/** The classification history. */
	private KmnmtClassificationHist classificationHistory;
	
	/**
	 * Instantiates a new jpa classification history get memento.
	 *
	 * @param classificationHistory the classification history
	 */
	public JpaClassificationHistoryGetMemento(KmnmtClassificationHist classificationHistory) {
		if (classificationHistory.getKmnmtClassificationHistPK() == null) {
			classificationHistory.setKmnmtClassificationHistPK(new KmnmtClassificationHistPK());
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
		return new Period(this.classificationHistory.getStrD(),
				this.classificationHistory.getEndD());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.classification.history.
	 * ClassificationHistoryGetMemento#getEmployeeId()
	 */
	@Override
	public EmployeeId getEmployeeId() {
		return new EmployeeId(this.classificationHistory.getKmnmtClassificationHistPK().getSid());
	}

}
