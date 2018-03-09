package nts.uk.ctx.at.record.pub.workrecord.erroralarm.recordcheck;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RegulationInfoEmployeeQueryResult {

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
}
