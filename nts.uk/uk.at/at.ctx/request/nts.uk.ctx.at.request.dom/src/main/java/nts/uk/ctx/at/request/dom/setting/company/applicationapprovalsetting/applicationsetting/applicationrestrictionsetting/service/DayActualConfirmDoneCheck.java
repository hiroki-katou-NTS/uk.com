package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationrestrictionsetting.service;

import nts.arc.time.GeneralDate;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
public interface DayActualConfirmDoneCheck {
	/**
	 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.申請承認.設定.会社別.申請承認設定.申請設定.申請制限設定.アルゴリズム.日別実績の確認済みかチェックする.日別実績の確認済みかチェックする
	 * @param canAppAchievementConfirm 日別実績が確認済なら申請できない
	 * @return
	 */
	public boolean check(boolean canAppAchievementConfirm, String companyID, String employeeID, GeneralDate appDate);
}