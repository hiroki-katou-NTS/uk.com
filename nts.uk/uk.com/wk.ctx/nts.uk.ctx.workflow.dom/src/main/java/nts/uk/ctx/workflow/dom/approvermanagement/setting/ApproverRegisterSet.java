package nts.uk.ctx.workflow.dom.approvermanagement.setting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Refactor5
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.承認者の登録設定
 * 
 * @author hoangnd
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApproverRegisterSet {
	// 会社単位
	private UseClassification companyUnit;
	// 職場単位
	private UseClassification workplaceUnit;
	// 社員単位
	private UseClassification employeeUnit;

	/**
	 * [C-1]社員単位だけ利用するで作成する
	 * 
	 * @return 承認者の登録設定
	 */
	public static ApproverRegisterSet createForEmployee() {
		return new ApproverRegisterSet(UseClassification.DO_NOT_USE, UseClassification.DO_NOT_USE,
				UseClassification.DO_USE);
	}
}
