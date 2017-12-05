/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.difftimeset.dto;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.FixedWorkRestSetDto;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.WorkTimezoneCommonSetDto;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkRestSet;
import nts.uk.ctx.at.shared.dom.worktime.common.LegalOTSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSettingSetMemento;

/**
 * The Class TimeDiffWorkSetting.
 */

@Getter
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
		// TODO Auto-generated method stub

	}

	@Override
	public void setWorkTimeCode(WorkTimeCode employmentTimezoneCode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setRestSet(FixedWorkRestSet restSet) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDayoffWorkTimezone(
			nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeDayOffWorkTimezone dayoffWorkTimezone) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setCommonSet(WorkTimezoneCommonSet commonSet) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setIsUseHalfDayShift(boolean isUseHalfDayShift) {
this.isUseHalfDayShift =true;
	}

	@Override
	public void setChangeExtent(nts.uk.ctx.at.shared.dom.worktime.difftimeset.EmTimezoneChangeExtent changeExtent) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setHalfDayWorkTimezones(
			List<nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeHalfDayWorkTimezone> halfDayWorkTimezones) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setStampReflectTimezone(
			nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkStampReflectTimezone stampReflectTimezone) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setOvertimeSetting(LegalOTSetting overtimeSetting) {
		// TODO Auto-generated method stub

	}

}
