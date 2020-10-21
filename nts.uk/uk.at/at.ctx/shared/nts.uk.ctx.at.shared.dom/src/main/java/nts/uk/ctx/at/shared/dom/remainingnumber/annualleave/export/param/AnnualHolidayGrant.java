package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.param;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
/**
 * 年休付与
 * @author do_dt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AnnualHolidayGrant {
	/**付与日	 */
	private GeneralDate ymd;
	/**年休付与数	 */
	private double grantDays;
	/**年休使用数	 */
	private double useDays;
	/**期限日	 */
	private GeneralDate deadline;
	/**年休残数	 */
	private double remainDays;
}
