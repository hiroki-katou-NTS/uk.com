package nts.uk.ctx.at.record.dom.workrecord.errorsetting;

public enum CheckState {

	// 包含
	INCLUSION(0),

	// 非包含
	NON_INCLUSION(1);
	
	public int value;

	private CheckState(int value) {
		this.value = value;
	}
}
