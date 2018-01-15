package nts.uk.ctx.at.request.dom.application;

public enum ReflectPlanScheReason {
	/**
	 * 問題なし
	 */
	NOTPROBLEM(0),
	/**
	 * 勤務スケジュール確定済
	 */
	WORKFIXED(1),
	/**
	 * 作業スケジュール確定済
	 */
	WORKCONFIRMED(2);
	
	public int value;
	
	ReflectPlanScheReason(int type){
		this.value =type;
	}
	
}
