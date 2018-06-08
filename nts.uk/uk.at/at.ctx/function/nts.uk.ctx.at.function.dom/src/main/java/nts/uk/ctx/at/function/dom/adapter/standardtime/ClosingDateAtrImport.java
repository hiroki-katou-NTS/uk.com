package nts.uk.ctx.at.function.dom.adapter.standardtime;

/**
 * @author dat.lh
 *
 */
public enum ClosingDateAtrImport {
	/*
	 * ３６協定締め日区分
	 */
	// 0: 勤怠の締め日と同じ
	SAMEDATE(0),
	// 1: 締め日を指定
	DESIGNATEDATE(1);

	public final int value;
	
	private ClosingDateAtrImport(int type) {
		this.value = type;
	}
}
