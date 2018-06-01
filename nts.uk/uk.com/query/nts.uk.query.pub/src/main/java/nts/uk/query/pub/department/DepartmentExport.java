/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.pub.department;

import lombok.Builder;
import lombok.Data;

/**
 * The Class DepartmentExport.
 */
//所属部門
@Data
@Builder
public class DepartmentExport {

	/** The department code. */
	private String departmentCode; // 部門コード
	
	/** The department name. */
	private String departmentName; // 部門表示名
	
	/** The department generic name. */
	private String departmentGenericName; // 部門総称
}
