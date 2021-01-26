package nts.uk.ctx.exio.dom.exi.execlog;

import lombok.AllArgsConstructor;
/**
 * 受入モード
 * @author do_dt
 *
 */
@AllArgsConstructor
public enum CsvAcceptMode {
	/**	新規 */
	INSERT(0),
	/**	上書き */
	REPLACE(1),
	/** 新規／上書き	 */
	INSERT_OR_REPLACE(2);
	
	public final Integer value;
}
