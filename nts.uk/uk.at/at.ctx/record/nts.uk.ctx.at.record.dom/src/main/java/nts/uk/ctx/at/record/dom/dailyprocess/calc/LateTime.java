package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import lombok.Value;

@Value
public class LateTime{
	private boolean test;
//	private ?? appropriateDeductionAtr;

	public boolean isTrue() {
		return this.test;
	}
	/**
	 * 遅刻時間の計算
	 */
	public void calcLateTime(){
	}
	
	/**
	 * 遅刻時間帯の補正
	 */
	public void lateTimeCorrection() {
	}
	
	/**
	 * 丸め設定の保持
	 */
	public void getRoundingSet(){
	}
	
//	public boolean isDeduction() {
//		return appropriateDeductionAtr.equal("控除");
//	}
	
	
}

