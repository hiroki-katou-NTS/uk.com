package nts.uk.ctx.at.request.dom.application.common.ovetimeholiday;

public enum PreActualError {
	/**
	 * 
	 */
	NO_ERROR(0),
	/**
	 * 計算値	
	 */
	CALC_ERROR(3),
	/**
	 * 実績アラーム	
	 */
	ACTUAL_ALARM(2),
	/**
	 * 実績エラー	
	 */
	ACTUAL_ERROR(1);
	
	public int value;
	
	PreActualError(int type){
		this.value = type;
	}
}
