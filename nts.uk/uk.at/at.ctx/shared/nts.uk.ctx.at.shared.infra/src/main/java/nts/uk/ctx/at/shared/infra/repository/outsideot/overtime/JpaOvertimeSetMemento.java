/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.outsideot.overtime;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.UseClassification;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.OvertimeName;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.OvertimeNo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.OvertimeSetMemento;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.OvertimeValue;
import nts.uk.ctx.at.shared.infra.entity.outsideot.overtime.KshstOverTime;
import nts.uk.ctx.at.shared.infra.entity.outsideot.overtime.KshstOverTimePK;

/**
 * The Class JpaOvertimeSetMemento.
 */
public class JpaOvertimeSetMemento implements OvertimeSetMemento{
	

	/** The Constant BOOLEAN_TRUE. */
	public static final int BOOLEAN_TRUE = 1;
	
	/** The Constant BOOLEAN_FALSE. */
	public static final int BOOLEAN_FALSE = 0;
	
	/** The entity. */
	private KshstOverTime entity;

	/**
	 * Instantiates a new jpa overtime set memento.
	 *
	 * @param entity the entity
	 * @param companyId the company id
	 */
	public JpaOvertimeSetMemento(KshstOverTime entity, String companyId) {
		if(entity.getKshstOverTimePK() == null){
			entity.setKshstOverTimePK(new KshstOverTimePK());
		}
		entity.getKshstOverTimePK().setCid(companyId);
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.overtime.OvertimeSetMemento#
	 * setSuperHoliday60HOccurs(boolean)
	 */
	@Override
	public void setSuperHoliday60HOccurs(boolean superHoliday60HOccurs) {
		this.entity.setIs60hSuperHd(superHoliday60HOccurs ? BOOLEAN_TRUE : BOOLEAN_FALSE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.OvertimeSetMemento#setUseClassification
	 * (nts.uk.ctx.at.shared.dom.overtime.UseClassification)
	 */
	@Override
	public void setUseClassification(UseClassification useClassification) {
		this.entity.setUseAtr(useClassification.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.OvertimeSetMemento#setName(nts.uk.ctx.
	 * at.shared.dom.overtime.OvertimeName)
	 */
	@Override
	public void setName(OvertimeName name) {
		this.entity.setName(name.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.OvertimeSetMemento#setOvertime(nts.uk.
	 * ctx.at.shared.dom.overtime.OvertimeValue)
	 */
	@Override
	public void setOvertime(OvertimeValue overtime) {
		this.entity.setOverTime(overtime.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.OvertimeSetMemento#setOvertimeNo(nts.uk
	 * .ctx.at.shared.dom.overtime.OvertimeNo)
	 */
	@Override
	public void setOvertimeNo(OvertimeNo overtimeNo) {
		this.entity.getKshstOverTimePK().setOverTimeNo(overtimeNo.value);
	}

}
