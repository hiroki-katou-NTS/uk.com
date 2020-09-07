package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.param;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AnnualHolidayGrantInfor {
	/**社員ID	 */
	private String sid;
	/**次回年休付与日	 */
	private GeneralDate ymd;
	/**年休付与	 */
	private List<AnnualHolidayGrant> lstGrantInfor;
}
