
package nts.uk.ctx.at.shared.dom.alarmList.extractionResult;

import lombok.Getter;
import lombok.Setter;
/**
 * 
 * @author do_dt
 *
 */
@Getter
@Setter
public class AlarmListCheckInfor {
	/**
	 * チェック条件のNo
	 */
	private String No;
	/**
	 * チェック種類
	 */
	private AlarmListCheckType chekType;
}
