/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.model.employee;

import lombok.Data;

/**
 * The Class SortingConditionOrder.
 */
// 規定の並び替え条件順番
@Data
public class SortingConditionOrder {
	
	/** The order. */
	private int order;
	
	/** The type. */
	private RegularSortingType type;
}
