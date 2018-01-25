/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeDomainObject;

/**
 * The Class FlowFixedRestSet.
 */
//流動固定休憩設定
@Getter 
public class FlowFixedRestSet extends WorkTimeDomainObject {
	
	/** The is refer rest time. */
	//休憩時刻がない場合はマスタから休憩時刻を参照する
	private boolean isReferRestTime;
	
	/** The use private go out rest. */
	//私用外出を休憩として扱う
	private boolean usePrivateGoOutRest;
	
	/** The use asso go out rest. */
	//組合外出を休憩として扱う
	private boolean useAssoGoOutRest;
	
	/** The calculate method. */
	//計算方法
	private FlowFixedRestCalcMethod calculateMethod;

	/**
	 * Constructor
	 * @param isReferRestTime the is refer rest time
	 * @param usePrivateGoOutRest the use private go out rest
	 * @param useAssoGoOutRest the use asso go out rest
	 * @param calculateMethod the calculate method
	 */
	public FlowFixedRestSet(boolean isReferRestTime, boolean usePrivateGoOutRest, boolean useAssoGoOutRest,
			FlowFixedRestCalcMethod calculateMethod) {
		super();
		this.isReferRestTime = isReferRestTime;
		this.usePrivateGoOutRest = usePrivateGoOutRest;
		this.useAssoGoOutRest = useAssoGoOutRest;
		this.calculateMethod = calculateMethod;
	}
	/**
	 * Instantiates a new flow fixed rest set.
	 *
	 * @param memento the memento
	 */
	public FlowFixedRestSet (FlowFixedRestSetGetMemento memento) {
		this.isReferRestTime = memento.getIsReferRestTime();
		this.usePrivateGoOutRest = memento.getUsePrivateGoOutRest();
		this.useAssoGoOutRest = memento.getUseAssoGoOutRest();
		this.calculateMethod = memento.getCalculateMethod();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(FlowFixedRestSetSetMemento memento) {
		memento.setIsReferRestTime(this.isReferRestTime);
		memento.setUsePrivateGoOutRest(this.usePrivateGoOutRest);
		memento.setUseAssoGoOutRest(this.useAssoGoOutRest);
		memento.setCalculateMethod(this.calculateMethod);
	}


}
