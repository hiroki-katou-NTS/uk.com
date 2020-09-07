package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.toppagealarm;
/**
 * 中止フラグ
 * @author chungnt
 *
 */
public enum IsCancelled {
	/** 未読 */
	NOT_CANCELLED(0),
	/** 了解 */
	CANCELLED(1);
	public final int value;
	private IsCancelled(int type){
		this.value = type;
	}
}
