/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.processbatch;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

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

	/** The constant MESSAGE */
	private static final String MESSAGE = "message";
	
	/** The constant DATE_YMD */
	private static final String DATE_YMD = "dateYMD";
	
	/** The constant EMPLOYEE_CODE */
	private static final String EMPLOYEE_CODE = "employeeCode";
	
	/** The constant EMPLOYEE_NAME */
	private static final String EMPLOYEE_NAME = "employeeName";
	
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
	private String employeeName = "";
	
	/**
	 * Create a error content json object to transfer to client side.
	 * @return error content json object
	 */
	public JsonObject buildJsonObject() {
		JsonObjectBuilder tmp = Json.createObjectBuilder();
		tmp.add(MESSAGE, message);
		tmp.add(DATE_YMD, dateYMD.toString());
		tmp.add(EMPLOYEE_CODE, employeeCode);
		tmp.add(EMPLOYEE_NAME, employeeName);
		
		return tmp.build();
	}
}
