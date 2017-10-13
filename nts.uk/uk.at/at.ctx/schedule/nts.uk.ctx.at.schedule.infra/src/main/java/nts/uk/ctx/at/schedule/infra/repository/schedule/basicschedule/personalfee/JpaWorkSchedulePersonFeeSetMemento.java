/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.schedule.basicschedule.personalfee;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.personalfee.ExtraTimeItemNo;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.personalfee.PersonalFeeAmount;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.personalfee.WorkSchedulePersonFeeSetMemento;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.personalfee.KscmtWsPersonFee;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.personalfee.KscmtWsPersonFeePK;

/**
 * The Class JpaWorkSchedulePersonFeeSetMemento.
 */
public class JpaWorkSchedulePersonFeeSetMemento implements WorkSchedulePersonFeeSetMemento {

	/** The entity. */
	private KscmtWsPersonFee entity;
	
	
	/**
	 * Instantiates a new jpa work schedule person fee set memento.
	 *
	 * @param entity the entity
	 * @param employeeId the employee id
	 * @param baseDate the base date
	 */
	public JpaWorkSchedulePersonFeeSetMemento(KscmtWsPersonFee entity, String employeeId,
			GeneralDate baseDate) {
		if (entity.getKscmtWsPersonFeePK() == null) {
			entity.setKscmtWsPersonFeePK(new KscmtWsPersonFeePK());
		}
		this.entity = entity;
		this.entity.getKscmtWsPersonFeePK().setSid(employeeId);
		this.entity.getKscmtWsPersonFeePK().setYmd(baseDate);
	}

	/**
	 * Sets the no.
	 *
	 * @param no the new no
	 */
	@Override
	public void setNo(ExtraTimeItemNo no) {
		this.entity.getKscmtWsPersonFeePK().setNo(no.value);
	}

	/**
	 * Sets the personal fee amount.
	 *
	 * @param personalFeeAmount the new personal fee amount
	 */
	@Override
	public void setPersonalFeeAmount(PersonalFeeAmount personalFeeAmount) {
		this.entity.setPersonalPeeAmount(personalFeeAmount.v());
	}

}
