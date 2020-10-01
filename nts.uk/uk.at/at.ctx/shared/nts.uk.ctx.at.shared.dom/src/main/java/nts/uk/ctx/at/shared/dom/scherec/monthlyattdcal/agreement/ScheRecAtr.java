package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement;

/**
 * 予実区分
 * @author shuichi_ishida
 */
public enum ScheRecAtr {
	/** 予定 */
	SCHEDULE(0),
	/** 実績 */
	RECORD(1);
	
	public int value;
	private ScheRecAtr(int value){
		this.value = value;
	}
}
