/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.timerounding.Rounding;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit;
import nts.uk.ctx.at.shared.dom.shortworktime.ChildCareAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.childcareset.ShortTimeWorkGetRange;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FixedChangeAtr;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeDomainObject;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.ScreenMode;

/**
 * The Class WorkTimezoneShortTimeWorkSet.
 */
// 就業時間帯の短時間勤務設定
@Getter
@NoArgsConstructor
public class WorkTimezoneShortTimeWorkSet extends WorkTimeDomainObject implements Cloneable{

	/** 介護時間帯に勤務した場合に勤務として扱う */
	private boolean nursTimezoneWorkUse;
	/** 育児時間帯に勤務した場合に勤務として扱う */
	private boolean childCareWorkUse;
	/** 丸め設定 */
	private TimeRoundingSetting roundingSet;

	/**
	 * Instantiates a new work timezone short time work set.
	 *
	 * @param nursTimezoneWorkUse
	 *            the nurs timezone work use
	 * @param childCareWorkUse
	 *            the child care work use
	 * @param roundingSet
	 *            the rounding setting
	 */
	public WorkTimezoneShortTimeWorkSet(
			boolean nursTimezoneWorkUse,
			boolean childCareWorkUse,
			TimeRoundingSetting roundingSet) {
		super();
		this.nursTimezoneWorkUse = nursTimezoneWorkUse;
		this.childCareWorkUse = childCareWorkUse;
		this.roundingSet = roundingSet;
	}

	/**
	 * Instantiates a new work timezone short time work set.
	 *
	 * @param memento
	 *            the memento
	 */
	public WorkTimezoneShortTimeWorkSet(WorkTimezoneShortTimeWorkSetGetMemento memento) {
		this.nursTimezoneWorkUse = memento.getNursTimezoneWorkUse();
		this.childCareWorkUse = memento.getChildCareWorkUse();
		this.roundingSet = memento.getRoudingSet();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(WorkTimezoneShortTimeWorkSetSetMemento memento) {
		memento.setNursTimezoneWorkUse(this.nursTimezoneWorkUse);
		memento.setChildCareWorkUse(this.childCareWorkUse);
		memento.setRoudingSet(this.roundingSet);
	}

	/**
	 * Correct data.
	 *
	 * @param screenMode
	 *            the screen mode
	 */
	public void correctData(ScreenMode screenMode) {
		if (ScreenMode.SIMPLE.equals(screenMode)) {
			this.nursTimezoneWorkUse = false;
			this.childCareWorkUse = false;
		}
	}
	
	/**
	 * Correct data for fixedChangeAtr.
	 *
	 * @param fixedChangeAtr
	 */
	public void correctDataForFixedChange(FixedChangeAtr fixedChangeAtr) {
		if (fixedChangeAtr != FixedChangeAtr.NOT_CHANGE) {
			this.nursTimezoneWorkUse = true;
			this.childCareWorkUse = true;
		}
	}
	
	/**
	 * Restore data.
	 *
	 * @param clone
	 */
	public void restoreWorkTimezoneShortTimeWorkSet(WorkTimezoneShortTimeWorkSet clone) {
		this.nursTimezoneWorkUse = clone.childCareWorkUse;
		this.childCareWorkUse = clone.childCareWorkUse;
		this.roundingSet = new TimeRoundingSetting(
				clone.roundingSet.getRoundingTime().value,
				clone.roundingSet.getRounding().value);
	}
	
	@Override
	public WorkTimezoneShortTimeWorkSet clone() {
		WorkTimezoneShortTimeWorkSet cloned = new WorkTimezoneShortTimeWorkSet();
		try {
			cloned.nursTimezoneWorkUse = this.nursTimezoneWorkUse ? true : false ;
			cloned.childCareWorkUse = this.childCareWorkUse ? true : false ;
			cloned.roundingSet = new TimeRoundingSetting(
					this.roundingSet.getRoundingTime().value,
					this.roundingSet.getRounding().value);
		}
		catch (Exception e){
			throw new RuntimeException("WorkTimezoneShortTimeWorkSet clone error.");
		}
		return cloned;
	}
	
	/**
	 * 勤務扱いによる取得範囲の判断
	 * @param childCareAtr 育児介護区分
	 * @return 短時間勤務取得範囲
	 */
	public ShortTimeWorkGetRange checkGetRangeByWorkUse(ChildCareAtr childCareAtr){
		
		ShortTimeWorkGetRange result = ShortTimeWorkGetRange.NOT_GET;
		
		// 育児介護区分を確認する
		boolean isWorkUse = false;		// 勤務とするかどうか
		if (childCareAtr == ChildCareAtr.CHILD_CARE){
			// 「育児時間帯に～」を確認する
			isWorkUse = this.childCareWorkUse;
		}
		else if (childCareAtr == ChildCareAtr.CARE){
			// 「介護時間帯に～」を確認する
			isWorkUse = this.nursTimezoneWorkUse;
		}
		
		if (isWorkUse){
			// 勤務とする
			// 結果　←　「出退勤と重複する時間帯を除く」
			result = ShortTimeWorkGetRange.WITHOUT_ATTENDANCE_LEAVE;
		}
		else{
			// 勤務としない
			// 結果　←　「そのまま取得する」
			result = ShortTimeWorkGetRange.NORMAL_GET;
		}
		// 結果を返す
		return result;
	}

	/**
	 * デフォルト設定のインスタンスを生成する
	 * @return 就業時間帯の短時間勤務設定
	 */
	public static WorkTimezoneShortTimeWorkSet generateDefault(){
		WorkTimezoneShortTimeWorkSet domain = new WorkTimezoneShortTimeWorkSet(false, false,
				new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN));
		return domain;
	}
}
