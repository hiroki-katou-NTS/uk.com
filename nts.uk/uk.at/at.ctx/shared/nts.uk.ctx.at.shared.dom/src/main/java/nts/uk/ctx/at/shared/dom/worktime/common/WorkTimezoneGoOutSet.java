/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.timerounding.Rounding;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.ActualWorkTimeSheetAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.ConditionAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.DeductionAtr;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeDomainObject;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.ScreenMode;

/**
 * The Class WorkTimezoneGoOutSet.
 */
//就業時間帯の外出設定
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WorkTimezoneGoOutSet extends WorkTimeDomainObject implements Cloneable{

	/** 丸め方法 */
	private GoOutTimeRoundingMethod roundingMethod;
	
	/** 時間帯別設定 */
	private GoOutTimezoneRoundingSet diffTimezoneSetting;

	/**
	 * Instantiates a new work timezone go out set.
	 *
	 * @param memento
	 *            the memento
	 */
	public WorkTimezoneGoOutSet(WorkTimezoneGoOutSetGetMemento memento) {
		this.roundingMethod = memento.getRoundingMethod();
		this.diffTimezoneSetting = memento.getDiffTimezoneSetting();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(WorkTimezoneGoOutSetSetMemento memento) {
		memento.setRoundingMethod(this.roundingMethod);
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
			this.roundingMethod = GoOutTimeRoundingMethod.IN_FRAME;
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
			this.roundingMethod = GoOutTimeRoundingMethod.IN_FRAME;
		}

		// Go deeper
		this.diffTimezoneSetting.correctDefaultData(screenMode);
	}
	
	@Override
	public WorkTimezoneGoOutSet clone() {
		WorkTimezoneGoOutSet cloned = new WorkTimezoneGoOutSet();
		try {
			cloned.roundingMethod = GoOutTimeRoundingMethod.valueOf(this.roundingMethod.value);
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
		domain.roundingMethod = GoOutTimeRoundingMethod.IN_FRAME;
		
		DeductGoOutRoundingSet deductDefault = new DeductGoOutRoundingSet(
				new GoOutTimeRoundingSetting(RoundingGoOutTimeSheet.REVERSE_ROUNDING_EACH_TIMEZONE,
						new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN)),
				new GoOutTimeRoundingSetting(RoundingGoOutTimeSheet.REVERSE_ROUNDING_EACH_TIMEZONE,
						new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN)));
		GoOutTypeRoundingSet typeDefault = new GoOutTypeRoundingSet(deductDefault, deductDefault);
		domain.diffTimezoneSetting = new GoOutTimezoneRoundingSet(typeDefault, typeDefault, typeDefault);
		return domain;
	}

	/**
	 * 「実働時間帯の枠ごとに合計せず丸める」の丸め設定を取得する
	 * @param actualAtr 実働時間帯区分
	 * @param reason 外出理由
	 * @param dedAtr 控除区分
	 * @param reverse 逆丸め用
	 * @return 丸め設定
	 */
	public Optional<TimeRoundingSetting> getInFrame(ActualWorkTimeSheetAtr actualAtr, GoingOutReason reason, DeductionAtr dedAtr, TimeRoundingSetting reverse) {
		if(this.roundingMethod.isInFrame()) {
			return Optional.of(this.diffTimezoneSetting.getRoundingSet(actualAtr, reason, dedAtr, reverse));
		}
		return Optional.of(TimeRoundingSetting.ONE_MIN_DOWN);
	}
	
	/**
	 * 「実働時間帯の枠ごとに合計してから丸める」の丸め設定を取得する
	 * @param actualAtr 実働時間帯区分
	 * @param conditionAtr 控除種別区分
	 * @param dedAtr 控除区分
	 * @param reverse 逆丸め用
	 * @return 丸め設定
	 */
	public Optional<TimeRoundingSetting> getAfterTotalInFrame(ActualWorkTimeSheetAtr actualAtr, ConditionAtr conditionAtr, DeductionAtr dedAtr, TimeRoundingSetting reverse) {
		Optional<GoingOutReason> reason = conditionAtr.toGoingOutReason();
		if(!reason.isPresent()) {
			return Optional.empty();
		}
		if(this.roundingMethod.isAfterTotalInFrame()) {
			return Optional.of(this.diffTimezoneSetting.getRoundingSet(actualAtr, reason.get(), dedAtr, reverse));
		}
		return Optional.of(TimeRoundingSetting.ONE_MIN_DOWN);
	}

	/**
	 * 「実働時間帯ごとに合計して丸める」の丸め設定を取得する
	 * @param actualAtr 実働時間帯区分
	 * @param conditionAtr 控除種別区分
	 * @param dedAtr 控除区分
	 * @return 丸め設定
	 */
	public Optional<TimeRoundingSetting> getAfterTotal(ActualWorkTimeSheetAtr actualAtr, ConditionAtr conditionAtr, DeductionAtr dedAtr) {
		Optional<GoingOutReason> reason = conditionAtr.toGoingOutReason();
		if(!reason.isPresent()) {
			return Optional.empty();
		}
		if(dedAtr.isDeduction()) {
			return Optional.of(TimeRoundingSetting.ONE_MIN_DOWN);
		}
		if(this.roundingMethod.isAfterTotal()) {
			return Optional.of(this.diffTimezoneSetting.getRoundingSet(actualAtr, reason.get(), dedAtr, TimeRoundingSetting.ONE_MIN_DOWN));
		}
		return Optional.of(TimeRoundingSetting.ONE_MIN_DOWN);
	}
}
