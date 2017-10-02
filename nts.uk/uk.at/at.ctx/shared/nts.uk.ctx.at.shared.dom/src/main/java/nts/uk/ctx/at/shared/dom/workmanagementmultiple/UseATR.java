package nts.uk.ctx.at.shared.dom.workmanagementmultiple;

public enum UseATR {
	/** 0: 使用しない*/
	notUse(0),
	/** 1: 使用する */
    use(1);
	
	public int value ; 
	
	UseATR(int type) {
		this.value = type;
	}

	
}
