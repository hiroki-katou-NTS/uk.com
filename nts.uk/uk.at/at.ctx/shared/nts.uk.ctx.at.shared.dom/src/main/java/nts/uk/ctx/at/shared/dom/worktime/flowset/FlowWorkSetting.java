/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.LegalOTSetting;

/**
 * The Class FlowWorkSetting.
 */
// 流動勤務設定
@Getter
public class FlowWorkSetting extends AggregateRoot {

	/** The company code. */
	// 会社ID
	private String companyId;

	/** The working code. */
	// 就業時間帯コード
	private WorkTimeCode workingCode;

	/** The rest setting. */
	// 休憩設定
	private FlowWorkRestSetting restSetting;

	/** The offday work timezone. */
	// 休日勤務時間帯
	private FlowOffdayWorkTimezone offdayWorkTimezone;

	/** The common setting. */
	// 共通設定
	private WorkTimezoneCommonSet commonSetting;

	/** The half day work timezone. */
	// 平日勤務時間帯
	private FlowHalfDayWorkTimezone halfDayWorkTimezone;

	/** The stamp reflect timezone. */
	// 打刻反映時間帯
	// TODO: NOT USE IN UI
	private FlowStampReflectTimezone stampReflectTimezone;

	/** The designated setting. */
	// 法定内残業設定
	private LegalOTSetting designatedSetting;

	/** The flow setting. */
	// 流動設定
	private FlowWorkDedicateSetting flowSetting;

	/**
	 * Instantiates a new flow work setting.
	 *
	 * @param memento the memento
	 */
	public FlowWorkSetting(FlowWorkSettingGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.workingCode = memento.getWorkingCode();
		this.restSetting = memento.getRestSetting();
		this.offdayWorkTimezone = memento.getOffdayWorkTimezone();
		this.commonSetting = memento.getCommonSetting();
		this.halfDayWorkTimezone = memento.getHalfDayWorkTimezone();
		this.stampReflectTimezone = memento.getStampReflectTimezone();
		this.designatedSetting = memento.getDesignatedSetting();
		this.flowSetting = memento.getFlowSetting();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(FlowWorkSettingSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setWorkingCode(this.workingCode);
		memento.setRestSetting(this.restSetting);
		memento.setOffdayWorkTimezone(this.offdayWorkTimezone);
		memento.setCommonSetting(this.commonSetting);
		memento.setHalfDayWorkTimezone(this.halfDayWorkTimezone);
		memento.setStampReflectTimezone(this.stampReflectTimezone);
		memento.setDesignatedSetting(this.designatedSetting);
		memento.setFlowSetting(this.flowSetting);
	}
}
