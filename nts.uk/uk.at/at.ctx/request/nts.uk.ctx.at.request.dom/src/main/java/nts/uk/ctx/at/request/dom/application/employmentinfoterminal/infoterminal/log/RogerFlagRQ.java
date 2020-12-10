package nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.log;
/**
 * 了解フラグ
 * @author ThanhNX
 *
 */
public enum RogerFlagRQ {
	/** 未読 */
	UNREAD(0),
	/** 了解 */
	ALREADY_READ(1);
	public final int value;
	private RogerFlagRQ(int type){
		this.value = type;
	}
}
