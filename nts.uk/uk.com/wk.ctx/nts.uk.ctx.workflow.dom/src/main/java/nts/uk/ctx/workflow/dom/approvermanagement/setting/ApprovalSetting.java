package nts.uk.ctx.workflow.dom.approvermanagement.setting;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.layer.dom.AggregateRoot;
/**
 * 承認設定
 * @author dudt
 *
 */
@Data
@AllArgsConstructor
public class ApprovalSetting extends AggregateRoot {
	/**
	 * 会社ID
	 */
	private String companyId;
	/**
	 * 本人による承認
	 */
	private PrincipalApprovalFlg prinFlg;
	
}
