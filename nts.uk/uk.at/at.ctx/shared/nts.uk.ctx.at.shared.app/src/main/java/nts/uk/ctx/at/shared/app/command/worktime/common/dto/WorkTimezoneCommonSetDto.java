/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.common.dto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.worktime.common.HolidayCalculation;
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

/**
 * The Class WorkTimezoneCommonSetDto.
 */
@Getter
@Setter
public class WorkTimezoneCommonSetDto implements WorkTimezoneCommonSetGetMemento {

	/** The zero H stradd calculate set. */
	private boolean zeroHStraddCalculateSet;

	/** The interval set. */
	private IntervalTimeSettingDto intervalSet;

	/** The sub hol time set. */
	private List<WorkTimezoneOtherSubHolTimeSetDto> subHolTimeSet;

	/** The medical set. */
	private List<WorkTimezoneMedicalSetDto> medicalSet;

	/** The go out set. */
	private WorkTimezoneGoOutSetDto goOutSet;

	/** The stamp set. */
	private WorkTimezoneStampSetDto stampSet;

	/** The late night time set. */
	private WorkTimezoneLateNightTimeSetDto lateNightTimeSet;

	/** The short time work set. */
	private WorkTimezoneShortTimeWorkSetDto shortTimeWorkSet;

	/** The extraord time set. */
	private WorkTimezoneExtraordTimeSetDto extraordTimeSet;

	/** The late early set. */
	private WorkTimezoneLateEarlySetDto lateEarlySet;

	/** The holiday calculation. */
	private HolidayCalculationDto holidayCalculation;

	/** The raising salary set. */
	private String raisingSalarySet;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSetGetMemento#
	 * getZeroHStraddCalculateSet()
	 */
	@Override
	public boolean getZeroHStraddCalculateSet() {
		return this.zeroHStraddCalculateSet;
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
		return new IntervalTimeSetting(this.intervalSet);
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
		return this.subHolTimeSet.stream().map(item -> new WorkTimezoneOtherSubHolTimeSet(item))
				.collect(Collectors.toList());
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
		return this.medicalSet.stream().map(item -> new WorkTimezoneMedicalSet(item)).collect(Collectors.toList());
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
		return new WorkTimezoneGoOutSet(this.goOutSet);
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
		return new WorkTimezoneStampSet(this.stampSet);
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
		return new WorkTimezoneLateNightTimeSet(this.lateNightTimeSet);
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
		return new WorkTimezoneShortTimeWorkSet(this.shortTimeWorkSet);
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
		return new WorkTimezoneExtraordTimeSet(this.extraordTimeSet);
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
		return new WorkTimezoneLateEarlySet(this.lateEarlySet);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSetGetMemento#
	 * getHolidayCalculation()
	 */
	@Override
	public HolidayCalculation getHolidayCalculation() {
		return new HolidayCalculation(this.holidayCalculation);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSetGetMemento#
	 * getRaisingSalarySet()
	 */
	@Override
	public Optional<BonusPaySettingCode> getRaisingSalarySet() {
		if (this.raisingSalarySet == null) {
			return Optional.empty();
		}
		return Optional.of(new BonusPaySettingCode(this.raisingSalarySet));
	}
}
