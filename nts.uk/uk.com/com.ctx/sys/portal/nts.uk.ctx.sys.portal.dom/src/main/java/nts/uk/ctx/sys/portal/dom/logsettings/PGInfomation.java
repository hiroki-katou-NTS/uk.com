package nts.uk.ctx.sys.portal.dom.logsettings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PGInfomation {
	/**
	 * プログラムID
	 */
	private String programId;
	
	/**
	 * ログイン履歴の記録
	 */
	private TargetSetting loginHistoryRecord;
	
	/**
	 * 修正履歴の記録
	 */
	private TargetSetting editHistoryRecord;
	
	/**
	 * 機能名
	 */
	private String functionName;
	
	/**
	 * メニュー分類
	 */
	private Integer menuClassification;
	
	/**
	 * 起動履歴の記録
	 */
	private TargetSetting bootHistoryRecord;
}
