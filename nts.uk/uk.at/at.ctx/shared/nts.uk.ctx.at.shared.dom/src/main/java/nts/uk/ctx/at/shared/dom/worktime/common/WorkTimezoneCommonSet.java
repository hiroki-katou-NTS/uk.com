/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeDomainObject;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.ScreenMode;

/**
 * The Class WorkTimezoneCommonSet.
 */
// 就業時間帯の共通設定
@Getter
public class WorkTimezoneCommonSet extends WorkTimeDomainObject {

	/** The Zero H stradd calculate set. */
	// 0時跨ぎ計算設定
	private boolean zeroHStraddCalculateSet;

	/** The interval set. */
	// インターバル時間設定
	private IntervalTimeSetting intervalSet;

	/** The sub hol time set. */
	// 代休時間設定
	private List<WorkTimezoneOtherSubHolTimeSet> subHolTimeSet;

	/** The raising salary set. */
	// 加給設定
	private BonusPaySettingCode raisingSalarySet;

	/** The medical sets. */
	// 医療設定
	private List<WorkTimezoneMedicalSet> medicalSets;

	/** The go out set. */
	// 外出設定
	private WorkTimezoneGoOutSet goOutSet;

	/** The stamp set. */
	// 打刻設定
	private WorkTimezoneStampSet stampSet;

	/** The late night time set. */
	// 深夜時間設定
	private WorkTimezoneLateNightTimeSet lateNightTimeSet;

	/** The short time work set. */
	// 短時間勤務設定
	private WorkTimezoneShortTimeWorkSet shortTimeWorkSet;

	/** The extraord time set. */
	// 臨時時間設定
	private WorkTimezoneExtraordTimeSet extraordTimeSet;

	/** The late early set. */
	// 遅刻・早退設定
	private WorkTimezoneLateEarlySet lateEarlySet;

	/**
	 * Instantiates a new work timezone common set.
	 *
	 * @param memento the memento
	 */
	public WorkTimezoneCommonSet(WorkTimezoneCommonSetGetMemento memento) {
		this.zeroHStraddCalculateSet = memento.getZeroHStraddCalculateSet();
		this.intervalSet = memento.getIntervalSet();
		this.subHolTimeSet = memento.getSubHolTimeSet();
		this.raisingSalarySet = memento.getRaisingSalarySet();
		this.medicalSets = memento.getMedicalSet();
		this.goOutSet = memento.getGoOutSet();
		this.stampSet = memento.getStampSet();
		this.lateNightTimeSet = memento.getLateNightTimeSet();
		this.shortTimeWorkSet = memento.getShortTimeWorkSet();
		this.extraordTimeSet = memento.getExtraordTimeSet();
		this.lateEarlySet = memento.getLateEarlySet();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(WorkTimezoneCommonSetSetMemento memento) {
		memento.setZeroHStraddCalculateSet(this.zeroHStraddCalculateSet);
		memento.setIntervalSet(this.intervalSet);
		memento.setSubHolTimeSet(this.subHolTimeSet);
		memento.setRaisingSalarySet(this.raisingSalarySet);
		memento.setMedicalSet(this.medicalSets);
		memento.setGoOutSet(this.goOutSet);
		memento.setStampSet(this.stampSet);
		memento.setLateNightTimeSet(this.lateNightTimeSet);
		memento.setShortTimeWorkSet(this.shortTimeWorkSet);
		memento.setExtraordTimeSet(this.extraordTimeSet);
		memento.setLateEarlySet(this.lateEarlySet);
	}

	/**
	 * Restore data.
	 *
	 * @param screenMode the screen mode
	 * @param oldDomain the old domain
	 */
	public void restoreData(ScreenMode screenMode, WorkTimezoneCommonSet oldDomain) {
		this.goOutSet.restoreData(screenMode, oldDomain.getGoOutSet());
		this.subHolTimeSet.forEach(item -> item.restoreData(screenMode, oldDomain.getSubHolTimeSet().stream()
				.filter(oldItem -> oldItem.getOriginAtr().equals(item.getOriginAtr())).findFirst().orElse(null)));
		this.stampSet.restoreData(screenMode, oldDomain.getStampSet());
	}
	
	/**
	 * Restore default data.
	 *
	 * @param screenMode the screen mode
	 */
	public void restoreDefaultData(ScreenMode screenMode) {
		this.goOutSet.restoreDefaultData(screenMode);
		this.subHolTimeSet.forEach(item -> item.restoreDefaultData(screenMode));
		this.stampSet.restoreDefaultData(screenMode);
	}
}
