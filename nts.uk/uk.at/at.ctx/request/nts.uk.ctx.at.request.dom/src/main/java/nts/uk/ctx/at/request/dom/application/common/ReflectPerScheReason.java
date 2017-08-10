package nts.uk.ctx.at.request.dom.application.common;

public enum ReflectPerScheReason {
	/**
	 * 問題なし
	 */
	NotProblem(0),
	/**
	 * 実績確定済
	 */
	ActualConfirmed(1);
	public int value;
	
	ReflectPerScheReason(int type){
		this.value =type;
	}
}
