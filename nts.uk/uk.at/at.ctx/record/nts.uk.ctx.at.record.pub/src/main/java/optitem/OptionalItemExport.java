/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package optitem;

import lombok.Builder;
import lombok.Getter;

/**
 * The Class OptionalItemExport.
 */

@Builder
@Getter
public class OptionalItemExport {

	/** The optional item no. */
	private int optionalItemNo;

	/** The optional item name. */
	private String optionalItemName;
	
	private String optionalItemUnit;
}
