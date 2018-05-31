/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.fixedset.dto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Value;
import nts.uk.ctx.at.shared.app.command.worktime.common.FixedWorkCalcSettingDto;
import nts.uk.ctx.at.shared.app.command.worktime.common.dto.FixedWorkRestSetDto;
import nts.uk.ctx.at.shared.app.command.worktime.common.dto.StampReflectTimezoneDto;
import nts.uk.ctx.at.shared.app.command.worktime.common.dto.WorkTimezoneCommonSetDto;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkRestSet;
import nts.uk.ctx.at.shared.dom.worktime.common.LegalOTSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezone;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixHalfDayWorkTimezone;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixOffdayWorkTimezone;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkCalcSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingGetMemento;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class FixedWorkSettingDto.
 */
@Value
public class FixedWorkSettingDto implements FixedWorkSettingGetMemento {

	/** The work time code. */
	private String workTimeCode;

	/** The offday work timezone. */
	private FixOffdayWorkTimezoneDto offdayWorkTimezone;

	/** The common setting. */
	private WorkTimezoneCommonSetDto commonSetting;

	/** The use half day shift. */
	private Boolean useHalfDayShift;

	/** The fixed work rest setting. */
	private FixedWorkRestSetDto fixedWorkRestSetting;

	/** The lst half day work timezone. */
	private List<FixHalfDayWorkTimezoneDto> lstHalfDayWorkTimezone;

	/** The lst stamp reflect timezone. */
	private List<StampReflectTimezoneDto> lstStampReflectTimezone;

	/** The legal OT setting. */
	private Integer legalOTSetting;
	
	/** The calculation setting. */
	private FixedWorkCalcSettingDto calculationSetting;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingGetMemento#
	 * getCompanyId()
	 */
	@Override
	public String getCompanyId() {
		return AppContexts.user().companyId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingGetMemento#
	 * getWorkTimeCode()
	 */
	@Override
	public WorkTimeCode getWorkTimeCode() {
		return new WorkTimeCode(this.workTimeCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingGetMemento#
	 * getOffdayWorkTimezone()
	 */
	@Override
	public FixOffdayWorkTimezone getOffdayWorkTimezone() {
		return new FixOffdayWorkTimezone(this.offdayWorkTimezone);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingGetMemento#
	 * getCommonSetting()
	 */
	@Override
	public WorkTimezoneCommonSet getCommonSetting() {
		return new WorkTimezoneCommonSet(this.commonSetting);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingGetMemento#
	 * getUseHalfDayShift()
	 */
	@Override
	public Boolean getUseHalfDayShift() {
		return this.useHalfDayShift;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingGetMemento#
	 * getFixedWorkRestSetting()
	 */
	@Override
	public FixedWorkRestSet getFixedWorkRestSetting() {
		return new FixedWorkRestSet(this.fixedWorkRestSetting);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingGetMemento#
	 * getLstHalfDayWorkTimezone()
	 */
	@Override
	public List<FixHalfDayWorkTimezone> getLstHalfDayWorkTimezone() {
		return this.lstHalfDayWorkTimezone.stream().map(item -> new FixHalfDayWorkTimezone(item))
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingGetMemento#
	 * getLstStampReflectTimezone()
	 */
	@Override
	public List<StampReflectTimezone> getLstStampReflectTimezone() {
		return this.lstStampReflectTimezone.stream().map(item -> new StampReflectTimezone(item))
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingGetMemento#
	 * getLegalOTSetting()
	 */
	@Override
	public LegalOTSetting getLegalOTSetting() {
		return LegalOTSetting.valueOf(this.legalOTSetting);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingGetMemento#getFixedWorkCalcSetting()
	 */
	@Override
	public Optional<FixedWorkCalcSetting> getCalculationSetting() {
		if (this.calculationSetting == null) {
			return Optional.empty();
		}
		return Optional.of(new FixedWorkCalcSetting(this.calculationSetting));
	}
}
