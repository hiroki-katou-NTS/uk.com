package nts.uk.ctx.at.record.dom.adapter.query.employee;

import lombok.Builder;
import lombok.Data;

/**
 * The Class RegulationInfoEmployeeDto.
 */
@Builder
@Data
public class RegulationInfoEmployeeQueryR {

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
	
	private String employmentCode;
	
	private String bussinessTypeCode;
	
	private String classificationCode;
	
	private String jobTitle;

}
