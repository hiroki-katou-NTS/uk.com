/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.flexset.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.FlowWorkRestSettingDto;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.StampReflectTimezoneDto;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.WorkTimezoneCommonSetDto;
import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezone;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.flexset.CoreTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexCalcSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexHalfDayWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexOffdayWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestSetting;

/**
 * The Class FlexWorkSettingDto.
 */
@Getter
public class FlexWorkSettingDto implements FlexWorkSettingSetMemento {

	/** The work time code. */
	private String workTimeCode;

	/** The core time setting. */
	private CoreTimeSettingDto coreTimeSetting;

	/** The rest setting. */
	private FlowWorkRestSettingDto restSetting;

	/** The offday work time. */
	private FlexOffdayWorkTimeDto offdayWorkTime;

	/** The common setting. */
	private WorkTimezoneCommonSetDto commonSetting;

	/** The use half day shift. */
	private boolean useHalfDayShift;

	/** The lst half day work timezone. */
	private List<FlexHalfDayWorkTimeDto> lstHalfDayWorkTimezone;

	/** The lst stamp reflect timezone. */
	private List<StampReflectTimezoneDto> lstStampReflectTimezone;

	/** The calculate setting. */
	private FlexCalcSettingDto calculateSetting;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingSetMemento#
	 * setcompanyId(java.lang.String)
	 */
	@Override
	public void setcompanyId(String companyId) {
		// No thing code
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingSetMemento#
	 * setWorkTimeCode(nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode)
	 */
	@Override
	public void setWorkTimeCode(WorkTimeCode workTimeCode) {
		this.workTimeCode = workTimeCode.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingSetMemento#
	 * setCoreTimeSetting(nts.uk.ctx.at.shared.dom.worktime.flexset.
	 * CoreTimeSetting)
	 */
	@Override
	public void setCoreTimeSetting(CoreTimeSetting coreTimeSetting) {
		if (coreTimeSetting != null) {
			this.coreTimeSetting = new CoreTimeSettingDto();
			coreTimeSetting.saveToMemento(this.coreTimeSetting);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingSetMemento#
	 * setRestSetting(nts.uk.ctx.at.shared.dom.worktime.common.
	 * FlowWorkRestSetting)
	 */
	@Override
	public void setRestSetting(FlowWorkRestSetting restSetting) {
		if (restSetting != null) {
			this.restSetting = new FlowWorkRestSettingDto();
			restSetting.saveToMemento(this.restSetting);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingSetMemento#
	 * setOffdayWorkTime(nts.uk.ctx.at.shared.dom.worktime.flexset.
	 * FlexOffdayWorkTime)
	 */
	@Override
	public void setOffdayWorkTime(FlexOffdayWorkTime offdayWorkTime) {
		if (offdayWorkTime != null) {
			this.offdayWorkTime = new FlexOffdayWorkTimeDto();
			offdayWorkTime.saveToMemento(this.offdayWorkTime);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingSetMemento#
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
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingSetMemento#
	 * setLstHalfDayWorkTimezone(java.util.List)
	 */
	@Override
	public void setLstHalfDayWorkTimezone(List<FlexHalfDayWorkTime> lstHalfDayWorkTimezone) {
		if (CollectionUtil.isEmpty(lstHalfDayWorkTimezone)) {
			this.lstHalfDayWorkTimezone = new ArrayList<>();
		} else {
			this.lstHalfDayWorkTimezone = lstHalfDayWorkTimezone.stream().map(domain -> {
				FlexHalfDayWorkTimeDto dto = new FlexHalfDayWorkTimeDto();
				domain.saveToMemento(dto);
				return dto;
			}).collect(Collectors.toList());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingSetMemento#
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
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingSetMemento#
	 * setCalculateSetting(nts.uk.ctx.at.shared.dom.worktime.flexset.
	 * FlexCalcSetting)
	 */
	@Override
	public void setCalculateSetting(FlexCalcSetting calculateSetting) {
		if (calculateSetting != null) {
			this.calculateSetting = new FlexCalcSettingDto();
			calculateSetting.saveToMemento(this.calculateSetting);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingSetMemento#
	 * setUseHalfDayShift(boolean)
	 */
	@Override
	public void setUseHalfDayShift(boolean useHalfDayShift) {
		this.useHalfDayShift = useHalfDayShift;
	}

}
