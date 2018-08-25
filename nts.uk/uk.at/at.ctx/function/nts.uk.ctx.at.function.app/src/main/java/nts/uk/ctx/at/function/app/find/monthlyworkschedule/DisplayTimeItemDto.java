/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.app.find.monthlyworkschedule;

import java.util.Optional;

import lombok.Data;

/**
 * The Class DisplayTimeItemDto.
 */
@Data
public class DisplayTimeItemDto {
	
	/**
	 * Instantiates a new display time item dto.
	 *
	 * @param itemDaily2 the item daily 2
	 * @param displayOrder2 the display order 2
	 * @param columnWidthTable2 the column width table 2
	 */
	public DisplayTimeItemDto(int itemDaily2, int displayOrder2, Optional<Integer> columnWidthTable2) {
		this.itemDaily = itemDaily2;
		this.displayOrder = displayOrder2;
		this.columnWidthTable = columnWidthTable2.orElse(null);
	}

	/** The display order. */
	private Integer displayOrder;
	
	/** The item daily. */
	private Integer itemDaily;
	
	/** The column width table. */
	private Integer columnWidthTable;
}
