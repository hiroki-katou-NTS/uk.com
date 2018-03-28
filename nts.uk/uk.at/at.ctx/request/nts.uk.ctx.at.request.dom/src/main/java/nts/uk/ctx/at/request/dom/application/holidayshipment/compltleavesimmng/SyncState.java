package nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng;

public enum SyncState {

	/**
	 * 非同期
	 */
	ASYNCHRONOUS(0),
	/**
	 * 同期中
	 */
	SYNCHRONIZING(1);

	public int value;

	SyncState(int value) {
		this.value = value;
	}

}
