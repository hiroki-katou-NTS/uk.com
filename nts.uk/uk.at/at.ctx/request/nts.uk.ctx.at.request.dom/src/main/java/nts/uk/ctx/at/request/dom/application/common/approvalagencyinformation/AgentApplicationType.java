package nts.uk.ctx.at.request.dom.application.common.approvalagencyinformation;

public enum AgentApplicationType {
	/**
	 *  0:代行者指定
	 */
	SUBSTITUTE_DESIGNATION(0),
	/**
	 *  1:パス
	 */
	PATH(1),
	/**
	 *  2:設定なし
	 */
	NO_SETTINGS(2);
	
	public final int value;
	
	private AgentApplicationType(int value){
		this.value = value;
	}
}
