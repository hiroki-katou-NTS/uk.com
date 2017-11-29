/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.ot.autocalsetting.wkpjob;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalFlexOvertimeSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalOvertimeSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalRestTimeSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.JobTitleId;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.wkpjob.WkpJobAutoCalSettingSetMemento;
import nts.uk.ctx.at.shared.infra.entity.ot.autocalsetting.wkpjob.KshmtAutoWkpJobCal;
import nts.uk.ctx.at.shared.infra.entity.ot.autocalsetting.wkpjob.KshmtAutoWkpJobCalPK;

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
		if (entity.getKshmtAutoWkpJobCalPK() == null) {
			entity.setKshmtAutoWkpJobCalPK(new KshmtAutoWkpJobCalPK());
		}
		this.entity = entity;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.WkpJobAutoCalSettingSetMemento#setCompanyId(nts.uk.ctx.at.shared.dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		KshmtAutoWkpJobCalPK pk = entity.getKshmtAutoWkpJobCalPK();
		pk.setCid(companyId.v());
		this.entity.setKshmtAutoWkpJobCalPK(pk);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.WkpJobAutoCalSettingSetMemento#setWkpId(nts.uk.ctx.at.shared.dom.common.WorkplaceId)
	 */
	@Override
	public void setWkpId(WorkplaceId workplaceId) {
		KshmtAutoWkpJobCalPK pk = entity.getKshmtAutoWkpJobCalPK();
		pk.setWpkid(workplaceId.v());
		this.entity.setKshmtAutoWkpJobCalPK(pk);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.WkpJobAutoCalSettingSetMemento#setPositionId(nts.uk.ctx.at.schedule.dom.shift.autocalsetting.PositionId)
	 */
	@Override
	public void setJobId(JobTitleId positionId) {
		KshmtAutoWkpJobCalPK pk = entity.getKshmtAutoWkpJobCalPK();
		pk.setJobid(positionId.v());
		this.entity.setKshmtAutoWkpJobCalPK(pk);
		
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.WkpJobAutoCalSettingSetMemento#setNormalOTTime(nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalOvertimeSetting)
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

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.WkpAutoCalSettingSetMemento#setFlexOTTime(nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalFlexOvertimeSetting)
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

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.WkpAutoCalSettingSetMemento#setRestTime(nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalRestTimeSetting)
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
