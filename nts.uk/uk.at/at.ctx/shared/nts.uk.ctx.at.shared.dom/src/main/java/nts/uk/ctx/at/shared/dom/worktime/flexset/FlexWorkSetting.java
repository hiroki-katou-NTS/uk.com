/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flexset;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezone;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestSetting;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeAggregateRoot;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.ScreenMode;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDivision;

/**
 * The Class FlexWorkSetting.
 */
@Getter
// フレックス勤務設定
public class FlexWorkSetting extends WorkTimeAggregateRoot {

	/** The company id. */
	// 会社ID
	private String companyId;

	/** The work time code. */
	// 就業時間帯コード
	private WorkTimeCode workTimeCode;

	/** The core time setting. */
	// コアタイム時間帯
	private CoreTimeSetting coreTimeSetting;

	/** The rest setting. */
	// 休憩設定
	private FlowWorkRestSetting restSetting;

	/** The offday work time. */
	// 休日勤務時間帯
	private FlexOffdayWorkTime offdayWorkTime;

	/** The common setting. */
	// 共通設定
	private WorkTimezoneCommonSet commonSetting;

	/** The use half day shift. */
	// 半日用シフトを使用する
	private boolean useHalfDayShift;

	/** The lst half day work timezone. */
	// 平日勤務時間帯
	private List<FlexHalfDayWorkTime> lstHalfDayWorkTimezone;

	/** The lst stamp reflect timezone. */
	// 打刻反映時間帯
	private List<StampReflectTimezone> lstStampReflectTimezone;

	/** The calculate setting. */
	// 計算設定
	private FlexCalcSetting calculateSetting;
	
	/**
	 * Instantiates a new flex work setting.
	 *
	 * @param memento the memento
	 */
	public FlexWorkSetting(FlexWorkSettingGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.workTimeCode = memento.getWorkTimeCode();
		this.coreTimeSetting = memento.getCoreTimeSetting();
		this.restSetting = memento.getRestSetting();
		this.offdayWorkTime = memento.getOffdayWorkTime();
		this.commonSetting = memento.getCommonSetting();
		this.useHalfDayShift = memento.getUseHalfDayShift();
		this.lstHalfDayWorkTimezone = memento.getLstHalfDayWorkTimezone();
		this.lstStampReflectTimezone = memento.getLstStampReflectTimezone();
		this.calculateSetting = memento.getCalculateSetting();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(FlexWorkSettingSetMemento memento){
		memento.setcompanyId(this.companyId);
		memento.setWorkTimeCode(this.workTimeCode);
		memento.setCoreTimeSetting(this.coreTimeSetting);
		memento.setRestSetting(this.restSetting);
		memento.setOffdayWorkTime(this.offdayWorkTime);
		memento.setCommonSetting(this.commonSetting);
		memento.setUseHalfDayShift(this.useHalfDayShift);
		memento.setLstHalfDayWorkTimezone(this.lstHalfDayWorkTimezone);
		memento.setLstStampReflectTimezone(this.lstStampReflectTimezone);
		memento.setCalculateSetting(this.calculateSetting);
	}
	
	/**
	 * Restore data.
	 *
	 * @param screenMode the screen mode
	 * @param workTimeType the work time type
	 * @param other the other
	 */
	public void restoreData(ScreenMode screenMode, WorkTimeDivision workTimeType, FlexWorkSetting other) {
		this.commonSetting.restoreData(screenMode, other.getCommonSetting());
		
		// restore 平日勤務時間帯
		//TODO
//		if (workTimeType.getWorkTimeDailyAtr() == WorkTimeDailyAtr.FLEX_WORK) {
//			
//			// convert map
//			Map<AmPmAtr, FlexHalfDayWorkTime> mapFixHalfWork = other.getLstHalfDayWorkTimezone().stream()
//					.collect(Collectors.toMap(item -> ((FlexHalfDayWorkTime) item).getAmpmAtr(), Function.identity()));
//			
//			this.lstHalfDayWorkTimezone.forEach(item -> item.restoreData(screenMode, this,
//					mapFixHalfWork.get(item.getAmpmAtr())));
//		} else {
//			this.lstHalfDayWorkTimezone = other.getLstHalfDayWorkTimezone();
//		}
		this.offdayWorkTime.restoreData(screenMode,other);
	}
	
	/**
	 * Restore default data.
	 *
	 * @param screenMode the screen mode
	 */
	public void restoreDefaultData(ScreenMode screenMode) {
		this.commonSetting.restoreDefaultData(screenMode);
	}
	
}
