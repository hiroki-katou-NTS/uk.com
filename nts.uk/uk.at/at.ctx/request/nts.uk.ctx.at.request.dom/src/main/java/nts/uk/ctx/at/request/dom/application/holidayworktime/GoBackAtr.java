package nts.uk.ctx.at.request.dom.application.holidayworktime;

public enum GoBackAtr {
	/**
	 * しない
	 */
	NOTUSER(0),
	/**
	 * する
	 */
	USER(1);
	
	public final int value;
	
	GoBackAtr(int value){
		this.value = value;
	}
	
}
