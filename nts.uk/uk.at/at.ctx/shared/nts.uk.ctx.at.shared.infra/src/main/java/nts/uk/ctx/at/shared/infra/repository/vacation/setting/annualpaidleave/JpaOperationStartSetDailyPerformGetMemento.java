/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.annualpaidleave;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.OperationStartSetDailyPerformGetMemento;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.annualpaidleave.KshstStartSetDailyPfm;

/**
 * The Class JpaOperationStartSetDailyPerformGetMemento.
 */
public class JpaOperationStartSetDailyPerformGetMemento implements OperationStartSetDailyPerformGetMemento{

	/** The entity. */
	private KshstStartSetDailyPfm entity;
	
	/**
	 * Instantiates a new jpa operation start set daily perform get memento.
	 *
	 * @param entity the entity
	 */
	public JpaOperationStartSetDailyPerformGetMemento(KshstStartSetDailyPfm entity) {
		this.entity = entity;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.OperationStartSetDailyPerformGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.entity.getCid());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.OperationStartSetDailyPerformGetMemento#getOperateStartDateDailyPerform()
	 */
	@Override
	public Optional<GeneralDate> getOperateStartDateDailyPerform() {
		return Optional.ofNullable(this.entity.getStartDateDailyPerform());
	}

}

