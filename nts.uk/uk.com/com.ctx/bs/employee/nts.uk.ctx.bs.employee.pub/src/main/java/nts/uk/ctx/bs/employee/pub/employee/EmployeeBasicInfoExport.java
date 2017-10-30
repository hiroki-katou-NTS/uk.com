/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.pub.employee;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDate;

/**
 * The Class EmployeeBasicInfoExport.
 */
//個人社員基本情報
@Data
@Builder
public class EmployeeBasicInfoExport {
	
	/** The p id. */
	// 個人ID
	private String pId;
	
	/** The p name. */
	// 個人名
	private String pName;
	
	/** The company mail addr. */
	// 会社メールアドレス
	private MailAddress companyMailAddr;
	
	/** The birth day. */
	// 生年月日
	private GeneralDate birthDay;
	
	/** The p mail addr. */
	// 個人メールアドレス
	private MailAddress pMailAddr;
	
	/** The emp id. */
	// 社員ID
	private String empId;
	
	/** The emp code. */
	// 社員コード
	private String empCode;
	
	/** The entry date. */
	// 入社年月日
	private GeneralDate entryDate;

	/** The retired date. */
	// 退職年月日
	private GeneralDate retiredDate;

}
