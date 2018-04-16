package nts.uk.ctx.at.shared.dom.yearholidaygrant.service;

/**
 * 終了状態
 * @author tanlv
 *
 */
public enum StatusResult {
	/** 期間より前 */
	BEFORE_PERIOD(0),
	/** 期間内 */
	WITHIN_PERIOD(1),
	/** 期間より後 */
	AFTER_PERIOD(2);
	
	public final int value;
	
	StatusResult(int value) {
		this.value = value;
	}
}
