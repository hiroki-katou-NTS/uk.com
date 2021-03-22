package nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.log;
/**
 * 中止フラグ
 * @author ThanhNX
 *
 */
public enum IsCancelledRQ {
	/** 未読 */
	NOT_CANCELLED(0),
	/** 了解 */
	CANCELLED(1);
	public final int value;
	private IsCancelledRQ(int type){
		this.value = type;
	}
}
