/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.pub.classification;

import lombok.Builder;
import lombok.Data;

/**
 * The Class ClassificationExport.
 */
// 所属分類
@Data
@Builder
public class ClassificationExport {

	/** The classification code. */
	private String classificationCode; // 分類コード
	
	/** The classification name. */
	private String classificationName; // 分類名称
}
