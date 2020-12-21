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
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeDomainObject;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.ScreenMode;

/**
 * The Class WorkTimezoneGoOutSet.
 */
//就業時間帯の外出設定
@Getter
@NoArgsConstructor
public class WorkTimezoneGoOutSet extends WorkTimeDomainObject implements Cloneable{

	/** The total rounding set. */
	// 合計丸め設定
	private TotalRoundingSet totalRoundingSet;

	/** The diff timezone setting. */
	// 時間帯別設定
	private GoOutTimezoneRoundingSet diffTimezoneSetting;

	/**
	 * Instantiates a new work timezone go out set.
	 *
	 * @param memento
	 *            the memento
	 */
	public WorkTimezoneGoOutSet(WorkTimezoneGoOutSetGetMemento memento) {
		this.totalRoundingSet = memento.getTotalRoundingSet();
		this.diffTimezoneSetting = memento.getDiffTimezoneSetting();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(WorkTimezoneGoOutSetSetMemento memento) {
		memento.setTotalRoundingSet(this.totalRoundingSet);
		memento.setDiffTimezoneSetting(this.diffTimezoneSetting);
	}

	/**
	 * Correct data.
	 *
	 * @param screenMode
	 *            the screen mode
	 * @param oldDomain
	 *            the old domain
	 */
	public void correctData(ScreenMode screenMode, WorkTimezoneGoOutSet oldDomain) {
		// Simple mode
		if (screenMode == ScreenMode.SIMPLE) {
			this.totalRoundingSet.correctDefaultData();
			this.diffTimezoneSetting.correctDefaultData(screenMode);
		}

		// Go deeper
		this.diffTimezoneSetting.correctData(screenMode, oldDomain.getDiffTimezoneSetting());
	}

	/**
	 * Correct default data.
	 *
	 * @param screenMode
	 *            the screen mode
	 */
	public void correctDefaultData(ScreenMode screenMode) {
		// Simple mode
		if (screenMode == ScreenMode.SIMPLE) {
			this.totalRoundingSet.correctDefaultData();
		}

		// Go deeper
		this.diffTimezoneSetting.correctDefaultData(screenMode);
	}
	
	@Override
	public WorkTimezoneGoOutSet clone() {
		WorkTimezoneGoOutSet cloned = new WorkTimezoneGoOutSet();
		try {
			cloned.totalRoundingSet = this.totalRoundingSet.clone();
			cloned.diffTimezoneSetting = this.diffTimezoneSetting.clone();
		}
		catch (Exception e){
			throw new RuntimeException("WorkTimezoneGoOutSet clone error.");
		}
		return cloned;
	}
	
	/**
	 * デフォルト設定のインスタンスを生成する
	 * @return 就業時間帯の外出設定
	 */
	public static WorkTimezoneGoOutSet generateDefault(){
		WorkTimezoneGoOutSet domain = new WorkTimezoneGoOutSet();
		domain.totalRoundingSet = new TotalRoundingSet(
				GoOutTimeRoundingMethod.TOTAL_AND_ROUNDING,
				GoOutTimeRoundingMethod.TOTAL_AND_ROUNDING);
		
		DeductGoOutRoundingSet deductDefault = new DeductGoOutRoundingSet(
				new GoOutTimeRoundingSetting(RoundingGoOutTimeSheet.REVERSE_ROUNDING_EACH_TIMEZONE,
						new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN)),
				new GoOutTimeRoundingSetting(RoundingGoOutTimeSheet.REVERSE_ROUNDING_EACH_TIMEZONE,
						new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN)));
		GoOutTypeRoundingSet typeDefault = new GoOutTypeRoundingSet(deductDefault, deductDefault);
		domain.diffTimezoneSetting = new GoOutTimezoneRoundingSet(typeDefault, typeDefault, typeDefault);
		return domain;
	}
}
