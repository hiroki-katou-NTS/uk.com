/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.log;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 対象者
 * @author danpv
 *
 */
@Getter
@AllArgsConstructor
public class TargetPerson extends AggregateRoot{

	
	/**
	 * 社員ID
	 */
	private String employeeId;
	/**
	 * 就業計算と集計実行ログID
	 */
	private long empCalAndSumExecLogId;
	/**
	 * 状態
	 */
	private ComplStateOfExeContents state;
	
}
