/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.automaticcalculation;

import nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalFlexOvertimeSetting;
import nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalOvertimeSetting;
import nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalRestTimeSetting;
import nts.uk.ctx.at.schedule.dom.shift.autocalsetting.PositionId;
import nts.uk.ctx.at.schedule.dom.shift.autocalsetting.WkpJobAutoCalSettingSetMemento;
import nts.uk.ctx.at.schedule.infra.entity.shift.autocalsetting.KshmtAutoWkpJobCal;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;

/**
 * The Class JpaWkpJobAutoCalSettingSetMemento.
 */
public class JpaWkpJobAutoCalSettingSetMemento implements WkpJobAutoCalSettingSetMemento {
	
	/** The entity. */
	private KshmtAutoWkpJobCal entity;

	/**
	 * Instantiates a new jpa wkp job auto cal setting set memento.
	 *
	 * @param entity the entity
	 */
	public JpaWkpJobAutoCalSettingSetMemento(KshmtAutoWkpJobCal entity) {
		this.entity = entity;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.WkpJobAutoCalSettingSetMemento#setCompanyId(nts.uk.ctx.at.shared.dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		this.entity.getKshmtAutoWkpJobCalPK().setCid(companyId.v());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.WkpJobAutoCalSettingSetMemento#setWkpId(nts.uk.ctx.at.shared.dom.common.WorkplaceId)
	 */
	@Override
	public void setWkpId(WorkplaceId workplaceId) {
		this.entity.getKshmtAutoWkpJobCalPK().setWpkid(workplaceId.v());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.WkpJobAutoCalSettingSetMemento#setPositionId(nts.uk.ctx.at.schedule.dom.shift.autocalsetting.PositionId)
	 */
	@Override
	public void setPositionId(PositionId positionId) {
		this.entity.getKshmtAutoWkpJobCalPK().setJobid(positionId.v());
		
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.WkpJobAutoCalSettingSetMemento#setNormalOTTime(nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalOvertimeSetting)
	 */
	@Override
	public void setNormalOTTime(AutoCalOvertimeSetting normalOTTime) {
		this.entity.setEarlyOtTimeAtr(normalOTTime.getEarlyOtTime().getCalAtr().value);
		this.entity.setEarlyOtTimeLimit(normalOTTime.getEarlyOtTime().getUpLimitOtSet().value);

		this.entity.setEarlyMidOtTimeAtr(normalOTTime.getEarlyMidOtTime().getCalAtr().value);
		this.entity.setEarlyMidOtTimeLimit(normalOTTime.getEarlyMidOtTime().getUpLimitOtSet().value);

		this.entity.setNormalOtTimeAtr(normalOTTime.getNormalOtTime().getCalAtr().value);
		this.entity.setNormalOtTimeLimit(normalOTTime.getNormalOtTime().getUpLimitOtSet().value);

		this.entity.setNormalMidOtTimeAtr(normalOTTime.getNormalMidOtTime().getCalAtr().value);
		this.entity.setNormalMidOtTimeLimit(normalOTTime.getNormalMidOtTime().getUpLimitOtSet().value);

		this.entity.setLegalOtTimeAtr(normalOTTime.getLegalOtTime().getCalAtr().value);
		this.entity.setLegalOtTimeLimit(normalOTTime.getLegalOtTime().getUpLimitOtSet().value);

		this.entity.setLegalMidOtTimeAtr(normalOTTime.getLegalMidOtTime().getCalAtr().value);
		this.entity.setLegalMidOtTimeLimit(normalOTTime.getLegalMidOtTime().getUpLimitOtSet().value);

	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.WkpAutoCalSettingSetMemento#setFlexOTTime(nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalFlexOvertimeSetting)
	 */
	@Override
	public void setFlexOTTime(AutoCalFlexOvertimeSetting flexOTTime) {
		this.entity.setFlexOtNightTimeAtr(flexOTTime.getFlexOtNightTime().getCalAtr().value);
		this.entity.setFlexOtNightTimeLimit(flexOTTime.getFlexOtNightTime().getUpLimitOtSet().value);
		this.entity.setFlexOtTimeAtr(flexOTTime.getFlexOtTime().getCalAtr().value);
		this.entity.setFlexOtTimeLimit(flexOTTime.getFlexOtTime().getUpLimitOtSet().value);

	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.WkpAutoCalSettingSetMemento#setRestTime(nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalRestTimeSetting)
	 */
	@Override
	public void setRestTime(AutoCalRestTimeSetting restTime) {
		this.entity.setRestTimeAtr(restTime.getRestTime().getCalAtr().value);
		this.entity.setRestTimeLimit(restTime.getRestTime().getUpLimitOtSet().value);
		this.entity.setLateNightTimeAtr(restTime.getLateNightTime().getCalAtr().value);
		this.entity.setLateNightTimeLimit(restTime.getLateNightTime().getUpLimitOtSet().value);

	}


}
