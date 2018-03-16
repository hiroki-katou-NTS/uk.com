package nts.uk.pub.spr.approvalroot.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * 承認者
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ApproverSpr {
	/**会社ID*/
	private String companyId;
	/**分岐ID*/
	private String branchId;
	/**承認フェーズID*/
	private String approvalPhaseId;
	/**承認者ID*/
	private String approverId;
	/**職位ID*/
	private String jobTitleId;
	/**社員ID*/
	private String employeeId;
	/**順序*/
	private int orderNumber;
	/**承認者指定区分*/
	private Integer approvalAtr;
	/**確定者*/
	private Integer confirmPerson;
	
	public static ApproverSpr createFromJavaType(String companyId, String branchId, String approvalPhaseId, String approverId,
		String jobTitleId, String employeeId, int orderNumber, Integer approvalAtr, Integer confirmPerson){
		return new ApproverSpr(
				companyId, 
				branchId, 
				approvalPhaseId, 
				approverId, 
				jobTitleId, 
				employeeId, 
				orderNumber, 
				approvalAtr, 
				confirmPerson);
	} 
}
