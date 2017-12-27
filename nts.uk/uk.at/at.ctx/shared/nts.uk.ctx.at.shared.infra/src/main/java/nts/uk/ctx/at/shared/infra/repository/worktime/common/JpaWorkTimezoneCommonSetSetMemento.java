/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import java.util.List;

import nts.uk.ctx.at.shared.dom.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.IntervalTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSetSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneExtraordTimeSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneGoOutSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneLateEarlySet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneLateNightTimeSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneMedicalSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneOtherSubHolTimeSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneShortTimeWorkSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneStampSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWorktimeCommonSet;

/**
 * The Class JpaWorkTimezoneCommonSetSetMemento.
 */
public class JpaWorkTimezoneCommonSetSetMemento implements WorkTimezoneCommonSetSetMemento {

	/** The entity. */
	private KshmtWorktimeCommonSet entity;

	/**
	 * Instantiates a new jpa work timezone common set set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaWorkTimezoneCommonSetSetMemento(KshmtWorktimeCommonSet entity) {
		super();
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSetSetMemento#
	 * setZeroHStraddCalculateSet(boolean)
	 */
	@Override
	public void setZeroHStraddCalculateSet(boolean calcSet) {
		this.entity.setOverDayCalcSet(BooleanGetAtr.getAtrByBoolean(calcSet));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSetSetMemento#
	 * setIntervalSet(nts.uk.ctx.at.shared.dom.worktime.common.
	 * IntervalTimeSetting)
	 */
	@Override
	public void setIntervalSet(IntervalTimeSetting itvset) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSetSetMemento#
	 * setSubHolTimeSet(java.util.List)
	 */
	@Override
	public void setSubHolTimeSet(List<WorkTimezoneOtherSubHolTimeSet> shtSet) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSetSetMemento#
	 * setRaisingSalarySet(nts.uk.ctx.at.shared.dom.bonuspay.primitives.
	 * BonusPaySettingCode)
	 */
	@Override
	public void setRaisingSalarySet(BonusPaySettingCode set) {
		this.entity.setRaisingSalarySet(set.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSetSetMemento#
	 * setMedicalSet(java.util.List)
	 */
	@Override
	public void setMedicalSet(List<WorkTimezoneMedicalSet> list) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSetSetMemento#
	 * setGoOutSet(nts.uk.ctx.at.shared.dom.worktime.common.
	 * WorkTimezoneGoOutSet)
	 */
	@Override
	public void setGoOutSet(WorkTimezoneGoOutSet set) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSetSetMemento#
	 * setStampSet(nts.uk.ctx.at.shared.dom.worktime.common.
	 * WorkTimezoneStampSet)
	 */
	@Override
	public void setStampSet(WorkTimezoneStampSet set) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSetSetMemento#
	 * setLateNightTimeSet(nts.uk.ctx.at.shared.dom.worktime.common.
	 * WorkTimezoneLateNightTimeSet)
	 */
	@Override
	public void setLateNightTimeSet(WorkTimezoneLateNightTimeSet set) {
		this.entity.setLateNightUnit(set.getRoundingSetting().getRoundingTime().value);
		this.entity.setLateNightRounding(set.getRoundingSetting().getRounding().value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSetSetMemento#
	 * setShortTimeWorkSet(nts.uk.ctx.at.shared.dom.worktime.common.
	 * WorkTimezoneShortTimeWorkSet)
	 */
	@Override
	public void setShortTimeWorkSet(WorkTimezoneShortTimeWorkSet set) {
		this.entity.setNurTimezoneWorkUse(BooleanGetAtr.getAtrByBoolean(set.isNursTimezoneWorkUse()));
		this.entity.setEmpTimeDeduct(BooleanGetAtr.getAtrByBoolean(set.isEmploymentTimeDeduct()));
		this.entity.setChildCareWorkUse(BooleanGetAtr.getAtrByBoolean(set.isChildCareWorkUse()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSetSetMemento#
	 * setExtraordTimeSet(nts.uk.ctx.at.shared.dom.worktime.common.
	 * WorkTimezoneExtraordTimeSet)
	 */
	@Override
	public void setExtraordTimeSet(WorkTimezoneExtraordTimeSet set) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSetSetMemento#
	 * setLateEarlySet(nts.uk.ctx.at.shared.dom.worktime.common.
	 * WorkTimezoneLateEarlySet)
	 */
	@Override
	public void setLateEarlySet(WorkTimezoneLateEarlySet set) {
		// TODO Auto-generated method stub

	}

}
