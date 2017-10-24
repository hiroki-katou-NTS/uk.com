package nts.uk.ctx.at.request.dom.application;

public enum UseAtr {

	/**
	 * 使用しない
	 */
	NOTUSE(0),
	/**
	 * 使用する
	 */
	USE(1);
	
	public int value;
	
	UseAtr(int type){
		this.value = type;
	}
	
}
