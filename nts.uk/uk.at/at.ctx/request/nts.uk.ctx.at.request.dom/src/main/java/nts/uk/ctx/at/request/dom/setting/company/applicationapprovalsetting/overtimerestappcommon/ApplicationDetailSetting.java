package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.残業休出申請共通設定.申請詳細設定
 * @author Doan Duy Hung
 *
 */
@Getter
@AllArgsConstructor
public class ApplicationDetailSetting {
	
	/**
	 * 指示が必須
	 */
	private Boolean requiredInstruction; 
	
	/**
	 * 事前必須設定
	 */
	private NotUseAtr preRequireSet;
	
	/**
	 * 時間入力利用区分
	 */
	private NotUseAtr timeInputUse;
	
	/**
	 * 時刻計算利用区分
	 */
	private NotUseAtr timeCalUse;
	
	/**
	 * 出退勤時刻初期表示区分
	 */
	private AtWorkAtr atworkTimeBeginDisp;
	
	/**
	 * 退勤時刻がない時システム時刻を表示するか
	 */
	private boolean dispSystemTimeWhenNoWorkTime;
	
}
