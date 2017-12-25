/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.bonuspay.primitives.BonusPaySettingCode;
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
import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtWorktimeCommonSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtWorktimeCommonSetPK;

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
		if (entity.getKshmtWorktimeCommonSetPK() == null) {
			entity.setKshmtWorktimeCommonSetPK(new KshmtWorktimeCommonSetPK());
		}
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
		return false;
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
		return null;
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
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

}
