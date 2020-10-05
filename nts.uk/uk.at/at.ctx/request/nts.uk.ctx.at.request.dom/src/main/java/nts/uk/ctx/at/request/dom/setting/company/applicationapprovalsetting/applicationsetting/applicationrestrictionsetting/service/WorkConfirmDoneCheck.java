package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationrestrictionsetting.service;

import nts.arc.time.GeneralDate;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
public interface WorkConfirmDoneCheck {
	/**
	 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.申請承認.設定.会社別.申請承認設定.申請設定.申請制限設定.アルゴリズム.就業確定済みかチェックする.就業確定済みかチェックする
	 * @param canAppFinishWork 就業確定済の場合申請できない
	 * @return
	 */
	public boolean check(boolean canAppFinishWork, String companyID, String employeeID, GeneralDate appDate);
}