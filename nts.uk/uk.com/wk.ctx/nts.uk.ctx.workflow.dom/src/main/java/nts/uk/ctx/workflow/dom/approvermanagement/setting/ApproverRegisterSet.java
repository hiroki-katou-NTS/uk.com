package nts.uk.ctx.workflow.dom.approvermanagement.setting;

import lombok.Getter;
import lombok.NoArgsConstructor;
/**
 * Refactor5
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.承認者の登録設定
 * @author hoangnd
 *
 */
@Getter
@NoArgsConstructor
public class ApproverRegisterSet {
	// type 利用区分 in EA , it is not reference
	
	// 会社単位 
	private Boolean companyUnit;
	// 職場単位
	private Boolean workplaceUnit;
	// 社員単位
	private Boolean employeeUnit;	
}
