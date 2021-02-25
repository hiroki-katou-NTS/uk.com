/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.ot.autocalsetting.wkp;

import nts.uk.ctx.at.shared.dom.calculationattribute.AutoCalcSetOfDivergenceTime;
import nts.uk.ctx.at.shared.dom.calculationattribute.enums.DivergenceTimeAttr;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalFlexOvertimeSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalOvertimeSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalRestTimeSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalcOfLeaveEarlySetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.wkp.WkpAutoCalSettingGetMemento;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalRaisingSalarySetting;
import nts.uk.ctx.at.shared.infra.entity.ot.autocalsetting.wkp.KshmtAutoWkpCalSet;
import nts.uk.ctx.at.shared.infra.repository.ot.autocalsetting.JpaAutoCalFlexOvertimeSettingGetMemento;
import nts.uk.ctx.at.shared.infra.repository.ot.autocalsetting.JpaAutoCalOvertimeSettingGetMemento;
import nts.uk.ctx.at.shared.infra.repository.ot.autocalsetting.JpaAutoCalRestTimeSettingGetMemento;

/**
 * The Class JpaWkpAutoCalSettingGetMemento.
 */
public class JpaWkpAutoCalSettingGetMemento implements WkpAutoCalSettingGetMemento {


	/** The entity. */
	private KshmtAutoWkpCalSet  entity;

	/**
	 * Instantiates a new jpa wkp auto cal setting get memento.
	 *
	 * @param entity the entity
	 */
	public JpaWkpAutoCalSettingGetMemento(KshmtAutoWkpCalSet entity) {
		this.entity = entity;
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.WkpAutoCalSettingGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.entity.getKshmtAutoWkpCalSetPK().getCid());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.WkpAutoCalSettingGetMemento#getWkpId()
	 */
	@Override
	public WorkplaceId getWkpId() {
		return new WorkplaceId(this.entity.getKshmtAutoWkpCalSetPK().getWkpid());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.WkpAutoCalSettingGetMemento#getNormalOTTime()
	 */
	@Override
	public AutoCalOvertimeSetting getNormalOTTime() {
		return new AutoCalOvertimeSetting(new JpaAutoCalOvertimeSettingGetMemento(this.entity));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.WkpAutoCalSettingGetMemento#getFlexOTTime()
	 */
	@Override
	public AutoCalFlexOvertimeSetting getFlexOTTime() {
		return new AutoCalFlexOvertimeSetting(new JpaAutoCalFlexOvertimeSettingGetMemento(this.entity));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.WkpAutoCalSettingGetMemento#getRestTime()
	 */
	@Override
	public AutoCalRestTimeSetting getRestTime() {
		return new AutoCalRestTimeSetting(new JpaAutoCalRestTimeSettingGetMemento(this.entity));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.ot.autocalsetting.com.ComAutoCalSettingGetMemento#getLeaveEarly()
	 */
	@Override
	public AutoCalcOfLeaveEarlySetting getLeaveEarly() {
		return new AutoCalcOfLeaveEarlySetting(this.entity.isLeaveLate(), this.entity.isLeaveEarly());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.ot.autocalsetting.com.ComAutoCalSettingGetMemento#getRaisingSalary()
	 */
	@Override
	public AutoCalRaisingSalarySetting getRaisingSalary() {
		return new AutoCalRaisingSalarySetting(this.entity.isSpecificRaisingCalcAtr(), this.entity.isRaisingCalcAtr());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.ot.autocalsetting.com.ComAutoCalSettingGetMemento#getDivergenceTime()
	 */
	@Override
	public AutoCalcSetOfDivergenceTime getDivergenceTime() {
		return new AutoCalcSetOfDivergenceTime(DivergenceTimeAttr.valueOf(this.entity.getDivergence()));
	}
}
