package nts.uk.ctx.workflow.dom.approvermanagement.workroot;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
/**
 * Refactor5 UKDesign.ドメインモデル.NittsuSystem.UniversalK.ワークフロー.承認者管理.分岐.分岐
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
