package nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.log;
/**
 * 中止フラグ
 * @author ThanhNX
 *
 */
public enum IsCancelledPub {
	/** 未読 */
	NOT_CANCELLED(0),
	/** 了解 */
	CANCELLED(1);
	public final int value;
	private IsCancelledPub(int type){
		this.value = type;
	}
}
