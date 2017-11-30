/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.processbatch;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

/**
 * The Class ErrorContentDto.
 */
// エラーの内容
@Getter
@Setter
public class ErrorContentDto {

	/** The message. */
	// メッセージ
	private String message;
	
	/** The date YMD. */
	// 年月日
	private GeneralDate dateYMD;
	
	/** The employee code. */
	// 社員コード
	private String employeeCode;
	
	/** The employee name. */
	//社員名
	private String employeeName;
}
