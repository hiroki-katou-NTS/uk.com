/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.ot.autocalsetting.wkp;

import nts.uk.ctx.at.shared.dom.calculationattribute.AutoCalcSetOfDivergenceTime;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalFlexOvertimeSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalOvertimeSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalRestTimeSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalcOfLeaveEarlySetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.wkp.WkpAutoCalSettingSetMemento;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalRaisingSalarySetting;
import nts.uk.ctx.at.shared.infra.entity.ot.autocalsetting.wkp.KrcmtCalcSetWkp;
import nts.uk.ctx.at.shared.infra.entity.ot.autocalsetting.wkp.KrcmtCalcSetWkpPK;

/**
 * The Class JpaWkpAutoCalSettingSetMemento.
 */
public class JpaWkpAutoCalSettingSetMemento implements WkpAutoCalSettingSetMemento {

	/** The entity. */
	private KrcmtCalcSetWkp entity;

	/**
	 * Instantiates a new jpa wkp auto cal setting set memento.
	 *
	 * @param entity the entity
	 */
	public JpaWkpAutoCalSettingSetMemento(KrcmtCalcSetWkp entity) {
		if (entity.getKrcmtCalcSetWkpPK() == null) {
			entity.setKrcmtCalcSetWkpPK(new KrcmtCalcSetWkpPK());
		}
		this.entity = entity;
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.WkpAutoCalSettingSetMemento#setCompanyId(nts.uk.ctx.at.shared.dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		KrcmtCalcSetWkpPK pk = entity.getKrcmtCalcSetWkpPK();
		pk.setCid(companyId.v());
		this.entity.setKrcmtCalcSetWkpPK(pk);

	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.WkpAutoCalSettingSetMemento#setWkpId(nts.uk.ctx.at.shared.dom.common.WorkplaceId)
	 */
	@Override
	public void setWkpId(WorkplaceId workplaceId) {
		KrcmtCalcSetWkpPK pk = entity.getKrcmtCalcSetWkpPK();
		pk.setWkpid(workplaceId.v());
		this.entity.setKrcmtCalcSetWkpPK(pk);

	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.WkpAutoCalSettingSetMemento#setNormalOTTime(nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalOvertimeSetting)
	 */
	@Override
	public void setNormalOTTime(AutoCalOvertimeSetting normalOTTime) {
		this.entity.setEarlyOtTimeAtr(normalOTTime.getEarlyOtTime().getCalAtr().value);
		this.entity.setEarlyOtTimeLimit(normalOTTime.getEarlyOtTime().getUpLimitORtSet().value);
		this.entity.setEarlyMidOtTimeAtr(normalOTTime.getEarlyMidOtTime().getCalAtr().value);
		this.entity.setEarlyMidOtTimeLimit(normalOTTime.getEarlyMidOtTime().getUpLimitORtSet().value);
		this.entity.setNormalOtTimeAtr(normalOTTime.getNormalOtTime().getCalAtr().value);
		this.entity.setNormalOtTimeLimit(normalOTTime.getNormalOtTime().getUpLimitORtSet().value);
		this.entity.setNormalMidOtTimeAtr(normalOTTime.getNormalMidOtTime().getCalAtr().value);
		this.entity.setNormalMidOtTimeLimit(normalOTTime.getNormalMidOtTime().getUpLimitORtSet().value);
		this.entity.setLegalOtTimeAtr(normalOTTime.getLegalOtTime().getCalAtr().value);
		this.entity.setLegalOtTimeLimit(normalOTTime.getLegalOtTime().getUpLimitORtSet().value);
		this.entity.setLegalMidOtTimeAtr(normalOTTime.getLegalMidOtTime().getCalAtr().value);
		this.entity.setLegalMidOtTimeLimit(normalOTTime.getLegalMidOtTime().getUpLimitORtSet().value);	
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.WkpAutoCalSettingSetMemento#setFlexOTTime(nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalFlexOvertimeSetting)
	 */
	@Override
	public void setFlexOTTime(AutoCalFlexOvertimeSetting flexOTTime) {
		this.entity.setFlexOtTimeAtr(flexOTTime.getFlexOtTime().getCalAtr().value);
		this.entity.setFlexOtTimeLimit(flexOTTime.getFlexOtTime().getUpLimitORtSet().value);	
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.WkpAutoCalSettingSetMemento#setRestTime(nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalRestTimeSetting)
	 */
	@Override
	public void setRestTime(AutoCalRestTimeSetting restTime) {
		this.entity.setRestTimeAtr(restTime.getRestTime().getCalAtr().value);
		this.entity.setRestTimeLimit(restTime.getRestTime().getUpLimitORtSet().value);
		this.entity.setLateNightTimeAtr(restTime.getLateNightTime().getCalAtr().value);	
		this.entity.setLateNightTimeLimit(restTime.getLateNightTime().getUpLimitORtSet().value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.ot.autocalsetting.com.
	 * ComAutoCalSettingSetMemento#setLeaveEarly(nts.uk.ctx.at.shared.dom.ot.
	 * autocalsetting.AutoCalcOfLeaveEarlySetting)
	 */
	@Override
	public void setLeaveEarly(AutoCalcOfLeaveEarlySetting leaveEarly) {
		this.entity.setLeaveEarly(leaveEarly.isLeaveEarly());
		this.entity.setLeaveLate(leaveEarly.isLate());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.ot.autocalsetting.com.
	 * ComAutoCalSettingSetMemento#setRaisingSalary(nts.uk.ctx.at.shared.dom.
	 * workrule.outsideworktime.AutoCalRaisingSalarySetting)
	 */
	@Override
	public void setRaisingSalary(AutoCalRaisingSalarySetting raisingSalary) {
		this.entity.setRaisingCalcAtr(raisingSalary.isRaisingSalaryCalcAtr());
		this.entity.setSpecificRaisingCalcAtr(raisingSalary.isSpecificRaisingSalaryCalcAtr());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.ot.autocalsetting.com.
	 * ComAutoCalSettingSetMemento#setDivergenceTime(nts.uk.ctx.at.shared.dom.
	 * calculationattribute.AutoCalcSetOfDivergenceTime)
	 */
	@Override
	public void setDivergenceTime(AutoCalcSetOfDivergenceTime divergenceTime) {
		this.entity.setDivergence(divergenceTime.getDivergenceTime().value);
	}
}
