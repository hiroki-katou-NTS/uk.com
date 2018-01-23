/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.difftimeset.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
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

/**
 * The Class TimeDiffWorkSetting.
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

	/** The half day work timezone. */
	private List<DiffTimeHalfDayWorkTimezoneDto> halfDayWorkTimezones;

	/** The stamp reflect timezone. */
	private DiffTimeWorkStampReflectTimezoneDto stampReflectTimezone;

	/** The overtime setting. */
	private Integer overtimeSetting;

	@Override
	public void setCompanyId(String companyId) {

	}

	@Override
	public void setWorkTimeCode(WorkTimeCode employmentTimezoneCode) {
		this.workTimeCode = employmentTimezoneCode.v();
	}

	@Override
	public void setRestSet(FixedWorkRestSet restSet) {
		this.restSet = new FixedWorkRestSetDto();
		restSet.saveToMemento(this.restSet);
	}

	@Override
	public void setDayoffWorkTimezone(DiffTimeDayOffWorkTimezone dayoffWorkTimezone) {
		this.dayoffWorkTimezone = new DiffTimeDayOffWorkTimezoneDto();
		dayoffWorkTimezone.saveToMemento(this.dayoffWorkTimezone);
	}

	@Override
	public void setCommonSet(WorkTimezoneCommonSet commonSet) {
		this.commonSet = new WorkTimezoneCommonSetDto();
		commonSet.saveToMemento(this.commonSet);
	}

	@Override
	public void setIsUseHalfDayShift(boolean isUseHalfDayShift) {
		this.isUseHalfDayShift = true;
	}

	@Override
	public void setChangeExtent(EmTimezoneChangeExtent changeExtent) {
		this.changeExtent = new EmTimezoneChangeExtentDto();
		changeExtent.saveToMemento(this.changeExtent);
	}

	@Override
	public void setHalfDayWorkTimezones(List<DiffTimeHalfDayWorkTimezone> halfDayWorkTimezones) {
		this.halfDayWorkTimezones = new ArrayList<>();
		this.halfDayWorkTimezones.addAll(halfDayWorkTimezones.stream().map(item -> {
			DiffTimeHalfDayWorkTimezoneDto dto = new DiffTimeHalfDayWorkTimezoneDto();
			item.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList()));
	}

	@Override
	public void setStampReflectTimezone(DiffTimeWorkStampReflectTimezone stampReflectTimezone) {
		this.stampReflectTimezone = new DiffTimeWorkStampReflectTimezoneDto();
		stampReflectTimezone.saveToMemento(this.stampReflectTimezone);
	}

	@Override
	public void setOvertimeSetting(LegalOTSetting overtimeSetting) {
		this.overtimeSetting = overtimeSetting.value;
	}

}
