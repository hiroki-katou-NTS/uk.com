package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.log;
/**
 * 了解フラグ
 * @author ThanhNX
 *
 */
public enum RogerFlag {
	/** 未読 */
	UNREAD(0),
	/** 了解 */
	ALREADY_READ(1);
	public final int value;
	private RogerFlag(int type){
		this.value = type;
	}
}
