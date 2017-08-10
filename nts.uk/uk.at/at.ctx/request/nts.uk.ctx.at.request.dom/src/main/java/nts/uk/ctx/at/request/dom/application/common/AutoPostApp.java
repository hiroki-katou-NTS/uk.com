package nts.uk.ctx.at.request.dom.application.common;

public enum AutoPostApp {

	/**
	 * 使用しない
	 */
	NotUse(0),
	/**
	 * 使用する
	 */
	Use(1);
	
	public int value;
	
	AutoPostApp(int type){
		this.value = type;
	}
	
}
