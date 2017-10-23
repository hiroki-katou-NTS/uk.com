/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.log.usecase;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.record.dom.workrecord.log.objectvalue.ExecutionContent;
import nts.uk.ctx.at.record.dom.workrecord.log.objectvalue.ExecutionType;

/**
 * @author danpv
 * 計算実行設定情報
 */
@Getter
@AllArgsConstructor
public class CalExeSettingInfor {
	
	/**
	 * 0 : 日別作成
	 * 1 : 日別計算
	 * 2 : 承認結果反映
	 * 3 : 月別集計
	 */
	private ExecutionContent executionContent;
	
	/**
	 * 通常実行
	 * 再実行
	 */
	private ExecutionType executionType;
	
}
