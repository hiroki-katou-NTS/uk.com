package nts.uk.ctx.at.request.dom.setting.request.application.workchange;

public enum UseAtr {
	/** 0: 使用しない*/
	NOT_USE(0),
	/** 1: 使用する */
    USE(1);
	
	public int value ; 
	
	UseAtr(int type) {
		this.value = type;
	}

}
