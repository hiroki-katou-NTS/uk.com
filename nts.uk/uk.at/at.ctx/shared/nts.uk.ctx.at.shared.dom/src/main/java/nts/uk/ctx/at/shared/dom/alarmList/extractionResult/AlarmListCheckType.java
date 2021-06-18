package nts.uk.ctx.at.shared.dom.alarmList.extractionResult;

import lombok.AllArgsConstructor;
import nts.arc.enums.EnumAdaptor;

/**
 * アラームリストチェック種類
 * @author do_dt
 *
 */
@AllArgsConstructor
public enum AlarmListCheckType {
	/**固定チェック	 */
	FixCheck(0),
	/**自由チェック	 */
	FreeCheck(1),
	/**36法定	 */
	Legal(2),
	/**36超過	 */
	Excess(3);
	public final int value;

	public static AlarmListCheckType of(int value) {
		return EnumAdaptor.valueOf(value, AlarmListCheckType.class);
	}
}
