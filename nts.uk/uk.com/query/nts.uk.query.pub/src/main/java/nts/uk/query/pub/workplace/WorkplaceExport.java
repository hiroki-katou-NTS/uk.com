/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.pub.workplace;

import lombok.Builder;
import lombok.Data;

/**
 * The Class WorkplaceExport.
 */
// 所属職場
@Builder
@Data
public class WorkplaceExport {

	/** The workplace id. */
	private String workplaceId; // 職場ID

	/** The workplace code. */
	private String workplaceCode; //職場コード
	
	/** The workplace generic name. */
	private String workplaceGenericName; //職場総称
	
	/** The workplace name. */
	private String workplaceName; //職場表示名
}
