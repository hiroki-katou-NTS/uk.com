/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktime.common.LegalOTSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeAggregateRoot;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.ScreenMode;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDailyAtr;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDivision;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeMethodSet;

/**
 * The Class FlowWorkSetting.
 */
// 流動勤務設定
@Getter
public class FlowWorkSetting extends WorkTimeAggregateRoot {

	/** The company id. */
	// 会社ID
	private String companyId;

	/** The working code. */
	// 就業時間帯コード
	private WorkTimeCode workingCode;

	/** The half day work timezone. */
	// 平日勤務時間帯
	private FlowHalfDayWorkTimezone halfDayWorkTimezone;

	/** The offday work timezone. */
	// 休日勤務時間帯
	private FlowOffdayWorkTimezone offdayWorkTimezone;

	/** The stamp reflect timezone. */
	// 打刻反映時間帯
	private FlowStampReflectTimezone stampReflectTimezone;

	/** The legal OT setting. */
	// 法定内残業設定
	private LegalOTSetting legalOTSetting;

	/** The rest setting. */
	// 休憩設定
	private FlowWorkRestSetting restSetting;

	/** The common setting. */
	// 共通設定
	private WorkTimezoneCommonSet commonSetting;

	/** The flow setting. */
	// 流動設定
	private FlowWorkDedicateSetting flowSetting;

	/**
	 * Instantiates a new flow work setting.
	 *
	 * @param memento
	 *            the memento
	 */
	public FlowWorkSetting(FlowWorkSettingGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.workingCode = memento.getWorkingCode();
		this.restSetting = memento.getRestSetting();
		this.offdayWorkTimezone = memento.getOffdayWorkTimezone();
		this.commonSetting = memento.getCommonSetting();
		this.halfDayWorkTimezone = memento.getHalfDayWorkTimezone();
		this.stampReflectTimezone = memento.getStampReflectTimezone();
		this.legalOTSetting = memento.getLegalOTSetting();
		this.flowSetting = memento.getFlowSetting();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(FlowWorkSettingSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setWorkingCode(this.workingCode);
		memento.setRestSetting(this.restSetting);
		memento.setOffdayWorkTimezone(this.offdayWorkTimezone);
		memento.setCommonSetting(this.commonSetting);
		memento.setHalfDayWorkTimezone(this.halfDayWorkTimezone);
		memento.setStampReflectTimezone(this.stampReflectTimezone);
		memento.setLegalOTSetting(this.legalOTSetting);
		memento.setFlowSetting(this.flowSetting);
	}

	/**
	 * Restore data.
	 *
	 * @param screenMode
	 *            the screen mode
	 * @param workTimeType
	 *            the work time type
	 * @param other
	 *            the other
	 */
	public void correctData(ScreenMode screenMode, WorkTimeDivision workTimeType, FlowWorkSetting other) {		
		// Dialog J: list stamp timezone
		this.stampReflectTimezone.correctData(screenMode, other.getStampReflectTimezone());
		
		// Tab 2 + 5 + 7
		if (workTimeType.getWorkTimeDailyAtr() == WorkTimeDailyAtr.REGULAR_WORK
				&& workTimeType.getWorkTimeMethodSet() == WorkTimeMethodSet.FLOW_WORK) {
			// Tab 2: restore 平日勤務時間帯
			this.flowSetting.correctData(screenMode, other.getFlowSetting());
			// Tab 5
			this.halfDayWorkTimezone.correctData(screenMode, other.getHalfDayWorkTimezone());
			// Tab 7
			this.offdayWorkTimezone.correctData(screenMode, other.getOffdayWorkTimezone());
			
			this.restSetting.correctData(screenMode,other.getRestSetting(),other.getHalfDayWorkTimezone().getRestTimezone().isFixRestTime());
		} else {
			// Tab 2
			this.flowSetting = other.getFlowSetting();
			// Tab 5
			this.halfDayWorkTimezone = other.getHalfDayWorkTimezone();
			// Tab 7
			this.offdayWorkTimezone = other.getOffdayWorkTimezone();
		}
		
		// Tab 8 -> 16
		this.commonSetting.correctData(screenMode, other.getCommonSetting());
	}

	/**
	 * Restore default data.
	 *
	 * @param screenMode
	 *            the screen mode
	 */
	public void correctDefaultData(ScreenMode screenMode) {
		// Dialog J: list stamp timezone
		this.stampReflectTimezone.correctDefaultData(screenMode);
		
		// Tab 2 + 5: restore 平日勤務時間帯
		this.halfDayWorkTimezone.correctDefaultData(screenMode);
		
		// Tab 7
		this.offdayWorkTimezone.correctDefaultData(screenMode);
		
		// Tab 8 -> 16
		this.commonSetting.correctDefaultData(screenMode);
		//Dialog H
		this.restSetting.correctDefaultData(screenMode,this.getHalfDayWorkTimezone().getRestTimezone().isFixRestTime());
	}

	/**
	 * Correct default data.
	 *
	 * @param screenMode the screen mode
	 * @param overtimeSetting the overtime setting
	 */
	public void setDefaultData(ScreenMode screenMode) {
		if (screenMode == ScreenMode.SIMPLE) {
			this.halfDayWorkTimezone.correctDefaultData();
		}
	}
}
