package nts.uk.ctx.workflow.dom.approvermanagement.workroot;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
/**
 * 承認フェーズ
 * @author hoatt
 *
 */
@Getter
@AllArgsConstructor
public class ApprovalPhase extends AggregateRoot{
	/**会社ID*/
	private String companyId;
	/**分岐ID*/
	private String branchId;
	/**承認フェーズID*/
	private String approvalPhaseId;
	/**承認形態*/
	private ApprovalForm approvalForm;
	/**閲覧フェーズ*/
	private int browsingPhase;
	/**順序*/
	private int orderNumber;
	
	public static ApprovalPhase createSimpleFromJavaType(String companyId,
			String branchId,
			String approvalPhaseId,
			int approvalForm,
			int browsingPhase,
			int orderNumber){
		return new ApprovalPhase(companyId,
				branchId,
				approvalPhaseId,
				EnumAdaptor.valueOf(approvalForm, ApprovalForm.class),
				browsingPhase,
				orderNumber);
	}
}
