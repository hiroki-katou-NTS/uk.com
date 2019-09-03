package nts.uk.ctx.at.request.dom.application.common.ovetimeholiday;

public enum PreActualError {
	/**
	 * 
	 */
	NO_ERROR(0),
	/**
	 * 計算値	
	 */
	CALC_ERROR(1),
	/**
	 * 実績アラーム	
	 */
	PRE_ERROR(2),
	/**
	 * 実績アラーム	
	 */
	ACTUAL_ALARM(3),
	/**
	 * 実績エラー	
	 */
	ACTUAL_ERROR(4);
	
	public int value;
	
	PreActualError(int type){
		this.value = type;
	}
}
