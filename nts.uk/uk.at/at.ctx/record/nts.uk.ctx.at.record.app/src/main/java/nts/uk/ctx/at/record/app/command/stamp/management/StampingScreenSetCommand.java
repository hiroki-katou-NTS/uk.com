package nts.uk.ctx.at.record.app.command.stamp.management;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class StampingScreenSetCommand {
	/** 打刻履歴表示方法 */
	private int historyDisplayMethod;
	
	/** 打刻画面のサーバー時刻補正間隔 */
	private int correctionInterval;
	
	/** 打刻画面の日時の色設定 */
	private ColorSettingCommand colorSetting;
	
	/** 打刻結果自動閉じる時間 */
	private int resultDisplayTime;
}
