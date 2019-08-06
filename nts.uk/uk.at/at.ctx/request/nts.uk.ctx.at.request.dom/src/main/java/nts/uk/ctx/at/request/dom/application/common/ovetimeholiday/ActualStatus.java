package nts.uk.ctx.at.request.dom.application.common.ovetimeholiday;
/**
 * 実績状態
 * @author Doan Duy Hung
 *
 */
public enum ActualStatus {
	/**
	 * 実績あり
	 */
	ACTUAL(0, "実績あり"),
	/**
	 * 打刻漏れ
	 */
	STAMP_MISS(1,"打刻漏れ"),
	/**
	 * 打刻漏れ(補正済)
	 */
	FIXED_STAMP_MISS(2,"打刻漏れ(補正済)"),
	/**
	 * 実績なし
	 */
	NO_ACTUAL(3, "実績なし");
	 
	public int value;
	
	public String name;
	ActualStatus(int type,String name){
		this.value = type;
		this.name = name;
	}
}
