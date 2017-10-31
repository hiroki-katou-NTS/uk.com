/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.log;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.ExecutionContent;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.ExecutionType;

/**
 * @author danpv
 * 計算実行設定情報
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CalExeSettingInfor {
	
	/**
	 * 実行内容
	 * 
	 * 0 : 日別作成
	 * 1 : 日別計算
	 * 2 : 承認結果反映
	 * 3 : 月別集計
	 */
	private ExecutionContent executionContent;
	
	/**
	 * 実行種別
	 * 
	 * 0 : 通常実行
	 * 1 : 再実行
	 */
	private ExecutionType executionType;
	
}
