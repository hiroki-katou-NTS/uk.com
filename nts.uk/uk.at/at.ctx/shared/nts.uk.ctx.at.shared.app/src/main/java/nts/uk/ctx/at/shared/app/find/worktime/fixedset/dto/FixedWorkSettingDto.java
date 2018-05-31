/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.fixedset.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.FixedWorkCalcSettingDto;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.FixedWorkRestSetDto;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.StampReflectTimezoneDto;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.WorkTimezoneCommonSetDto;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkRestSet;
import nts.uk.ctx.at.shared.dom.worktime.common.LegalOTSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezone;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixHalfDayWorkTimezone;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixOffdayWorkTimezone;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkCalcSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingSetMemento;

/**
 * The Class FixedWorkSettingDto.
 */
@Getter
@Setter
public class FixedWorkSettingDto implements FixedWorkSettingSetMemento {

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
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingSetMemento#
	 * setCompanyId(java.lang.String)
	 */
	@Override
	public void setCompanyId(String companyId) {
		// Nothing to do
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingSetMemento#
	 * setWorkTimeCode(nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode)
	 */
	@Override
	public void setWorkTimeCode(WorkTimeCode workTimeCode) {
		this.workTimeCode = workTimeCode.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingSetMemento#
	 * setOffdayWorkTimezone(nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * FixOffdayWorkTimezone)
	 */
	@Override
	public void setOffdayWorkTimezone(FixOffdayWorkTimezone offdayWorkTimezone) {
		if (offdayWorkTimezone != null) {
			this.offdayWorkTimezone = new FixOffdayWorkTimezoneDto();
			offdayWorkTimezone.saveToMemento(this.offdayWorkTimezone);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingSetMemento#
	 * setCommonSetting(nts.uk.ctx.at.shared.dom.worktime.common.
	 * WorkTimezoneCommonSet)
	 */
	@Override
	public void setCommonSetting(WorkTimezoneCommonSet commonSetting) {
		if (commonSetting != null) {
			this.commonSetting = new WorkTimezoneCommonSetDto();
			commonSetting.saveToMemento(this.commonSetting);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingSetMemento#
	 * setUseHalfDayShift(java.lang.Boolean)
	 */
	@Override
	public void setUseHalfDayShift(Boolean useHalfDayShift) {
		this.useHalfDayShift = useHalfDayShift;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingSetMemento#
	 * setFixedWorkRestSetting(nts.uk.ctx.at.shared.dom.worktime.common.
	 * FixedWorkRestSet)
	 */
	@Override
	public void setFixedWorkRestSetting(FixedWorkRestSet fixedWorkRestSetting) {
		if (fixedWorkRestSetting != null) {
			this.fixedWorkRestSetting = new FixedWorkRestSetDto();
			fixedWorkRestSetting.saveToMemento(this.fixedWorkRestSetting);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingSetMemento#
	 * setLstHalfDayWorkTimezone(java.util.List)
	 */
	@Override
	public void setLstHalfDayWorkTimezone(List<FixHalfDayWorkTimezone> lstHalfDayWorkTimezone) {
		if (CollectionUtil.isEmpty(lstHalfDayWorkTimezone)) {
			this.lstHalfDayWorkTimezone = new ArrayList<>();
		} else {
			this.lstHalfDayWorkTimezone = lstHalfDayWorkTimezone.stream().map(domain -> {
				FixHalfDayWorkTimezoneDto dto = new FixHalfDayWorkTimezoneDto();
				domain.saveToMemento(dto);
				return dto;
			}).collect(Collectors.toList());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingSetMemento#
	 * setLstStampReflectTimezone(java.util.List)
	 */
	@Override
	public void setLstStampReflectTimezone(List<StampReflectTimezone> lstStampReflectTimezone) {
		if (CollectionUtil.isEmpty(lstStampReflectTimezone)) {
			this.lstStampReflectTimezone = new ArrayList<>();
		} else {
			this.lstStampReflectTimezone = lstStampReflectTimezone.stream().map(domain -> {
				StampReflectTimezoneDto dto = new StampReflectTimezoneDto();
				domain.saveToMemento(dto);
				return dto;
			}).collect(Collectors.toList());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingSetMemento#
	 * setLegalOTSetting(nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * LegalOTSetting)
	 */
	@Override
	public void setLegalOTSetting(LegalOTSetting legalOTSetting) {
		this.legalOTSetting = legalOTSetting.value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingSetMemento#
	 * setFixedWorkCalcSetting(nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * FixedWorkCalcSetting)
	 */
	@Override
	public void setCalculationSetting(Optional<FixedWorkCalcSetting> fixedWorkCalcSetting) {
		if (fixedWorkCalcSetting.isPresent()) {
			this.calculationSetting = new FixedWorkCalcSettingDto();
			fixedWorkCalcSetting.get().saveToMemento(this.calculationSetting);
		}
	}

}
