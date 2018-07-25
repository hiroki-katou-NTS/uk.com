/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.difftimeset;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkRestSet;
import nts.uk.ctx.at.shared.dom.worktime.common.LegalOTSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkCalcSetting;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeAggregateRoot;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.ScreenMode;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDivision;

/**
 * The Class DiffTimeWorkSetting.
 */
@Getter
// 時差勤務設定
public class DiffTimeWorkSetting extends WorkTimeAggregateRoot {

	/** The company id. */
	// 会社ID
	private String companyId;

	/** The work time code. */
	// 就業時間帯コード
	private WorkTimeCode workTimeCode;

	/** The rest set. */
	// 休憩設定
	private FixedWorkRestSet restSet;

	/** The dayoff work timezone. */
	// 休日勤務時間帯
	private DiffTimeDayOffWorkTimezone dayoffWorkTimezone;

	/** The common set. */
	// 共通設定
	private WorkTimezoneCommonSet commonSet;

	/** The is use half day shift. */
	// 半日用シフトを使用する
	private boolean isUseHalfDayShift;

	/** The change extent. */
	// 変動可能範囲
	private EmTimezoneChangeExtent changeExtent;

	/** The half day work timezones. */
	// 平日勤務時間帯
	private List<DiffTimeHalfDayWorkTimezone> halfDayWorkTimezones;

	/** The stamp reflect timezone. */
	// 打刻反映時間帯
	private DiffTimeWorkStampReflectTimezone stampReflectTimezone;

	/** The overtime setting. */
	// 残業設定
	private LegalOTSetting overtimeSetting;

	/** The calculation setting. */
	// 計算設定
	private Optional<FixedWorkCalcSetting> calculationSetting;

	/**
	 * Instantiates a new diff time work setting.
	 *
	 * @param memento
	 *            the memento
	 */
	public DiffTimeWorkSetting(DiffTimeWorkSettingGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.workTimeCode = memento.getWorkTimeCode();
		this.restSet = memento.getRestSet();
		this.dayoffWorkTimezone = memento.getDayoffWorkTimezone();
		this.commonSet = memento.getCommonSet();
		this.isUseHalfDayShift = memento.isIsUseHalfDayShift();
		this.changeExtent = memento.getChangeExtent();
		this.halfDayWorkTimezones = memento.getHalfDayWorkTimezones();
		this.stampReflectTimezone = memento.getStampReflectTimezone();
		this.overtimeSetting = memento.getOvertimeSetting();
		this.calculationSetting = memento.getCalculationSetting();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(DiffTimeWorkSettingSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setWorkTimeCode(this.workTimeCode);
		memento.setRestSet(this.restSet);
		memento.setDayoffWorkTimezone(this.dayoffWorkTimezone);
		memento.setCommonSet(this.commonSet);
		memento.setIsUseHalfDayShift(this.isUseHalfDayShift);
		memento.setChangeExtent(this.changeExtent);
		memento.setHalfDayWorkTimezones(this.halfDayWorkTimezones);
		memento.setStampReflectTimezone(this.stampReflectTimezone);
		memento.setOvertimeSetting(this.overtimeSetting);
		memento.setCalculationSetting(this.calculationSetting);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((workTimeCode == null) ? 0 : workTimeCode.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof DiffTimeWorkSetting))
			return false;
		DiffTimeWorkSetting other = (DiffTimeWorkSetting) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (workTimeCode == null) {
			if (other.workTimeCode != null)
				return false;
		} else if (!workTimeCode.equals(other.workTimeCode))
			return false;
		return true;
	}

	/**
	 * Correct data.
	 *
	 * @param screenMode
	 *            the screen mode
	 * @param workTimeType
	 *            the work time type
	 * @param oldDomain
	 *            the old domain
	 */
	public void correctData(ScreenMode screenMode, WorkTimeDivision workTimeType, DiffTimeWorkSetting oldDomain) {
		// Dialog J: list stamp timezone
		this.stampReflectTimezone.correctData(screenMode, oldDomain.getStampReflectTimezone());
		
		// Tab 8 -> 16
		this.commonSet.correctData(screenMode, oldDomain.getCommonSet());
		
		// Tab 17
		if (this.calculationSetting.isPresent()) {
			this.calculationSetting.get().correctData(screenMode, oldDomain.getCalculationSetting());
		}
	}

	/**
	 * Correct default data.
	 *
	 * @param screenMode
	 *            the screen mode
	 */
	public void correctDefaultData(ScreenMode screenMode) {
		// Tab 2 + 3 + 5: restore 平日勤務時間帯
		this.halfDayWorkTimezones.forEach(item -> item.correctDefaultData(screenMode));
		
		// Dialog J: list stamp timezone
		this.stampReflectTimezone.correctDefaultData(screenMode);

		// Tab 8 -> 16
		this.commonSet.correctDefaultData(screenMode);
		
		// Tab 17
		if (this.calculationSetting.isPresent()) {
			this.calculationSetting.get().correctDefaultData(screenMode);
		}	
	}

	/**
	 * Correct default data.
	 *
	 * @param screenMode the screen mode
	 * @param overtimeSetting the overtime setting
	 */
	public void setDefaultData(ScreenMode screenMode) {
		if (screenMode == ScreenMode.SIMPLE || this.overtimeSetting == LegalOTSetting.OUTSIDE_LEGAL_TIME) {
			this.halfDayWorkTimezones.forEach(item -> item.correctDefaultData());
		}
	}
}