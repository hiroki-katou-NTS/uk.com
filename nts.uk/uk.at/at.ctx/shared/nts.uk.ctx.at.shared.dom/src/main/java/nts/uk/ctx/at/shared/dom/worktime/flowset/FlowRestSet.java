/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;
import nts.uk.ctx.at.shared.dom.worktime.common.RestClockManageAtr;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeDomainObject;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class FlowRestSet.
 */
//流動休憩設定
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FlowRestSet extends WorkTimeDomainObject implements Cloneable{

	/** The use stamp. */
	//打刻を併用する
	private boolean useStamp;
	
	/** The use stamp calc method. */
	//打刻併用時の計算方法
	private FlowRestClockCalcMethod useStampCalcMethod;
	
	/** The time manager set atr. */
	//時刻管理設定区分
	private RestClockManageAtr 	timeManagerSetAtr;
	
	/** The calculate method. */
	//計算方法
//	private FlowRestCalcMethod calculateMethod;

	/**
	 * Instantiates a new flow rest set.
	 *
	 * @param memento the memento
	 */
	public FlowRestSet (FlowRestSetGetMemento memento) {
		this.useStamp = memento.getUseStamp();
		this.useStampCalcMethod = memento.getUseStampCalcMethod();
		this.timeManagerSetAtr = memento.getTimeManagerSetAtr();
//		this.calculateMethod = memento.getCalculateMethod();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(FlowRestSetSetMemento memento) {
		memento.setUseStamp(this.useStamp);
		memento.setUseStampCalcMethod(this.useStampCalcMethod);
		memento.setTimeManagerSetAtr(this.timeManagerSetAtr);
//		memento.setCalculateMethod(this.calculateMethod);
	}
	
	@Override
	public FlowRestSet clone() {
		FlowRestSet cloned = new FlowRestSet();
		try {
			cloned.useStamp = this.useStamp ? true : false;
			cloned.useStampCalcMethod = FlowRestClockCalcMethod.valueOf(this.useStampCalcMethod.value);
			cloned.timeManagerSetAtr = RestClockManageAtr.valueOf(this.timeManagerSetAtr.value);
//			cloned.calculateMethod = FlowRestCalcMethod.valueOf(this.calculateMethod.value);
		}
		catch (Exception e){
			throw new RuntimeException("FlowRestSet clone error.");
		}
		return cloned;
	}
	
	/**
	 * 外出から休憩へ変換するか
	 * @param reason 外出理由
	 * @return true：変換する false：変換しない
	 */
	public boolean isConvertGoOutToBreak(GoingOutReason reason) {
		if(!this.useStamp) {
			return false; //外出を休憩として扱わない
		}
		if(!this.useStampCalcMethod.isRestTimeToCalculate()) {
			return false; //外出として計上する
		}
		if(!reason.isPrivateOrUnion()) {
			return false; //私用組合以外
		}
		return true;
	}
	
	/**
	 * 休憩前の外出か
	 * @param goOutStart 外出開始
	 * @param breakStart 休憩開始
	 * @return true:休憩前の外出である false:休憩前の外出ではない
	 */
	public boolean isGoOutBeforeBreak(TimeWithDayAttr goOutStart, TimeWithDayAttr breakStart) {
		if(this.timeManagerSetAtr.isClockManage()) {
			//休憩を優先する場合に、外出開始と休憩開始が同じ時刻だったら休憩前の外出として取得したくない為
			return goOutStart.lessThan(breakStart);
		}
		return goOutStart.lessThanOrEqualTo(breakStart);
	}
}
