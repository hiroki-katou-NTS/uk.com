/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.IntervalTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSetGetMemento;
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
 * The Class JpaWorkTimezoneCommonSetGetMemento.
 */
public class JpaWorkTimezoneCommonSetGetMemento implements WorkTimezoneCommonSetGetMemento {

	/** The entity. */
	private KshmtWorktimeCommonSet entity;

	/**
	 * Instantiates a new jpa work timezone common set get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaWorkTimezoneCommonSetGetMemento(KshmtWorktimeCommonSet entity) {
		super();
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSetGetMemento#
	 * getZeroHStraddCalculateSet()
	 */
	@Override
	public boolean getZeroHStraddCalculateSet() {
		return this.entity.getOverDayCalcSet() == 1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSetGetMemento#
	 * getIntervalSet()
	 */
	@Override
	public IntervalTimeSetting getIntervalSet() {
		return new IntervalTimeSetting(new JpaIntervalTimeSettingGetMemento(entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSetGetMemento#
	 * getSubHolTimeSet()
	 */
	@Override
	public List<WorkTimezoneOtherSubHolTimeSet> getSubHolTimeSet() {
		return this.entity.getKshmtSubstitutionSets().stream()
				.map(item -> new WorkTimezoneOtherSubHolTimeSet(
						new JpaWorkTimezoneOtherSubHolTimeSetGetMemento(item)))
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSetGetMemento#
	 * getRaisingSalarySet()
	 */
	@Override
	public BonusPaySettingCode getRaisingSalarySet() {
		return new BonusPaySettingCode(this.entity.getRaisingSalarySet());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSetGetMemento#
	 * getMedicalSet()
	 */
	@Override
	public List<WorkTimezoneMedicalSet> getMedicalSet() {
		return this.entity.getKshmtMedicalTimeSets().stream().map(
				item -> new WorkTimezoneMedicalSet(new JpaWorkTimezoneMedicalSetGetMemento(item)))
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSetGetMemento#
	 * getGoOutSet()
	 */
	@Override
	public WorkTimezoneGoOutSet getGoOutSet() {
		return new WorkTimezoneGoOutSet(new JpaWorkTimezoneGoOutSetGetMemento(
				this.entity.getKshmtWorktimeGoOutSet(), this.entity.getKshmtSpecialRoundOuts()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSetGetMemento#
	 * getStampSet()
	 */
	@Override
	public WorkTimezoneStampSet getStampSet() {
		return new WorkTimezoneStampSet(new JpaWorkTimezoneStampSetGetMemento(
				this.entity.getKshmtRoundingSets(), this.entity.getKshmtPioritySets()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSetGetMemento#
	 * getLateNightTimeSet()
	 */
	@Override
	public WorkTimezoneLateNightTimeSet getLateNightTimeSet() {
		return new WorkTimezoneLateNightTimeSet(new TimeRoundingSetting(
				this.entity.getLateNightUnit(), this.entity.getLateNightRounding()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSetGetMemento#
	 * getShortTimeWorkSet()
	 */
	@Override
	public WorkTimezoneShortTimeWorkSet getShortTimeWorkSet() {
		return new WorkTimezoneShortTimeWorkSet(this.entity.getNurTimezoneWorkUse() == 1,
				this.entity.getEmpTimeDeduct() == 1, this.entity.getChildCareWorkUse() == 1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSetGetMemento#
	 * getExtraordTimeSet()
	 */
	@Override
	public WorkTimezoneExtraordTimeSet getExtraordTimeSet() {
		return new WorkTimezoneExtraordTimeSet(new JpaWorkTimezoneExtraordTimeSetGetMemento(
				this.entity.getKshmtTempWorktimeSet()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSetGetMemento#
	 * getLateEarlySet()
	 */
	@Override
	public WorkTimezoneLateEarlySet getLateEarlySet() {
		return new WorkTimezoneLateEarlySet(new JpaWorkTimezoneLateEarlySetGetMemento(this.entity));
	}

}
