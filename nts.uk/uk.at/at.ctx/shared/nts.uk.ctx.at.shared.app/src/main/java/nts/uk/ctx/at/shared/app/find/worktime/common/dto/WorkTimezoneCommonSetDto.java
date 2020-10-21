/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.worktime.common.HolidayCalculation;
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

/**
 * The Class WorkTimezoneCommonSetDto.
 */
@Getter
@Setter
public class WorkTimezoneCommonSetDto implements WorkTimezoneCommonSetSetMemento {

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

	/**
	 * Instantiates a new work timezone common set dto.
	 */
	public WorkTimezoneCommonSetDto() {
		this.intervalSet = new IntervalTimeSettingDto();
		this.subHolTimeSet = new ArrayList<>();
		this.medicalSet = new ArrayList<>();
		this.goOutSet = new WorkTimezoneGoOutSetDto();
		this.stampSet = new WorkTimezoneStampSetDto();
		this.lateNightTimeSet = new WorkTimezoneLateNightTimeSetDto();
		this.shortTimeWorkSet = new WorkTimezoneShortTimeWorkSetDto();
		this.extraordTimeSet = new WorkTimezoneExtraordTimeSetDto();
		this.lateEarlySet = new WorkTimezoneLateEarlySetDto();
		this.holidayCalculation = new HolidayCalculationDto();
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
		itvset.saveToMemento(this.intervalSet);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSetSetMemento#
	 * setSubHolTimeSet(nts.uk.ctx.at.shared.dom.worktime.common.
	 * WorkTimezoneOtherSubHolTimeSet)
	 */
	@Override
	public void setSubHolTimeSet(List<WorkTimezoneOtherSubHolTimeSet> list) {
		if (CollectionUtil.isEmpty(list)) {
			return;
		}
		this.subHolTimeSet = list.stream().map(domain -> {
			WorkTimezoneOtherSubHolTimeSetDto dto = new WorkTimezoneOtherSubHolTimeSetDto();
			domain.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());
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
		if (CollectionUtil.isEmpty(list)) {
			return;
		}
		this.medicalSet = list.stream().map(domain -> {
			WorkTimezoneMedicalSetDto dto = new WorkTimezoneMedicalSetDto();
			domain.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());
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
		set.saveToMemento(this.goOutSet);
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
		set.saveToMemento(this.stampSet);
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
		set.saveToMemento(this.lateNightTimeSet);
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
		set.saveToMemento(this.shortTimeWorkSet);
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
		set.saveToMemento(this.extraordTimeSet);
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
		set.saveToMemento(this.lateEarlySet);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSetSetMemento#
	 * setHolidayCalculation(nts.uk.ctx.at.shared.dom.worktime.common.
	 * HolidayCalculation)
	 */
	@Override
	public void setHolidayCalculation(HolidayCalculation holidayCalculation) {
		holidayCalculation.saveToMememto(this.holidayCalculation);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSetSetMemento#
	 * setRaisingSalarySet(java.util.Optional)
	 */
	@Override
	public void setRaisingSalarySet(Optional<BonusPaySettingCode> set) {
		if (set.isPresent()) {
			this.raisingSalarySet = set.get().v();
		}
	}

}
