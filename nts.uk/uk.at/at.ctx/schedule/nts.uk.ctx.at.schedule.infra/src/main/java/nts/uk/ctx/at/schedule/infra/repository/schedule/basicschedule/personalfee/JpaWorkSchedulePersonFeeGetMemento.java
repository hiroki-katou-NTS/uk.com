/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.schedule.basicschedule.personalfee;

import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.personalfee.ExtraTimeItemNo;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.personalfee.PersonalFeeAmount;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.personalfee.WorkSchedulePersonFeeGetMemento;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.personalfee.KscmtWsPersonFee;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.personalfee.KscmtWsPersonFeePK;

/**
 * The Class JpaWorkSchedulePersonFeeGetMemento.
 */
public class JpaWorkSchedulePersonFeeGetMemento implements WorkSchedulePersonFeeGetMemento {

	/** The entity. */
	private KscmtWsPersonFee entity;
	
	
	/**
	 * Instantiates a new jpa work schedule person fee get memento.
	 *
	 * @param entity the entity
	 * @param employeeId the employee id
	 * @param baseDate the base date
	 */
	public JpaWorkSchedulePersonFeeGetMemento(KscmtWsPersonFee entity) {
		if (entity.getKscmtWsPersonFeePK() == null) {
			entity.setKscmtWsPersonFeePK(new KscmtWsPersonFeePK());
		}
		
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.schedule.basicschedule.personalfee.
	 * WorkSchedulePersonFeeGetMemento#getNo()
	 */
	@Override
	public ExtraTimeItemNo getNo() {
		return ExtraTimeItemNo.valueOf(this.entity.getKscmtWsPersonFeePK().getNo());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.schedule.basicschedule.personalfee.
	 * WorkSchedulePersonFeeGetMemento#getPersonalFeeAmount()
	 */
	@Override
	public PersonalFeeAmount getPersonalFeeAmount() {
		return new PersonalFeeAmount(this.entity.getPersonalPeeAmount());
	}

}
