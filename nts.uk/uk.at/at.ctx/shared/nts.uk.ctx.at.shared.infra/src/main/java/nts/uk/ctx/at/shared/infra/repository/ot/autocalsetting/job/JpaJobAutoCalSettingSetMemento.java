/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.ot.autocalsetting.job;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalFlexOvertimeSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalOvertimeSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalRestTimeSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.JobTitleId;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.job.JobAutoCalSettingSetMemento;
import nts.uk.ctx.at.shared.infra.entity.ot.autocalsetting.job.KshmtAutoJobCalSet;
import nts.uk.ctx.at.shared.infra.entity.ot.autocalsetting.job.KshmtAutoJobCalSetPK;

/**
 * The Class JpaJobAutoCalSettingSetMemento.
 */
public class JpaJobAutoCalSettingSetMemento implements JobAutoCalSettingSetMemento {

	/** The entity. */
	private KshmtAutoJobCalSet entity;

	/**
	 * Instantiates a new jpa job auto cal setting set memento.
	 *
	 * @param entity the entity
	 */
	public JpaJobAutoCalSettingSetMemento(KshmtAutoJobCalSet entity) {
		if (entity.getKshmtAutoJobCalSetPK() == null) {
			entity.setKshmtAutoJobCalSetPK(new KshmtAutoJobCalSetPK());
		}
		this.entity = entity;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.JobAutoCalSettingSetMemento#setCompanyId(nts.uk.ctx.at.shared.dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		KshmtAutoJobCalSetPK pk = entity.getKshmtAutoJobCalSetPK();
		pk.setCid(companyId.v());
		this.entity.setKshmtAutoJobCalSetPK(pk);	
		
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.JobAutoCalSettingSetMemento#setPositionId(nts.uk.ctx.at.schedule.dom.shift.autocalsetting.PositionId)
	 */
	@Override
	public void setPositionId(JobTitleId positionId) {
		KshmtAutoJobCalSetPK pk = entity.getKshmtAutoJobCalSetPK();
		pk.setJobid(positionId.v());
		this.entity.setKshmtAutoJobCalSetPK(pk);	

	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.JobAutoCalSettingSetMemento#setNormalOTTime(nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalOvertimeSetting)
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
	 * WkpAutoCalSettingSetMemento#setFlexOTTime(nts.uk.ctx.at.schedule.dom.
	 * shift.autocalsetting.AutoCalFlexOvertimeSetting)
	 */
	@Override
	public void setFlexOTTime(AutoCalFlexOvertimeSetting flexOTTime) {
		this.entity.setFlexOtTimeAtr(flexOTTime.getFlexOtTime().getCalAtr().value);
		this.entity.setFlexOtTimeLimit(flexOTTime.getFlexOtTime().getUpLimitOtSet().value);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.
	 * WkpAutoCalSettingSetMemento#setRestTime(nts.uk.ctx.at.schedule.dom.shift.
	 * autocalsetting.AutoCalRestTimeSetting)
	 */
	@Override
	public void setRestTime(AutoCalRestTimeSetting restTime) {
		this.entity.setRestTimeAtr(restTime.getRestTime().getCalAtr().value);
		this.entity.setRestTimeLimit(restTime.getRestTime().getUpLimitOtSet().value);
		this.entity.setLateNightTimeAtr(restTime.getLateNightTime().getCalAtr().value);
		this.entity.setLateNightTimeLimit(restTime.getLateNightTime().getUpLimitOtSet().value);

	}

}
