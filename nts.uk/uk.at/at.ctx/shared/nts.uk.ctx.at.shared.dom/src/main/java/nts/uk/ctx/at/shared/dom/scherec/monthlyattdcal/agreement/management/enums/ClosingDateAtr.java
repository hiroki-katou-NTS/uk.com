package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums;

/**
 * 
 * @author nampt
 *
 */
public enum ClosingDateAtr {
	
	/*
	 * ３６協定締め日区分
	 */
	// 0: 勤怠の締め日と同じ
	SAMEDATE(0),
	// 1: 締め日を指定
	DESIGNATEDATE(1);

	public final int value;
	
	private ClosingDateAtr(int type) {
		this.value = type;
	}

}
