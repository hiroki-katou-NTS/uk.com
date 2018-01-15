package nts.uk.ctx.sys.auth.dom.user;

public enum DisabledSegment {

	True(1), // あり
	False(0); // なし

	public int value;

	private DisabledSegment(int type) {
		this.value = type;
	}
}
