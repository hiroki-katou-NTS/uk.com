package nts.uk.ctx.workflow.dom.approvermanagement.workroot;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
@Getter
@AllArgsConstructor
public class ApprovalBranch extends AggregateRoot{
	/*会社ID*/
	private String companyId;
	/*分岐ID*/
	private int branchId;
	/*番号*/
	private BranchNumber number;
	
	public static ApprovalBranch createSimpleFromJavaType(String companyId,
			int branchId,
			int number){
		return new ApprovalBranch(companyId,
				branchId,
				EnumAdaptor.valueOf(number, BranchNumber.class));
	}
}
