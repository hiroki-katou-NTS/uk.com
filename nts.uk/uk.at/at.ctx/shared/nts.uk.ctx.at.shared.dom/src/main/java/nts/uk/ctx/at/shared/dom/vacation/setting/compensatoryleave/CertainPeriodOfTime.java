package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.TimeOfDay;

/**
 * 一定時間
 * @author tutk
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CertainPeriodOfTime {
	/** 一定時間 **/
	private TimeOfDay  certainPeriodofTime;
}
