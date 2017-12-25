/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import java.util.List;

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
 * The Class JpaFlexCommonRestSettingGetMemento.
 */
public class JpaWorkTimezoneCommonSetGetMemento implements WorkTimezoneCommonSetGetMemento {

	/** The entity. */
	private KshmtWorktimeCommonSet entity;

	/**
	 * Instantiates a new jpa flex common rest setting get memento.
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

	@Override
	public boolean getZeroHStraddCalculateSet() {
		return false;
	}

	@Override
	public IntervalTimeSetting getIntervalSet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WorkTimezoneOtherSubHolTimeSet> getSubHolTimeSet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BonusPaySettingCode getRaisingSalarySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WorkTimezoneMedicalSet> getMedicalSet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WorkTimezoneGoOutSet getGoOutSet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WorkTimezoneStampSet getStampSet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WorkTimezoneLateNightTimeSet getLateNightTimeSet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WorkTimezoneShortTimeWorkSet getShortTimeWorkSet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WorkTimezoneExtraordTimeSet getExtraordTimeSet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WorkTimezoneLateEarlySet getLateEarlySet() {
		// TODO Auto-generated method stub
		return null;
	}

}
