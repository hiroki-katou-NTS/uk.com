package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.param;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

//年休付与情報
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AnnualHolidayGrantInfor {
	/**付与	 */
	private List<AnnualHolidayGrant> lstGrantInfor;
	/**取得した期間	 */
	private DatePeriod period;
	/**次回年休付与日	 */
	private GeneralDate ymd;
	/**社員ID	 */
	private String sid;
	/**ダブルトラック開始日	 */
	private GeneralDate doubleTrackStartDate;
}
