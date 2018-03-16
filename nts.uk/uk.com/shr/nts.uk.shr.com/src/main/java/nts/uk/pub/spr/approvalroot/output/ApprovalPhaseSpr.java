package nts.uk.pub.spr.approvalroot.output;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * 承認フェーズ
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ApprovalPhaseSpr {
	/**会社ID*/
	private String companyId;
	/**分岐ID*/
	private String branchId;
	/**承認フェーズID*/
	private String approvalPhaseId;
	/**承認形態*/
	private Integer approvalForm;
	/**閲覧フェーズ*/
	private int browsingPhase;
	/**順序*/
	private int orderNumber;
	
	/**承認者*/
	private List<ApproverSpr>  approvers;
	
	public static ApprovalPhaseSpr createFromJavaType(String companyId, String branchId, String approvalPhaseId,
		Integer approvalForm, int browsingPhase, int orderNumber, List<ApproverSpr> approvers){
		return new ApprovalPhaseSpr(companyId, branchId, approvalPhaseId, approvalForm, browsingPhase, orderNumber, approvers);
	}
}
