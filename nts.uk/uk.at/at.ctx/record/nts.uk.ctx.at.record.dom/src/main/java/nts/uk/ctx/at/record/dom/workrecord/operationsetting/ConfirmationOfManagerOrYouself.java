package nts.uk.ctx.at.record.dom.workrecord.operationsetting;

public enum ConfirmationOfManagerOrYouself {
	
	CAN_CHECK_WHEN_ERROR(0),
	
	CANNOT_CHECKED_WHEN_ERROR(1), 
	
	CANNOT_REGISTER_WHEN_ERROR(2);
	
	public final int value;
	
	private ConfirmationOfManagerOrYouself(int value) {
		this.value = value;
	}

}
