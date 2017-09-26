package nts.uk.ctx.at.shared.dom.bonuspay.setting;

import lombok.Value;
import nts.uk.shr.com.time.AttendanceClock;

/**
 * 特定日加給時間帯
 * @author keisuke_hoshina
 *
 */
@Value
public class SpecifiedbonusPayTimeSheet {
	private AttendanceClock start;
	private AttendanceClock end;
}
