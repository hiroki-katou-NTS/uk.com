package nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal;

import lombok.AllArgsConstructor;
/**
 * 年休カウント区分
 * @author yennth
 *
 */
@AllArgsConstructor
public enum YearHd {
	/**0:しない。*/
	NotUse(0),
	/** 1：する */
	Use(1);
	public final int value;
}
