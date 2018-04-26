/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.difftimeset.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.FixedWorkCalcSettingDto;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.FixedWorkRestSetDto;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.WorkTimezoneCommonSetDto;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkRestSet;
import nts.uk.ctx.at.shared.dom.worktime.common.LegalOTSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeDayOffWorkTimezone;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeHalfDayWorkTimezone;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSettingSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkStampReflectTimezone;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.EmTimezoneChangeExtent;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkCalcSetting;

/**
 * The Class DiffTimeWorkSettingDto.
 */
@Getter
@Setter
public class DiffTimeWorkSettingDto implements DiffTimeWorkSettingSetMemento {

	/** The work time code. */
	private String workTimeCode;

	/** The rest set. */
	private FixedWorkRestSetDto restSet;

	/** The dayoff work timezone. */
	private DiffTimeDayOffWorkTimezoneDto dayoffWorkTimezone;

	/** The common set. */
	private WorkTimezoneCommonSetDto commonSet;

	/** The is use half day shift. */
	private boolean isUseHalfDayShift;

	/** The change extent. */
	private EmTimezoneChangeExtentDto changeExtent;

	/** The half day work timezones. */
	private List<DiffTimeHalfDayWorkTimezoneDto> halfDayWorkTimezones;

	/** The stamp reflect timezone. */
	private DiffTimeWorkStampReflectTimezoneDto stampReflectTimezone;

	/** The overtime setting. */
	private Integer overtimeSetting;

	/** The calculation setting. */
	private FixedWorkCalcSettingDto calculationSetting;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.difftimeset.
	 * DiffTimeWorkSettingSetMemento#setCompanyId(java.lang.String)
	 */
	@Override
	public void setCompanyId(String companyId) {
		// Nothing to do
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.difftimeset.
	 * DiffTimeWorkSettingSetMemento#setWorkTimeCode(nts.uk.ctx.at.shared.dom.
	 * worktime.common.WorkTimeCode)
	 */
	@Override
	public void setWorkTimeCode(WorkTimeCode employmentTimezoneCode) {
		this.workTimeCode = employmentTimezoneCode.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.difftimeset.
	 * DiffTimeWorkSettingSetMemento#setRestSet(nts.uk.ctx.at.shared.dom.
	 * worktime.common.FixedWorkRestSet)
	 */
	@Override
	public void setRestSet(FixedWorkRestSet restSet) {
		this.restSet = new FixedWorkRestSetDto();
		restSet.saveToMemento(this.restSet);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.difftimeset.
	 * DiffTimeWorkSettingSetMemento#setDayoffWorkTimezone(nts.uk.ctx.at.shared.
	 * dom.worktime.difftimeset.DiffTimeDayOffWorkTimezone)
	 */
	@Override
	public void setDayoffWorkTimezone(DiffTimeDayOffWorkTimezone dayoffWorkTimezone) {
		this.dayoffWorkTimezone = new DiffTimeDayOffWorkTimezoneDto();
		dayoffWorkTimezone.saveToMemento(this.dayoffWorkTimezone);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.difftimeset.
	 * DiffTimeWorkSettingSetMemento#setCommonSet(nts.uk.ctx.at.shared.dom.
	 * worktime.common.WorkTimezoneCommonSet)
	 */
	@Override
	public void setCommonSet(WorkTimezoneCommonSet commonSet) {
		this.commonSet = new WorkTimezoneCommonSetDto();
		commonSet.saveToMemento(this.commonSet);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.difftimeset.
	 * DiffTimeWorkSettingSetMemento#setIsUseHalfDayShift(boolean)
	 */
	@Override
	public void setIsUseHalfDayShift(boolean isUseHalfDayShift) {
		this.isUseHalfDayShift = isUseHalfDayShift;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.difftimeset.
	 * DiffTimeWorkSettingSetMemento#setChangeExtent(nts.uk.ctx.at.shared.dom.
	 * worktime.difftimeset.EmTimezoneChangeExtent)
	 */
	@Override
	public void setChangeExtent(EmTimezoneChangeExtent changeExtent) {
		this.changeExtent = new EmTimezoneChangeExtentDto();
		changeExtent.saveToMemento(this.changeExtent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.difftimeset.
	 * DiffTimeWorkSettingSetMemento#setHalfDayWorkTimezones(java.util.List)
	 */
	@Override
	public void setHalfDayWorkTimezones(List<DiffTimeHalfDayWorkTimezone> halfDayWorkTimezones) {
		this.halfDayWorkTimezones = new ArrayList<>();
		this.halfDayWorkTimezones.addAll(halfDayWorkTimezones.stream().map(item -> {
			DiffTimeHalfDayWorkTimezoneDto dto = new DiffTimeHalfDayWorkTimezoneDto();
			item.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.difftimeset.
	 * DiffTimeWorkSettingSetMemento#setStampReflectTimezone(nts.uk.ctx.at.
	 * shared.dom.worktime.difftimeset.DiffTimeWorkStampReflectTimezone)
	 */
	@Override
	public void setStampReflectTimezone(DiffTimeWorkStampReflectTimezone stampReflectTimezone) {
		this.stampReflectTimezone = new DiffTimeWorkStampReflectTimezoneDto();
		stampReflectTimezone.saveToMemento(this.stampReflectTimezone);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.difftimeset.
	 * DiffTimeWorkSettingSetMemento#setOvertimeSetting(nts.uk.ctx.at.shared.dom
	 * .worktime.common.LegalOTSetting)
	 */
	@Override
	public void setOvertimeSetting(LegalOTSetting overtimeSetting) {
		this.overtimeSetting = overtimeSetting.value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.difftimeset.
	 * DiffTimeWorkSettingSetMemento#setCalculationSetting(nts.uk.ctx.at.shared.
	 * dom.worktime.fixedset.FixedWorkCalcSetting)
	 */
	@Override
	public void setCalculationSetting(Optional<FixedWorkCalcSetting> fixedWorkCalcSetting) {
		if (fixedWorkCalcSetting.isPresent()) {
			this.calculationSetting = new FixedWorkCalcSettingDto();
			fixedWorkCalcSetting.get().saveToMemento(this.calculationSetting);
		}
	}

}
