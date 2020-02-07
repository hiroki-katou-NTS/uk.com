package nts.uk.ctx.workflow.pub.spr.export;

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
public class ApprovalPhaseSprExport {
	/**会社ID*/
	private String companyId;
	/**承認ID*/
	private String approvalId;
	/**承認フェーズ順序*/
	private int phaseOrder;
	/**承認形態*/
	private Integer approvalForm;
	/**閲覧フェーズ*/
	private int browsingPhase;
	/**承認者指定区分*/
	private Integer approvalAtr;
	/**承認者*/
	private List<ApproverSprExport>  approvers;
	
	public static ApprovalPhaseSprExport createFromJavaType(String companyId, String approvalId, int phaseOrder,
		Integer approvalForm, int browsingPhase,Integer approvalAtr, List<ApproverSprExport> approvers){
		return new ApprovalPhaseSprExport(companyId, approvalId, phaseOrder, approvalForm, browsingPhase, approvalAtr, approvers);
	}
}
