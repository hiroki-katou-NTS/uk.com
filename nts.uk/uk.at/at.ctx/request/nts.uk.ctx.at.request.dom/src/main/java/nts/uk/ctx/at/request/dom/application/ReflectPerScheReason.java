package nts.uk.ctx.at.request.dom.application;

public enum ReflectPerScheReason {
	/**
	 * 問題なし
	 */
	NOTPROBLEM(0),
	/**
	 * 実績確定済
	 */
	ACTUALCONFIRMED(1);
	public int value;
	
	ReflectPerScheReason(int type){
		this.value =type;
	}
}
