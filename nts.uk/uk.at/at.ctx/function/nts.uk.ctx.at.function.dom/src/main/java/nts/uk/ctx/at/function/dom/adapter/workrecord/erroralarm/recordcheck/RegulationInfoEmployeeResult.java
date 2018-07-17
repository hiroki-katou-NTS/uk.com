package nts.uk.ctx.at.function.dom.adapter.workrecord.erroralarm.recordcheck;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class RegulationInfoEmployeeResult {

	/** The employee id. */
	private String employeeId; // 社員ID

	/** The employee code. */
	private String employeeCode; // 社員コード

	/** The employee name. */
	private String employeeName; // 氏名

	/** The workplace code. */
	private String workplaceCode; 

	/** The workplace id. */
	private String workplaceId;

	/** The workplace name. */
	private String workplaceName; 
}