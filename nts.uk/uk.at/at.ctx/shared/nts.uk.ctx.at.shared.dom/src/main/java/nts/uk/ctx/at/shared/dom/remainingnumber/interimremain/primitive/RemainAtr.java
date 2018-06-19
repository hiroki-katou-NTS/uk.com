package nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive;
/**
 * 残数分類
 * @author do_dt
 *
 */

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum RemainAtr {
	/**
	 * 単一
	 */
	SINGLE(0),
	/**
	 * 複合
	 */
	COMPOSITE(1);
	public final Integer value;
}
