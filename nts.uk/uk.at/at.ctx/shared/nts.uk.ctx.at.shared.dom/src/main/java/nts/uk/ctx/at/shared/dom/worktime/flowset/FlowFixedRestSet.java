/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeDomainObject;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.ScreenMode;

/**
 * The Class FlowFixedRestSet.
 */
//流動固定休憩設定
@Getter
@NoArgsConstructor
public class FlowFixedRestSet extends WorkTimeDomainObject implements Cloneable{

	/** The calculate method. */
	// 計算方法
	private FlowFixedRestCalcMethod calculateMethod;

	/** The calculate from stamp. */
	// 打刻から休憩を計算
	private StampBreakCalculation calculateFromStamp;

	/**
	 * Instantiates a new flow fixed rest set.
	 *
	 * @param isReferRestTime
	 *            the is refer rest time
	 * @param usePrivateGoOutRest
	 *            the use private go out rest
	 * @param useAssoGoOutRest
	 *            the use asso go out rest
	 * @param calculateMethod
	 *            the calculate method
	 */
	public FlowFixedRestSet(boolean usePrivateGoOutRest, boolean useAssoGoOutRest,
			FlowFixedRestCalcMethod calculateMethod) {
		super();
		this.calculateMethod = calculateMethod;
		this.calculateFromStamp = new StampBreakCalculation(usePrivateGoOutRest, useAssoGoOutRest);
	}

	/**
	 * Instantiates a new flow fixed rest set.
	 *
	 * @param memento
	 *            the memento
	 */
	public FlowFixedRestSet(FlowFixedRestSetGetMemento memento) {
		this.calculateMethod = memento.getCalculateMethod();
		this.calculateFromStamp = memento.getCalculateFromStamp();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(FlowFixedRestSetSetMemento memento) {
		memento.setCalculateMethod(this.calculateMethod);
		memento.setCalculateFromStamp(this.calculateFromStamp);
	}

	/**
	 * Checks if is use private go out rest.
	 *
	 * @return true, if is use private go out rest
	 */
	public boolean isUsePrivateGoOutRest() {
		return this.getCalculateFromStamp().isUsePrivateGoOutRest();
	}

	/**
	 * Checks if is use asso go out rest.
	 *
	 * @return true, if is use asso go out rest
	 */
	public boolean isUseAssoGoOutRest() {
		return this.getCalculateFromStamp().isUseAssoGoOutRest();
	}

	public void correctData(ScreenMode screenMode, FlowFixedRestSet flowFixedRestSetting,boolean fixRestTime) {
		if (ScreenMode.DETAIL.equals(screenMode) && fixRestTime) {
			switch (this.calculateMethod) {
			case REFER_MASTER:
				this.calculateFromStamp = flowFixedRestSetting.getCalculateFromStamp();
				break;
			default:
				break;
			}
		}
	}

	public void correctDefaultData(ScreenMode screenMode,boolean fixRestTime) {
		if (ScreenMode.DETAIL.equals(screenMode) && fixRestTime) {
			switch (this.calculateMethod) {
			case REFER_MASTER:
				this.calculateFromStamp.setDefaultValue();
				break;
			default:
				break;
			}
		}
	}
	
	@Override
	public FlowFixedRestSet clone() {
		FlowFixedRestSet cloned = new FlowFixedRestSet();
		try {
			cloned.calculateMethod = FlowFixedRestCalcMethod.valueOf(this.calculateMethod.value);
			cloned.calculateFromStamp = this.calculateFromStamp.clone();
		}
		catch (Exception e){
			throw new RuntimeException("FlowFixedRestSet clone error.");
		}
		return cloned;
	}
}
