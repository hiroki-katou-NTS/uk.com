/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.annualpaidleave;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.OperationStartSetDailyPerformSetMemento;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.annualpaidleave.KshstStartSetDailyPfm;

/**
 * The Class JpaOperationStartSetDailyPerformSetMemento.
 */
public class JpaOperationStartSetDailyPerformSetMemento implements OperationStartSetDailyPerformSetMemento{

	/** The entity. */
	private KshstStartSetDailyPfm entity;
	
	/**
	 * Instantiates a new jpa operation start set daily perform set memento.
	 *
	 * @param entity the entity
	 */
	public JpaOperationStartSetDailyPerformSetMemento(KshstStartSetDailyPfm entity) {
		this.entity = entity;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.OperationStartSetDailyPerformSetMemento#setCompanyId(nts.uk.ctx.at.shared.dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		this.entity.setCid(companyId.v());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.OperationStartSetDailyPerformSetMemento#setOperateStartDateDailyPerform(java.util.Optional)
	 */
	@Override
	public void setOperateStartDateDailyPerform(Optional<GeneralDate> operateStartDateDailyPerform) {
		if (operateStartDateDailyPerform.isPresent()) {
			this.entity.setStartDateDailyPerform(operateStartDateDailyPerform.get());
		} else {
			this.entity.setStartDateDailyPerform(null);
		}
		
	}

}

