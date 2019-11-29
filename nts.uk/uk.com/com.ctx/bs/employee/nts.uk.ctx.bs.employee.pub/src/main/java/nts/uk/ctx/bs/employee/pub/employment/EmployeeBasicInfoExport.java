/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.pub.employment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

/**
 * EmploymentInfoExport dùng cho RequestList638
 */
// 雇用
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class EmployeeBasicInfoExport   {
	
	private String employmentCode;

	private GeneralDate dateJoinComp;

	private String sid;

	private GeneralDate birthday;
	
	public  String  pid;
	
}
