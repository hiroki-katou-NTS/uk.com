/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.app.employee;

import lombok.Builder;
import lombok.Data;

/**
 * The Class RegulationInfoEmployeeDto.
 */
@Builder
@Data
public class RegulationInfoEmployeeDto {

	/** The employee id. */
	private String employeeId; // 社員ID

	/** The employee code. */
	private String employeeCode; // 社員コード

	/** The employee name. */
	private String employeeName; // 氏名
	/** The workplace code. */
	
	private String workplaceCode; // 職場の階層コード
	/** The workplace id. */
	private String workplaceId; // 職場の階層コード
	 
	/** The workplace name. */
	private String workplaceName; // 職場の階層コード

//	/** The affiliation code. */
//	private String affiliationCode; // 所属コード
//
//	/** The affiliation id. */
//	private String affiliationId; // 所属ID
//
//	/** The affiliation name. */
//	private String affiliationName; // 所属名

}
