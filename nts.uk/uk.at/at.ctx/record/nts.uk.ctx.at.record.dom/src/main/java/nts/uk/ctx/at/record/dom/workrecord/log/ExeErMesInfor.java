/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.log;

import lombok.Data;
import nts.uk.ctx.at.record.dom.workrecord.log.objectvalue.SysErClass;

/**
 * 実行エラーメッセージ情報
 * @author tutk
 *
 */
@Data
public class ExeErMesInfor {
	/**
	 * エラーメッセージ
	 */
	private String errorMessage;
	/**
	 * システムエラー区分
	 */
	private SysErClass sysErrorType;
	/**
	 * 就業計算と集計実行ログID
	 */
	private long empCalAndSumExecLogId;
	
}
