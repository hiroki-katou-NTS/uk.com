package nts.uk.ctx.at.shared.dom.category;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum HeavyHd {
	/** 積休カウント区分 */
	/**0:しない。*/
	NotUse(0),
	/** 1：する */
	Use(1);
	public final int value;
}
