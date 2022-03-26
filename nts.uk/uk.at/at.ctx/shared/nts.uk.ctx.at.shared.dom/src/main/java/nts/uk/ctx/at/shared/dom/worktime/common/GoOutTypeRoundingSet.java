/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.DeductionAtr;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeDomainObject;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.ScreenMode;

/**
 * The Class GoOutTypeRoundingSet.
 */
// 外出種類別丸め設定
@Getter
@NoArgsConstructor
public class GoOutTypeRoundingSet extends WorkTimeDomainObject implements Cloneable{

	/** The offical use compen go out. */
	// 公用、有償外出
	private DeductGoOutRoundingSet officalUseCompenGoOut;

	/** The private union go out. */
	// 私用、組合外出
	private DeductGoOutRoundingSet privateUnionGoOut;

	/**
	 * Instantiates a new go out type rounding set.
	 *
	 * @param officalUseCompenGoOut
	 *            the offical use compen go out
	 * @param privateUnionGoOut
	 *            the private union go out
	 */
	public GoOutTypeRoundingSet(DeductGoOutRoundingSet officalUseCompenGoOut,
			DeductGoOutRoundingSet privateUnionGoOut) {
		super();
		this.officalUseCompenGoOut = officalUseCompenGoOut;
		this.privateUnionGoOut = privateUnionGoOut;
	}

	/**
	 * Instantiates a new go out type rounding set.
	 *
	 * @param memento
	 *            the memento
	 */
	public GoOutTypeRoundingSet(GoOutTypeRoundingSetGetMemento memento) {
		this.officalUseCompenGoOut = memento.getOfficalUseCompenGoOut();
		this.privateUnionGoOut = memento.getPrivateUnionGoOut();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(GoOutTypeRoundingSetSetMemento memento) {
		memento.setOfficalUseCompenGoOut(this.officalUseCompenGoOut);
		memento.setPrivateUnionGoOut(this.privateUnionGoOut);
	}

	/**
	 * Correct data.
	 *
	 * @param screenMode
	 *            the screen mode
	 * @param oldDomain
	 *            the old domain
	 */
	public void correctData(ScreenMode screenMode, GoOutTypeRoundingSet oldDomain) {
		this.officalUseCompenGoOut.correctData(screenMode, oldDomain.getOfficalUseCompenGoOut());
		this.privateUnionGoOut.correctData(screenMode, oldDomain.getPrivateUnionGoOut());
	}

	/**
	 * Correct default data.
	 *
	 * @param screenMode
	 *            the screen mode
	 */
	public void correctDefaultData(ScreenMode screenMode) {
		this.officalUseCompenGoOut.correctDefaultData(screenMode);
		this.privateUnionGoOut.correctDefaultData(screenMode);
	}

	@Override
	public GoOutTypeRoundingSet clone() {
		GoOutTypeRoundingSet cloned = new GoOutTypeRoundingSet();
		try {
			cloned.officalUseCompenGoOut = this.officalUseCompenGoOut.clone();
			cloned.privateUnionGoOut = this.privateUnionGoOut.clone();
		}
		catch (Exception e){
			throw new RuntimeException("AggregateTotalTimeSpentAtWork clone error.");
		}
		return cloned;
	}

	/**
	 * 丸め設定を取得する
	 * @param reason 外出理由
	 * @param deductAtr 控除区分
	 * @param reverse 逆丸め用
	 * @return 時間丸め設定
	 */
	public TimeRoundingSetting getRoundingSet(GoingOutReason reason, DeductionAtr deductAtr, TimeRoundingSetting reverse) {
		if(reason.isPublicOrCmpensation()) {
			return this.officalUseCompenGoOut.getRoundingSet(deductAtr, reverse);
		}
		return this.privateUnionGoOut.getRoundingSet(deductAtr, reverse);
	}
}
