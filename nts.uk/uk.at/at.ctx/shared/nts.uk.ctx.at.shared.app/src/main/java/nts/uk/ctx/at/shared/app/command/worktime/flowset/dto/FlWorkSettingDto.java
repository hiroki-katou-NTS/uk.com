/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.flowset.dto;

import nts.uk.ctx.at.shared.app.find.worktime.common.dto.FlowWorkRestSettingDto;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.WorkTimezoneCommonSetDto;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
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
 * The Class FlowWorkSettingDto.
 */
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

	@Override
	public String getCompanyId() {
		return AppContexts.user().companyId();
	}

	@Override
	public WorkTimeCode getWorkingCode() {
		return new WorkTimeCode(this.workingCode);
	}

	@Override
	public FlowWorkRestSetting getRestSetting() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FlOffdayWtz getOffdayWorkTimezone() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WorkTimezoneCommonSet getCommonSetting() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FlHalfDayWtz getHalfDayWorkTimezone() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FlStampReflectTz getStampReflectTimezone() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LegalOTSetting getDesignatedSetting() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FlWorkDedSetting getFlowSetting() {
		// TODO Auto-generated method stub
		return null;
	}

}
