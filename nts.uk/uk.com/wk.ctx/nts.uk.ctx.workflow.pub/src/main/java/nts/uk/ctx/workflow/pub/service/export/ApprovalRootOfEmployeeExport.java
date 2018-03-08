package nts.uk.ctx.workflow.pub.service.export;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author loivt
 * 基準社員の承認対象者
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalRootOfEmployeeExport {
	/**
	 * 基準社員
	 */
	private String employeeStandard;
	
	/**
	 * ルート状況
	 */
	private List<ApprovalRootSituation> approvalRootSituations;

}
