/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.log;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDateTime;

/**
 * @author danpv
 *
 */
@Getter
@AllArgsConstructor
public class ExecutionTime {

	/**
	 * 開始日時
	 */
	private GeneralDateTime startTime;
	/**
	 * 終了日時
	 */
	private GeneralDateTime endTime;

}
