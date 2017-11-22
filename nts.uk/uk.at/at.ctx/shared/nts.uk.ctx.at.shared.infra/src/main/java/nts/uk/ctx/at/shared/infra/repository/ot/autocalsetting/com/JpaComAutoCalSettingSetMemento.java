/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.ot.autocalsetting.com;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalFlexOvertimeSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalOvertimeSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalRestTimeSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.com.ComAutoCalSettingSetMemento;
import nts.uk.ctx.at.shared.infra.entity.ot.autocalsetting.com.KshmtAutoComCalSet;

/**
 * The Class JpaComAutoCalSettingSetMemento.
 */

// public class JpaComAutoCalSettingSetMemento {}
public class JpaComAutoCalSettingSetMemento implements ComAutoCalSettingSetMemento {

	/** The entity. */
	private KshmtAutoComCalSet entity;

	/**
	 * Instantiates a new jpa com auto cal setting set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaComAutoCalSettingSetMemento(KshmtAutoComCalSet entity) {
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.
	 * ComAutoCalSettingSetMemento#setCompanyId(nts.uk.ctx.at.shared.dom.common.
	 * CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		this.entity.setCid(companyId.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.
	 * ComAutoCalSettingSetMemento#setNormalOTTime(nts.uk.ctx.at.schedule.dom.
	 * shift.autocalsetting.AutoCalOvertimeSetting)
	 */
	@Override
	public void setNormalOTTime(AutoCalOvertimeSetting normalOTTime) {
		if (normalOTTime.getEarlyOtTime().getCalAtr() != null) {
			this.entity.setEarlyOtTimeAtr(normalOTTime.getEarlyOtTime().getCalAtr().value);
		}		
		if (normalOTTime.getEarlyOtTime().getUpLimitOtSet() != null) {
			this.entity.setEarlyOtTimeLimit(normalOTTime.getEarlyOtTime().getUpLimitOtSet().value);
		}
		
		if (normalOTTime.getEarlyMidOtTime().getCalAtr() != null) {
			this.entity.setEarlyMidOtTimeAtr(normalOTTime.getEarlyMidOtTime().getCalAtr().value);
		}		
		if (normalOTTime.getEarlyMidOtTime().getUpLimitOtSet() != null) {
			this.entity.setEarlyMidOtTimeLimit(normalOTTime.getEarlyMidOtTime().getUpLimitOtSet().value);
		}

		if (normalOTTime.getNormalOtTime().getCalAtr() != null) {
			this.entity.setNormalOtTimeAtr(normalOTTime.getNormalOtTime().getCalAtr().value);
		}		
		if (normalOTTime.getNormalOtTime().getUpLimitOtSet() != null) {
			this.entity.setNormalOtTimeLimit(normalOTTime.getNormalOtTime().getUpLimitOtSet().value);
		}
		
		if (normalOTTime.getNormalMidOtTime().getCalAtr() != null) {
			this.entity.setNormalMidOtTimeAtr(normalOTTime.getNormalMidOtTime().getCalAtr().value);
		}		
		if (normalOTTime.getNormalMidOtTime().getUpLimitOtSet() != null) {
			this.entity.setNormalMidOtTimeLimit(normalOTTime.getNormalMidOtTime().getUpLimitOtSet().value);
		}
		
		if (normalOTTime.getLegalOtTime().getCalAtr() != null) {
			this.entity.setLegalOtTimeAtr(normalOTTime.getLegalOtTime().getCalAtr().value);
		}		
		if (normalOTTime.getLegalOtTime().getUpLimitOtSet() != null) {
			this.entity.setLegalOtTimeLimit(normalOTTime.getLegalOtTime().getUpLimitOtSet().value);
		}

		if (normalOTTime.getLegalMidOtTime().getCalAtr() != null) {
			this.entity.setLegalMidOtTimeAtr(normalOTTime.getLegalMidOtTime().getCalAtr().value);
		}		
		if (normalOTTime.getLegalMidOtTime().getUpLimitOtSet() != null) {
			this.entity.setLegalMidOtTimeLimit(normalOTTime.getLegalMidOtTime().getUpLimitOtSet().value);
		}		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.
	 * ComAutoCalSettingSetMemento#setFlexOTTime(nts.uk.ctx.at.schedule.dom.
	 * shift.autocalsetting.AutoCalFlexOvertimeSetting)
	 */
	@Override
	public void setFlexOTTime(AutoCalFlexOvertimeSetting flexOTTime) {		
		if (flexOTTime.getFlexOtTime().getCalAtr() != null) {
			this.entity.setFlexOtTimeAtr(flexOTTime.getFlexOtTime().getCalAtr().value);
		}		
		if (flexOTTime.getFlexOtTime().getUpLimitOtSet() != null) {
			this.entity.setFlexOtTimeLimit(flexOTTime.getFlexOtTime().getUpLimitOtSet().value);
		}	
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.
	 * ComAutoCalSettingSetMemento#setRestTime(nts.uk.ctx.at.schedule.dom.shift.
	 * autocalsetting.AutoCalRestTimeSetting)
	 */
	@Override
	public void setRestTime(AutoCalRestTimeSetting restTime) {
		if (restTime.getRestTime().getCalAtr() != null) {
			this.entity.setFlexOtTimeAtr(restTime.getRestTime().getCalAtr().value);
		}		
		if (restTime.getRestTime().getUpLimitOtSet() != null) {
			this.entity.setFlexOtTimeLimit(restTime.getRestTime().getUpLimitOtSet().value);
		}	
		if (restTime.getLateNightTime().getCalAtr() != null) {
			this.entity.setFlexOtTimeAtr(restTime.getLateNightTime().getCalAtr().value);
		}		
		if (restTime.getLateNightTime().getUpLimitOtSet() != null) {
			this.entity.setFlexOtTimeLimit(restTime.getLateNightTime().getUpLimitOtSet().value);
		}	
	}

}
