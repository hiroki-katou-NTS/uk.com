package nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service;
/**
 * Parameter: 特別休暇期間外の使用
 * @author do_dt
 *
 */

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class UseDaysOfPeriodSpeHoliday {
	/**
	 * YMD
	 */
	private GeneralDate ymd;
	/**
	 * 使用数
	 */
	private Double useDays;
	/**
	 * 使用時間数
	 */
	private Integer useTimes;
}
