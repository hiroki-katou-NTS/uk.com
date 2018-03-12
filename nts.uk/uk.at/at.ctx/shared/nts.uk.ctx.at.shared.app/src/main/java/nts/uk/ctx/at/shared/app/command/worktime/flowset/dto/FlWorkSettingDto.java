/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.flowset.dto;

import lombok.Value;
import nts.uk.ctx.at.shared.app.command.worktime.common.dto.FlowWorkRestSettingDto;
import nts.uk.ctx.at.shared.app.command.worktime.common.dto.WorkTimezoneCommonSetDto;
import nts.uk.ctx.at.shared.dom.worktime.common.LegalOTSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowHalfDayWorkTimezone;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowOffdayWorkTimezone;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowStampReflectTimezone;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkDedicateSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSettingGetMemento;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class FlWorkSettingDto.
 */
@Value
public class FlWorkSettingDto implements FlowWorkSettingGetMemento {

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
	public FlowOffdayWorkTimezone getOffdayWorkTimezone() {
		return new FlowOffdayWorkTimezone(this.offdayWorkTimezone);
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
	public FlowHalfDayWorkTimezone getHalfDayWorkTimezone() {
		return new FlowHalfDayWorkTimezone(this.halfDayWorkTimezone);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkSettingGetMemento#
	 * getStampReflectTimezone()
	 */
	@Override
	public FlowStampReflectTimezone getStampReflectTimezone() {
		return new FlowStampReflectTimezone(this.stampReflectTimezone);
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
	public FlowWorkDedicateSetting getFlowSetting() {
		return new FlowWorkDedicateSetting(this.flowSetting);
	}

}
