package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationrestrictionsetting;

import lombok.Getter;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.申請設定.申請制限設定.申請制限設定
 * @author Doan Duy Hung
 *
 */
@Getter
public class AppLimitSetting {
	
	/**
	 * 月別実績が確認済なら申請できない
	 */
	private boolean canAppAchievementMonthConfirm;
	
	/**
	 * 実績修正がロック状態なら申請できない
	 */
	private boolean canAppAchievementLock;
	
	/**
	 * 就業確定済の場合申請できない
	 */
	private boolean canAppFinishWork;
	
	/**
	 * 申請理由が必須
	 */
	private boolean requiredAppReason;
	
	/**
	 * 定型理由が必須
	 */
	private boolean standardReasonRequired;
	
	/**
	 * 日別実績が確認済なら申請できない
	 */
	private boolean canAppAchievementConfirm;
	
}
