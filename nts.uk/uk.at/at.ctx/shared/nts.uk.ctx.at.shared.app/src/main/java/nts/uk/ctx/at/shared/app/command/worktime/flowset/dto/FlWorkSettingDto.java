/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.flowset.dto;

import lombok.Value;
import nts.uk.ctx.at.shared.app.command.worktime.common.dto.FlowWorkRestSettingDto;
import nts.uk.ctx.at.shared.app.command.worktime.common.dto.WorkTimezoneCommonSetDto;
import nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.LegalOTSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlHalfDayWtz;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlOffdayWtz;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlStampReflectTz;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkDedSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkSettingGetMemento;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class FlWorkSettingDto.
 */
@Value
public class FlWorkSettingDto implements FlWorkSettingGetMemento {

	/** The working code. */
	private String workingCode;

	/** The rest setting. */
	private FlowWorkRestSettingDto restSetting;

	/** The offday work timezone. */
	private FlOffdayWorkTzDto offdayWorkTimezone;

	/** The common setting. */
	private WorkTimezoneCommonSetDto commonSetting;

	/** The half day work timezone. */
	private FlHalfDayWorkTzDto halfDayWorkTimezone;

	/** The stamp reflect timezone. */
	private FlStampReflectTzDto stampReflectTimezone;

	/** The designated setting. */
	private Integer designatedSetting;

	/** The flow setting. */
	private FlWorkDedSettingDto flowSetting;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkSettingGetMemento#
	 * getCompanyId()
	 */
	@Override
	public String getCompanyId() {
		return AppContexts.user().companyId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkSettingGetMemento#
	 * getWorkingCode()
	 */
	@Override
	public WorkTimeCode getWorkingCode() {
		return new WorkTimeCode(this.workingCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkSettingGetMemento#
	 * getRestSetting()
	 */
	@Override
	public FlowWorkRestSetting getRestSetting() {
		return new FlowWorkRestSetting(this.restSetting);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkSettingGetMemento#
	 * getOffdayWorkTimezone()
	 */
	@Override
	public FlOffdayWtz getOffdayWorkTimezone() {
		return new FlOffdayWtz(this.offdayWorkTimezone);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkSettingGetMemento#
	 * getCommonSetting()
	 */
	@Override
	public WorkTimezoneCommonSet getCommonSetting() {
		return new WorkTimezoneCommonSet(this.commonSetting);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkSettingGetMemento#
	 * getHalfDayWorkTimezone()
	 */
	@Override
	public FlHalfDayWtz getHalfDayWorkTimezone() {
		return new FlHalfDayWtz(this.halfDayWorkTimezone);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkSettingGetMemento#
	 * getStampReflectTimezone()
	 */
	@Override
	public FlStampReflectTz getStampReflectTimezone() {
		return new FlStampReflectTz(this.stampReflectTimezone);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkSettingGetMemento#
	 * getDesignatedSetting()
	 */
	@Override
	public LegalOTSetting getLegalOTSetting() {
		return LegalOTSetting.valueOf(this.designatedSetting);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkSettingGetMemento#
	 * getFlowSetting()
	 */
	@Override
	public FlWorkDedSetting getFlowSetting() {
		return new FlWorkDedSetting(this.flowSetting);
	}

}
