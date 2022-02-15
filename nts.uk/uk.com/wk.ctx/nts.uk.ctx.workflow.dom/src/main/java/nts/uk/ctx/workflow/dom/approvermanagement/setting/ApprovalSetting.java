package nts.uk.ctx.workflow.dom.approvermanagement.setting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings.OperationMode;

/**
 * 承認設定
 * 
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

	public static ApprovalSetting createFromJavaType(String companyId, ApproverRegisterSet approverRegsterSet,
			Boolean prinFlg) {
		return new ApprovalSetting(companyId, approverRegsterSet, prinFlg);
	}

	/**
	 * [C-1] 社員単位だけ利用するで作成する
	 * 	
	 * @param cid	会社ID
	 * @return 承認設定
	 */
	public static ApprovalSetting createForEmployee(String cid) {
		// $承認単位の利用設定 ＝ 承認者の登録設定#社員単位だけ利用するで作成する()
		ApproverRegisterSet approverRegisterSet = ApproverRegisterSet.createForEmployee();
		return new ApprovalSetting(cid, approverRegisterSet, false);
	}

	/**
	 * [1] 運用モードにより単位変更する
	 * 
	 * @param operationMode 運用モード
	 */
	public void changeUnit(OperationMode operationMode) {
		// if 運用モード == 「上長・社員が行う」
		if (operationMode.equals(OperationMode.SUPERIORS_EMPLOYEE)) {
			// @承認単位の利用設定 ＝ 承認者の登録設定#社員単位だけ利用するで作成する()
			this.approverRegsterSet = ApproverRegisterSet.createForEmployee();
		}
	}
}
