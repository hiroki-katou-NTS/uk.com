package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.appdispset;

import lombok.Getter;
import nts.uk.ctx.at.request.dom.setting.DisplayAtr;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.申請設定.申請表示設定.申請表示設定
 * @author Doan Duy Hung
 *
 */
@Getter
public class AppDisplaySetting {
	
	/**
	 * 事前事後区分表示
	 */
	private DisplayAtr prePostDisplayAtr;
	
	/**
	 * 登録時の手動メール送信の初期値
	 */
	private NotUseAtr manualSendMailAtr;
	
}
