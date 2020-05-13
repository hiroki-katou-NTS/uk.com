package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainValue;

/**
 * VO: 打刻画面の表示設定
 * path: UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.打刻設定.打刻入力の前準備.打刻画面の表示設定
 * @author laitv
 *
 */
@Getter
public class DisplaySettingsStampScreen implements DomainValue{
	
	/** 打刻画面のサーバー時刻補正間隔	 */
	private  CorrectionInterval serverCorrectionInterval;
	
	/** 打刻画面の日時の色設定	 */
	private  SettingDateTimeColorOfStampScreen settingDateTimeColor;
	
	/** 	打刻結果自動閉じる時間  */
	private  ResultDisplayTime resultDisplayTime;
	
	
	// [C-0] 打刻画面の表示設定(打刻画面のサーバー時刻補正間隔, 打刻画面の日時の色設定, 打刻結果自動閉じる時間)																							
	public DisplaySettingsStampScreen(CorrectionInterval serverCorrectionInterval, 
			SettingDateTimeColorOfStampScreen settingDateTimeColor,
			ResultDisplayTime resultDisplayTime) {
		this.serverCorrectionInterval = serverCorrectionInterval;
		this.settingDateTimeColor = settingDateTimeColor;
		this.resultDisplayTime = resultDisplayTime;
	}
}
