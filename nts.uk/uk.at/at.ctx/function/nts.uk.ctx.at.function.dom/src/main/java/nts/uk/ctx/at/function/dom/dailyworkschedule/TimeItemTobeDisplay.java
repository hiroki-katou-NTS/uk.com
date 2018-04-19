/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.dom.dailyworkschedule;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class TimeitemTobeDisplay.
 */
// 表示する勤怠項目
@Getter
public class TimeItemTobeDisplay extends DomainObject{
	
	/** The sort by. */
	// 並び順
	private int sortBy;
	
	/** The item to display. */
	// 表示する項目
	private int itemToDisplay;

	/**
	 * Instantiates a new timeitem tobe display.
	 *
	 * @param sortBy the sort by
	 * @param itemToDisplay the item to display
	 */
	public TimeItemTobeDisplay(int sortBy, int itemToDisplay) {
		super();
		this.sortBy = sortBy;
		this.itemToDisplay = itemToDisplay;
	}
}
