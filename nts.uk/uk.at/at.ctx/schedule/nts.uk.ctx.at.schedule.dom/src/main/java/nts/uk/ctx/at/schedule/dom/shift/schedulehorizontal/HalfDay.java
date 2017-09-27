package nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum HalfDay {
	/** 半日カウント区分 */
	/**0:しない。*/
	NotUse(0),
	/** 1：する */
	Use(1);
	public final int value;
}
