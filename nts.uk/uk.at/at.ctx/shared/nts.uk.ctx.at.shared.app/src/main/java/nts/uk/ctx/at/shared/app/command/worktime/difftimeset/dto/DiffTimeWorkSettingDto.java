/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.difftimeset.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.app.command.worktime.common.FixedWorkCalcSettingDto;
import nts.uk.ctx.at.shared.app.command.worktime.common.dto.FixedWorkRestSetDto;
import nts.uk.ctx.at.shared.app.command.worktime.common.dto.WorkTimezoneCommonSetDto;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkRestSet;
import nts.uk.ctx.at.shared.dom.worktime.common.LegalOTSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeDayOffWorkTimezone;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeHalfDayWorkTimezone;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSettingGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkStampReflectTimezone;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.EmTimezoneChangeExtent;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkCalcSetting;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class DiffTimeWorkSettingDto.
 */
@Getter
@Setter
public class DiffTimeWorkSettingDto implements DiffTimeWorkSettingGetMemento {

	/** The work time code. */
	private String workTimeCode;

	/** The rest set. */
	private FixedWorkRestSetDto restSet;

	/** The dayoff work timezone. */
	private DiffTimeDayOffWorkTimezoneDto dayoffWorkTimezone;

	/** The common set. */
	private WorkTimezoneCommonSetDto commonSet;

	/** The use half day shift. */
	private boolean useHalfDayShift;

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
	 * DiffTimeWorkSettingGetMemento#getCompanyId()
	 */
	@Override
	public String getCompanyId() {
		return AppContexts.user().companyId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.difftimeset.
	 * DiffTimeWorkSettingGetMemento#getWorkTimeCode()
	 */
	@Override
	public WorkTimeCode getWorkTimeCode() {
		return new WorkTimeCode(this.workTimeCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.difftimeset.
	 * DiffTimeWorkSettingGetMemento#getRestSet()
	 */
	@Override
	public FixedWorkRestSet getRestSet() {
		return new FixedWorkRestSet(this.restSet);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.difftimeset.
	 * DiffTimeWorkSettingGetMemento#getDayoffWorkTimezone()
	 */
	@Override
	public DiffTimeDayOffWorkTimezone getDayoffWorkTimezone() {
		return this.dayoffWorkTimezone.toDomain();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.difftimeset.
	 * DiffTimeWorkSettingGetMemento#getCommonSet()
	 */
	@Override
	public WorkTimezoneCommonSet getCommonSet() {
		return new WorkTimezoneCommonSet(this.commonSet);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.difftimeset.
	 * DiffTimeWorkSettingGetMemento#isIsUseHalfDayShift()
	 */
	@Override
	public boolean isIsUseHalfDayShift() {
		return this.useHalfDayShift;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.difftimeset.
	 * DiffTimeWorkSettingGetMemento#getChangeExtent()
	 */
	@Override
	public EmTimezoneChangeExtent getChangeExtent() {
		if (this.changeExtent == null) {
			return null;
		}
		return this.changeExtent.toDomain();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.difftimeset.
	 * DiffTimeWorkSettingGetMemento#getHalfDayWorkTimezones()
	 */
	@Override
	public List<DiffTimeHalfDayWorkTimezone> getHalfDayWorkTimezones() {
		if (CollectionUtil.isEmpty(this.halfDayWorkTimezones)) {
			return new ArrayList<>();
		}
		return this.halfDayWorkTimezones.stream().map(dto -> dto.toDomain()).collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.difftimeset.
	 * DiffTimeWorkSettingGetMemento#getStampReflectTimezone()
	 */
	@Override
	public DiffTimeWorkStampReflectTimezone getStampReflectTimezone() {
		return this.stampReflectTimezone.toDomain();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.difftimeset.
	 * DiffTimeWorkSettingGetMemento#getOvertimeSetting()
	 */
	@Override
	public LegalOTSetting getOvertimeSetting() {
		return LegalOTSetting.valueOf(this.overtimeSetting);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.difftimeset.
	 * DiffTimeWorkSettingGetMemento#getCalculationSetting()
	 */
	@Override
	public Optional<FixedWorkCalcSetting> getCalculationSetting() {
		if (this.calculationSetting == null) {
			return Optional.empty();
		}
		return Optional.of(new FixedWorkCalcSetting(this.calculationSetting));
	}

}
