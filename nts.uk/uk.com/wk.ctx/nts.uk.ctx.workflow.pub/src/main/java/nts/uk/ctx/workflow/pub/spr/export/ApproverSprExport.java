package nts.uk.ctx.workflow.pub.spr.export;

import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * 承認者
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ApproverSprExport {
	/**会社ID*/
	private String companyId;
	/**承認ID*/
	private String approvalId;
	/**承認フェーズ順序*/
	private int phaseOrder;
	/**承認者順序*/
	private int approverOrder;
	/**職位ID*/
	private String jobTitleId;
	/**社員ID*/
	private String employeeId;
	/**確定者*/
	private Integer confirmPerson;
	
	public static ApproverSprExport createFromJavaType(String companyId, String approvalId, int phaseOrder,
			int approverOrder, String jobTitleId, String employeeId, Integer confirmPerson){
		return new ApproverSprExport(
				companyId, 
				approvalId, 
				phaseOrder, 
				approverOrder, 
				jobTitleId, 
				employeeId, 
				confirmPerson);
	} 
}
