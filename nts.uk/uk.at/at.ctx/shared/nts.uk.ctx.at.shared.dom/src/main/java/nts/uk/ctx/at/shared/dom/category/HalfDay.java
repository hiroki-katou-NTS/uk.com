package nts.uk.ctx.at.shared.dom.category;

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
