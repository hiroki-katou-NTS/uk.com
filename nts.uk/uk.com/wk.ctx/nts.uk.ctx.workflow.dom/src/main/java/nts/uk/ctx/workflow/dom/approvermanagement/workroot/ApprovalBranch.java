package nts.uk.ctx.workflow.dom.approvermanagement.workroot;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
/**
 * 分岐
 * @author hoatt
 *
 */
@Getter
@AllArgsConstructor
public class ApprovalBranch extends AggregateRoot{
	/**会社ID*/
	private String companyId;
	/**分岐ID*/
	private String branchId;
	/**番号*/
	private int number;
}
