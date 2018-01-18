package nts.uk.ctx.workflow.dom.approvermanagement.setting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
/**
 * 承認設定
 * @author dudt
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalSetting extends AggregateRoot {
	/**
	 * 会社ID
	 */
	private String companyId;
	/**
	 * 本人による承認
	 */
	private PrincipalApprovalFlg prinFlg;
	public static ApprovalSetting createFromJavaType(String companyId, int prinFlg){
		return new ApprovalSetting(companyId, EnumAdaptor.valueOf(prinFlg, PrincipalApprovalFlg.class));
	}
}
