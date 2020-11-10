package nts.uk.ctx.at.request.dom.application;

public enum ApprovalDevice {
	
	PC(0),
	
	MOBILE(1);
	
	public int value;
	
	ApprovalDevice(int type){
		this.value = type;
	}
}
