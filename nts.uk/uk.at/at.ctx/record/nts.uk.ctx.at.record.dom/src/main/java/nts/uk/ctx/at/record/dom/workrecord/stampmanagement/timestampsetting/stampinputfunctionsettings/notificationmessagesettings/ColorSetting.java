package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.stampinputfunctionsettings.notificationmessagesettings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.color.ColorCode;

/**
 * @name 表示色設定
 * @part UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.打刻管理.打刻設定.打刻入力の機能設定.お知らせメッセージ設定.表示色設定
 * @author ThanhPV
 */
@AllArgsConstructor
@Getter
public class ColorSetting {

	/** 文字色 */
	private ColorCode textColor;
	
	/** 背景色 */
	private ColorCode backGroundColor;

}
