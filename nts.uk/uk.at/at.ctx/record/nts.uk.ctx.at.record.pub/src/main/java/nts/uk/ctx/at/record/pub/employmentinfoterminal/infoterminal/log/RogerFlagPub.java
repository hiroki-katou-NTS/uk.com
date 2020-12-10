package nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.log;
/**
 * 了解フラグ
 * @author ThanhNX
 *
 */
public enum RogerFlagPub {
	/** 未読 */
	UNREAD(0),
	/** 了解 */
	ALREADY_READ(1);
	public final int value;
	private RogerFlagPub(int type){
		this.value = type;
	}
}
