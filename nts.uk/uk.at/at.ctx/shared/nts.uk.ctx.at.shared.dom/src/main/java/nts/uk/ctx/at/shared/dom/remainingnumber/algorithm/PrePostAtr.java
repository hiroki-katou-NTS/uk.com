package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm;

import lombok.AllArgsConstructor;
/**
 * 事前事後区分
 * @author do_dt
 *
 */
@AllArgsConstructor
public enum PrePostAtr {
	/**
	 * 0: 事前の受付制限
	 */
	PREDICT(0),
	/**
	 * 1: 事後の受付制限
	 */
	POSTERIOR(1),
	
	/**
	 * 2: 選択なし
	 */
	NONE(2);
	
	public final int value;
}
