/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.outsideot.overtime;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.UseClassification;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.OvertimeGetMemento;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.OvertimeName;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.OvertimeNo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.OvertimeValue;
import nts.uk.ctx.at.shared.infra.entity.outsideot.overtime.KshstOverTime;
import nts.uk.ctx.at.shared.infra.entity.outsideot.overtime.KshstOverTimePK;

/**
 * The Class JpaOvertimeGetMemento.
 */
public class JpaOvertimeGetMemento implements OvertimeGetMemento{
	
	/** The Constant BOOLEAN_TRUE. */
	public static final int BOOLEAN_TRUE = 1;
	
	/** The entity. */
	private KshstOverTime entity;
	
	/**
	 * Instantiates a new jpa overtime get memento.
	 *
	 * @param entity the entity
	 */
	public JpaOvertimeGetMemento(KshstOverTime entity) {
		if (entity.getKshstOverTimePK() == null) {
			entity.setKshstOverTimePK(new KshstOverTimePK());
		}
		this.entity = entity;
	}

	/**
	 * Gets the super holiday 60 H occurs.
	 *
	 * @return the super holiday 60 H occurs
	 */
	@Override
	public boolean getSuperHoliday60HOccurs() {
		return this.entity.getIs60hSuperHd() == BOOLEAN_TRUE;
	}

	/**
	 * Gets the use classification.
	 *
	 * @return the use classification
	 */
	@Override
	public UseClassification getUseClassification() {
		return UseClassification.valueOf(this.entity.getUseAtr());
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	@Override
	public OvertimeName getName() {
		return new OvertimeName(this.entity.getName());
	}

	/**
	 * Gets the overtime.
	 *
	 * @return the overtime
	 */
	@Override
	public OvertimeValue getOvertime() {
		return new OvertimeValue(this.entity.getOverTime());
	}

	/**
	 * Gets the overtime no.
	 *
	 * @return the overtime no
	 */
	@Override
	public OvertimeNo getOvertimeNo() {
		return OvertimeNo.valueOf(this.entity.getKshstOverTimePK().getOverTimeNo());
	}

}
