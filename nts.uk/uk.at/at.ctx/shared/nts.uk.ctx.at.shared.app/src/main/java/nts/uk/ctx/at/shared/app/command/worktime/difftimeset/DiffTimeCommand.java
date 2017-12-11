/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.difftimeset;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.app.command.worktime.common.dto.FixedWorkRestSetDto;
import nts.uk.ctx.at.shared.app.command.worktime.common.dto.WorkTimezoneCommonSetDto;
import nts.uk.ctx.at.shared.app.command.worktime.difftimeset.dto.DiffTimeDayOffWorkTimezoneDto;
import nts.uk.ctx.at.shared.app.command.worktime.difftimeset.dto.DiffTimeHalfDayWorkTimezoneDto;
import nts.uk.ctx.at.shared.app.command.worktime.difftimeset.dto.DiffTimeWorkStampReflectTimezoneDto;
import nts.uk.ctx.at.shared.app.command.worktime.difftimeset.dto.EmTimezoneChangeExtentDto;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkRestSet;
import nts.uk.ctx.at.shared.dom.worktime.common.LegalOTSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeDayOffWorkTimezone;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeHalfDayWorkTimezone;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSettingGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkStampReflectTimezone;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.EmTimezoneChangeExtent;

/**
 * The Class DiffTimeCommand.
 */
public class DiffTimeCommand {

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

	/**
	 * To domain.
	 *
	 * @param companyId
	 *            the company id
	 * @return the diff time work setting
	 */
	public DiffTimeWorkSetting toDomain(String companyId) {
		return new DiffTimeWorkSetting(new DiffTimeWorkSettingImpl(companyId, this));
	}

	/**
	 * The Class DiffTimeWorkSettingImpl.
	 */
	public class DiffTimeWorkSettingImpl implements DiffTimeWorkSettingGetMemento {

		/** The company id. */
		private String companyId;

		/** The command. */
		private DiffTimeCommand command;

		/**
		 * @param companyId
		 * @param command
		 */
		public DiffTimeWorkSettingImpl(String companyId, DiffTimeCommand command) {
			this.companyId = companyId;
			this.command = command;
		}

		@Override
		public String getCompanyId() {
			return this.companyId;
		}

		@Override
		public WorkTimeCode getWorkTimeCode() {
			return new WorkTimeCode(this.command.workTimeCode);
		}

		@Override
		public FixedWorkRestSet getRestSet() {
			return new FixedWorkRestSet(this.command.restSet);
		}

		@Override
		public DiffTimeDayOffWorkTimezone getDayoffWorkTimezone() {
			return this.command.dayoffWorkTimezone.toDomain();
		}

		@Override
		public WorkTimezoneCommonSet getCommonSet() {
			return new WorkTimezoneCommonSet(this.command.commonSet);
		}

		@Override
		public boolean isIsUseHalfDayShift() {
			return this.command.isUseHalfDayShift;
		}

		@Override
		public EmTimezoneChangeExtent getChangeExtent() {
			return this.command.changeExtent.toDomain();
		}

		@Override
		public List<DiffTimeHalfDayWorkTimezone> getHalfDayWorkTimezones() {
			return this.command.halfDayWorkTimezones.stream().map(item -> {
				return item.toDomain();
			}).collect(Collectors.toList());
		}

		@Override
		public DiffTimeWorkStampReflectTimezone getStampReflectTimezone() {
			return this.command.stampReflectTimezone.toDomain();
		}

		@Override
		public LegalOTSetting getOvertimeSetting() {
			return LegalOTSetting.valueOf(this.command.overtimeSetting);
		}
	}
}
