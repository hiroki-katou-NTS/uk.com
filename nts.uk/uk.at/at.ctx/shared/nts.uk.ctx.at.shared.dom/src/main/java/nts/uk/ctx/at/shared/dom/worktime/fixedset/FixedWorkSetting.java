/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fixedset;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkRestSet;
import nts.uk.ctx.at.shared.dom.worktime.common.LegalOTSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezone;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeAggregateRoot;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.ScreenMode;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDailyAtr;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDivision;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeMethodSet;

/**
 * The Class FixedWorkSetting.
 */
@Getter
// 固定勤務設定
public class FixedWorkSetting extends WorkTimeAggregateRoot {

	/** The company id. */
	// 会社ID
	private String companyId;

	/** The work time code. */
	// 就業時間帯コード
	private WorkTimeCode workTimeCode;

	/** The offday work timezone. */
	// 休日勤務時間帯
	private FixOffdayWorkTimezone offdayWorkTimezone;

	/** The common setting. */
	// 共通設定
	private WorkTimezoneCommonSet commonSetting;

	/** The use half day shift. */
	// 半日用シフトを使用する
	private Boolean useHalfDayShift;

	/** The fixed work rest setting. */
	//固定勤務の休憩設定
	private FixedWorkRestSet fixedWorkRestSetting;
	
	/** The lst half day work timezone. */
	// 平日勤務時間帯
	private List<FixHalfDayWorkTimezone> lstHalfDayWorkTimezone;
	
	/** The lst stamp reflect timezone. */
	// 打刻反映時間帯
	private List<StampReflectTimezone> lstStampReflectTimezone;

	/** The legal OT setting. */
	// 法定内残業設定
    private LegalOTSetting legalOTSetting;
    
    /**
     * Instantiates a new fixed work setting.
     *
     * @param memento the memento
     */
    public FixedWorkSetting(FixedWorkSettingGetMemento memento) {
    	this.companyId = memento.getCompanyId();
    	this.workTimeCode = memento.getWorkTimeCode();
    	this.offdayWorkTimezone = memento.getOffdayWorkTimezone();
    	this.commonSetting = memento.getCommonSetting();
    	this.useHalfDayShift = memento.getUseHalfDayShift();
    	this.fixedWorkRestSetting = memento.getFixedWorkRestSetting();
    	this.lstHalfDayWorkTimezone = memento.getLstHalfDayWorkTimezone();
    	this.lstStampReflectTimezone = memento.getLstStampReflectTimezone();
        this.legalOTSetting = memento.getLegalOTSetting();
    }
    
    /**
     * Save to memento.
     *
     * @param memento the memento
     */
    public void saveToMemento(FixedWorkSettingSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setWorkTimeCode(this.workTimeCode);
		memento.setOffdayWorkTimezone(this.offdayWorkTimezone);
		memento.setCommonSetting(this.commonSetting);
		memento.setUseHalfDayShift(this.useHalfDayShift);
		memento.setFixedWorkRestSetting(this.fixedWorkRestSetting);
		memento.setLstHalfDayWorkTimezone(this.lstHalfDayWorkTimezone);
		memento.setLstStampReflectTimezone(this.lstStampReflectTimezone);
		memento.setLegalOTSetting(this.legalOTSetting);
	}

	/* (non-Javadoc)
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

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FixedWorkSetting other = (FixedWorkSetting) obj;
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
	 * Restore data.
	 *
	 * @param screenMode the screen mode
	 * @param workTimeType the work time type
	 * @param oldDomain the old domain
	 */
	public void restoreData(ScreenMode screenMode, WorkTimeDivision workTimeType, FixedWorkSetting oldDomain) {
		this.commonSetting.restoreData(screenMode, oldDomain.getCommonSetting());
		
		// restore 平日勤務時間帯
		if (workTimeType.getWorkTimeDailyAtr() == WorkTimeDailyAtr.REGULAR_WORK
				&& workTimeType.getWorkTimeMethodSet() == WorkTimeMethodSet.FIXED_WORK) {
			Map<AmPmAtr, FixHalfDayWorkTimezone> mapFixHalfWork = oldDomain.getLstHalfDayWorkTimezone().stream()
					.collect(Collectors.toMap(item -> ((FixHalfDayWorkTimezone) item).getDayAtr(),
							Function.identity()));
			this.lstHalfDayWorkTimezone.forEach(item -> item.restoreData(screenMode, this,
					mapFixHalfWork.get(item.getDayAtr())));
		} else {
			this.lstHalfDayWorkTimezone = oldDomain.getLstHalfDayWorkTimezone();
		}
		
		//TODO tab 3 + 5
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
