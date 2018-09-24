package nts.uk.ctx.at.request.dom.applicationreflect.service;

import lombok.AllArgsConstructor;

/**
 * 実行区分
 * @author do_dt
 *
 */
@AllArgsConstructor
public enum WorkReflectAtr {
	/**	手動 */
	MANUAL(0),
	/**	自動 */
	AUTOMATIC(1);
	public int value;
}
