/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.flowset.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.FlowWorkRestSettingDto;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.WorkTimezoneCommonSetDto;
import nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.LegalOTSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowHalfDayWorkTimezone;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowOffdayWorkTimezone;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowStampReflectTimezone;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkDedicateSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSettingSetMemento;

/**
 * The Class FlowWorkSettingDto.
 */
@Getter
@Setter
public class FlowWorkSettingDto implements FlowWorkSettingSetMemento {

	/** The working code. */
	private String workingCode;

	/** The rest setting. */
	private FlowWorkRestSettingDto restSetting;

	/** The offday work timezone. */
	private FlowOffdayWorkTimezoneDto offdayWorkTimezone;

	/** The common setting. */
	private WorkTimezoneCommonSetDto commonSetting;

	/** The half day work timezone. */
	private FlowHalfDayWorkTimezoneDto halfDayWorkTimezone;

	/** The stamp reflect timezone. */
	private FlowStampReflectTimezoneDto stampReflectTimezone;

	/** The designated setting. */
	private Integer designatedSetting;

	/** The flow setting. */
	private FlowWorkDedicateSettingDto flowSetting;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSettingSetMemento#
	 * setCompanyId(java.lang.String)
	 */
	@Override
	public void setCompanyId(String cid) {
		// unnecessary.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSettingSetMemento#
	 * setWorkingCode(nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode)
	 */
	@Override
	public void setWorkingCode(WorkTimeCode wtCode) {
		this.workingCode = wtCode.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSettingSetMemento#
	 * setRestSetting(nts.uk.ctx.at.shared.dom.worktime.common.
	 * FlowWorkRestSetting)
	 */
	@Override
	public void setRestSetting(FlowWorkRestSetting restSet) {
		restSet.saveToMemento(this.restSetting);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSettingSetMemento#
	 * setOffdayWorkTimezone(nts.uk.ctx.at.shared.dom.worktime.flowset.
	 * FlowOffdayWorkTimezone)
	 */
	@Override
	public void setOffdayWorkTimezone(FlowOffdayWorkTimezone offDayWtz) {
		offDayWtz.saveToMemento(this.offdayWorkTimezone);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSettingSetMemento#
	 * setCommonSetting(nts.uk.ctx.at.shared.dom.worktime.common.
	 * WorkTimezoneCommonSet)
	 */
	@Override
	public void setCommonSetting(WorkTimezoneCommonSet cmnSet) {
		cmnSet.saveToMemento(this.commonSetting);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSettingSetMemento#
	 * setHalfDayWorkTimezone(nts.uk.ctx.at.shared.dom.worktime.flowset.
	 * FlowHalfDayWorkTimezone)
	 */
	@Override
	public void setHalfDayWorkTimezone(FlowHalfDayWorkTimezone halfDayWtz) {
		halfDayWtz.saveToMemento(this.halfDayWorkTimezone);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSettingSetMemento#
	 * setStampReflectTimezone(nts.uk.ctx.at.shared.dom.worktime.flowset.
	 * FlowStampReflectTimezone)
	 */
	@Override
	public void setStampReflectTimezone(FlowStampReflectTimezone stampRefTz) {
		stampRefTz.saveToMemento(this.stampReflectTimezone);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSettingSetMemento#
	 * setDesignatedSetting(nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * LegalOTSetting)
	 */
	@Override
	public void setDesignatedSetting(LegalOTSetting legalOtSet) {
		this.designatedSetting = legalOtSet.value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSettingSetMemento#
	 * setFlowSetting(nts.uk.ctx.at.shared.dom.worktime.flowset.
	 * FlowWorkDedicateSetting)
	 */
	@Override
	public void setFlowSetting(FlowWorkDedicateSetting flowSet) {
		flowSet.saveToMemento(this.flowSetting);
	}
}
