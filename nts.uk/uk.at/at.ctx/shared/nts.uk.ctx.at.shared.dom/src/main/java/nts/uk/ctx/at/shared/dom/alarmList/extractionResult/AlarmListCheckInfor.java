
package nts.uk.ctx.at.shared.dom.alarmList.extractionResult;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * 
 * @author do_dt
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
