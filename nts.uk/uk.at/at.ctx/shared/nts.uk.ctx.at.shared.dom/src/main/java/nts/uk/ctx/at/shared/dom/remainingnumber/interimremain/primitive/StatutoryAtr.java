package nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive;

import lombok.AllArgsConstructor;
/**
 * 休日区分
 * @author do_dt
 *
 */
@AllArgsConstructor
public enum StatutoryAtr {
	/**
	 * 法定内休日
	 */
	STATUTORY(0),
	/**
	 * 法定外休日
	 */
	NONSTATURORY(1),
	/**
	 * 祝日
	 */
	PUBLIC(2);
	public final Integer value;
}
