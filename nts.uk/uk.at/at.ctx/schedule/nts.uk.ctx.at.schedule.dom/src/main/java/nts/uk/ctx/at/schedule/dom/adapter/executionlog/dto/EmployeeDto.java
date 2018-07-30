package nts.uk.ctx.at.schedule.dom.adapter.executionlog.dto;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import lombok.Value;

/**
 * The Class EmployeeDto.
 */
@Value
public class EmployeeDto {
	/** The employee Id. */
	String employeeId;
	
	/** The employee code. */
	String employeeCode;

	/** The employee name. */
	String employeeName;
	
	/** The constant EMPLOYEE_ID */
	private static final String EMPLOYEE_ID = "employeeId";
	
	/** The constant EMPLOYEE_CODE */
	private static final String EMPLOYEE_CODE = "employeeCode";
	
	/** The constant EMPLOYEE_NAME */
	private static final String EMPLOYEE_NAME = "employeeName";
	
	/**
	 * Builds the json object. Added by HoangNDH
	 *
	 * @return the json object
	 */
	public JsonObject buildJsonObject() {
		JsonObjectBuilder tmp = Json.createObjectBuilder();
		tmp.add(EMPLOYEE_ID, employeeId);
		tmp.add(EMPLOYEE_CODE, employeeCode);
		tmp.add(EMPLOYEE_NAME, employeeName);
		
		return tmp.build();
	}
}
