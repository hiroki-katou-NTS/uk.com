package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationrestrictionsetting.service;

import nts.arc.time.GeneralDate;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
public interface ActualLockingCheck {
	/**
	 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.申請承認.設定.会社別.申請承認設定.申請設定.申請制限設定.アルゴリズム.実績ロック状態かチェックする.実績ロック状態かチェックする
	 * @param canAppAchievementLock 実績修正がロック状態なら申請できない
	 * @return
	 */
	public boolean check(boolean canAppAchievementLock, String companyID, String employeeID, GeneralDate appDate);
}