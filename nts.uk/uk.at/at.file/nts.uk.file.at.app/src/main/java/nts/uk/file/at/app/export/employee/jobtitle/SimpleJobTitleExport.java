/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.at.app.export.employee.jobtitle;

import lombok.Builder;
import lombok.Data;

/**
 * The Class JobTitleExport.
 * @author HoangNDH
 */
// 職位
@Data
@Builder
public class SimpleJobTitleExport {

	// 職位ID
	private String jobTitleId;

	// 職位コード
	private String jobTitleCode;

	// 職位名称
	private String jobTitleName;

	// 序列
	private Integer disporder;

}
