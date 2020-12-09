package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal;

/**
 * 
 * @author dungbn
 *	端末の現在の状態
 *
 */
public enum TerminalCurrentState {
	// 正常
	NORMAL(0),
	// 異常
	ABNORMAL(1),
	// 未通信
	NOT_COMMUNICATED(2);
	
	public final int value;
	
	private TerminalCurrentState(int value) {
		this.value = value;
	}
}
