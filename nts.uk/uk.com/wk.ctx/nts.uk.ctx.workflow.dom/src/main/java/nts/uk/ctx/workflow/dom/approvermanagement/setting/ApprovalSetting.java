package nts.uk.ctx.workflow.dom.approvermanagement.setting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
	
	// 承認単位の利用設定
	private ApproverRegisterSet approverRegsterSet;
	
	/**
	 * 本人による承認
	 */
	private Boolean prinFlg;
	
	public static ApprovalSetting createFromJavaType(String companyId,ApproverRegisterSet approverRegsterSet,  Boolean prinFlg){
		return new ApprovalSetting(companyId, approverRegsterSet, prinFlg);
	}
}
