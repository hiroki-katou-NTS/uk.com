package nts.uk.ctx.at.record.dom.workrecord.operationsetting.old;

public enum ConfirmOfManagerOrYouself {
	
	CAN_CHECK_WHEN_ERROR(0),
	
	CANNOT_CHECKED_WHEN_ERROR(1), 
	
	CANNOT_REGISTER_WHEN_ERROR(2);
	
	public final int value;
	
	private ConfirmOfManagerOrYouself(int value) {
		this.value = value;
	}

}
