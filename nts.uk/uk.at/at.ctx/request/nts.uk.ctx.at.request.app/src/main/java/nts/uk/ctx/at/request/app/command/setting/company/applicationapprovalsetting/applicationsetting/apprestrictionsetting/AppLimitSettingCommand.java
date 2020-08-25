package nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.applicationsetting.apprestrictionsetting;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationrestrictionsetting.AppLimitSetting;
import org.apache.commons.lang3.BooleanUtils;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.申請設定.申請制限設定.申請制限設定
 * @author Doan Duy Hung
 *
 */

@NoArgsConstructor
@Setter
@Getter
public class AppLimitSettingCommand {

	/**
	 * 月別実績が確認済なら申請できない
	 */
	private int canAppAchievementMonthConfirm;

	/**
	 * 実績修正がロック状態なら申請できない
	 */
	private int canAppAchievementLock;

	/**
	 * 就業確定済の場合申請できない
	 */
	private int canAppFinishWork;

	/**
	 * 申請理由が必須
	 */
	private int requiredAppReason;

	/**
	 * 定型理由が必須
	 */
	private int standardReasonRequired;

	/**
	 * 日別実績が確認済なら申請できない
	 */
	private int canAppAchievementConfirm;

	public AppLimitSetting toDomain() {
		return new AppLimitSetting(
				BooleanUtils.toBoolean(canAppAchievementMonthConfirm),
				BooleanUtils.toBoolean(canAppAchievementLock),
				BooleanUtils.toBoolean(canAppFinishWork),
				BooleanUtils.toBoolean(requiredAppReason),
				BooleanUtils.toBoolean(standardReasonRequired),
				BooleanUtils.toBoolean(canAppAchievementConfirm)
		);
	}
	
}
