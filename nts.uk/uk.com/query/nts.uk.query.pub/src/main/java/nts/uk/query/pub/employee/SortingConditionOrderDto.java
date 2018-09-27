/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.pub.employee;

import lombok.Data;

/**
 * The Class SortingConditionOrderDto.
 */
// 規定の並び替え条件順番
@Data
public class SortingConditionOrderDto {
	
	/** The order. */
	private int order;
	
	/** The type. */
	private RegularSortingType type;

	public SortingConditionOrderDto() {
		super();
	}
	
	public SortingConditionOrderDto(int order, RegularSortingType type) {
		super();
		this.order = order;
		this.type = type;
	}
}
