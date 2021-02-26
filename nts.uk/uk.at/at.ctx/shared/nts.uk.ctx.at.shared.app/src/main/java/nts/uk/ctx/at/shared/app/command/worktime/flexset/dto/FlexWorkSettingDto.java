/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.flexset.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.app.command.worktime.common.dto.FlowWorkRestSettingDto;
import nts.uk.ctx.at.shared.app.command.worktime.common.dto.StampReflectTimezoneDto;
import nts.uk.ctx.at.shared.app.command.worktime.common.dto.WorkTimezoneCommonSetDto;
import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezone;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.flexset.CoreTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexCalcSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexHalfDayWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexOffdayWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.HalfDayWorkSet;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class FlexWorkSettingDto.
 */
@Getter
@Setter
public class FlexWorkSettingDto implements FlexWorkSettingGetMemento{

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
	private HalfDayWorkSet useHalfDayShift;

	/** The lst half day work timezone. */
	private List<FlexHalfDayWorkTimeDto> lstHalfDayWorkTimezone;

	/** The lst stamp reflect timezone. */
	private List<StampReflectTimezoneDto> lstStampReflectTimezone;

	/** The calculate setting. */
	private FlexCalcSettingDto calculateSetting;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingGetMemento#getCompanyId()
	 */
	@Override
	public String getCompanyId() {
		return AppContexts.user().companyId();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingGetMemento#getWorkTimeCode()
	 */
	@Override
	public WorkTimeCode getWorkTimeCode() {
		return new WorkTimeCode(this.workTimeCode);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingGetMemento#getCoreTimeSetting()
	 */
	@Override
	public CoreTimeSetting getCoreTimeSetting() {
		return new CoreTimeSetting(this.coreTimeSetting);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingGetMemento#getRestSetting()
	 */
	@Override
	public FlowWorkRestSetting getRestSetting() {
		return new FlowWorkRestSetting(this.restSetting);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingGetMemento#getOffdayWorkTime()
	 */
	@Override
	public FlexOffdayWorkTime getOffdayWorkTime() {
		return new FlexOffdayWorkTime(this.offdayWorkTime);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingGetMemento#getCommonSetting()
	 */
	@Override
	public WorkTimezoneCommonSet getCommonSetting() {
		return new WorkTimezoneCommonSet(this.commonSetting);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingGetMemento#getUseHalfDayShift()
	 */
	@Override
	public HalfDayWorkSet getUseHalfDayShift() {
		return this.useHalfDayShift;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingGetMemento#getCalculateSetting()
	 */
	@Override
	public FlexCalcSetting getCalculateSetting() {
		return new FlexCalcSetting(this.calculateSetting);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingGetMemento#getLstHalfDayWorkTimezone()
	 */
	@Override
	public List<FlexHalfDayWorkTime> getLstHalfDayWorkTimezone() {
		if(CollectionUtil.isEmpty(this.lstHalfDayWorkTimezone)){
			return new ArrayList<>();
		}
		return this.lstHalfDayWorkTimezone.stream().map(haffdayworktime -> new FlexHalfDayWorkTime(haffdayworktime))
				.collect(Collectors.toList());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingGetMemento#getLstStampReflectTimezone()
	 */
	@Override
	public List<StampReflectTimezone> getLstStampReflectTimezone() {
		if (CollectionUtil.isEmpty(this.lstStampReflectTimezone)) {
			return new ArrayList<>();
		}
		return this.lstStampReflectTimezone.stream().map(stamp -> new StampReflectTimezone(stamp))
				.collect(Collectors.toList());
	}
	
	
}
