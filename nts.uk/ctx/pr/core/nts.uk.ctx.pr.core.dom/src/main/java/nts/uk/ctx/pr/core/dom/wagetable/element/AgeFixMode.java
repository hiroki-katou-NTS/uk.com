/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.element;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.pr.core.dom.wagetable.ElementType;

/**
 * The Class AgeFixMode.
 */
@Getter
public class AgeFixMode implements ElementMode {

	/** The Constant mode. */
	public static final ElementType mode = ElementType.AgeFix;

	/** The upper limit. */
	// Demension
	private Integer upperLimit;

	/** The lower limit. */
	private Integer lowerLimit;

	/** The interval. */
	private Integer interval;
	
	/** The items. */
	private List<RangeItem> items;
	
	/**
	 * Instantiates a new age fix mode.
	 *
	 * @param lowerLimit the lower limit
	 * @param upperLimit the upper limit
	 * @param interval the interval
	 */
	public AgeFixMode(Integer lowerLimit, Integer upperLimit, Integer interval) {
		super();
		this.lowerLimit = lowerLimit;
		this.upperLimit = upperLimit;
		this.interval = interval;

		List<RangeItem> items = new ArrayList<>();

		int index = 0;
		int start = lowerLimit;
		while (start <= upperLimit) {
			index++;
			int end = start + interval - 1;

			items.add(new RangeItem(index, start, ((end <= upperLimit) ? end : upperLimit),
					IdentifierUtil.randomUniqueId()));

			start = start + interval;
		}

		this.items = items;
	}

}
