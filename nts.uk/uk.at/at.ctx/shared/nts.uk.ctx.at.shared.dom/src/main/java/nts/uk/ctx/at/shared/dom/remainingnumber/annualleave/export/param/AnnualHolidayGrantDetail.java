package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.param;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AnnualHolidayGrantDetail {
	/**社員ID	 */
	private String sid;
	/**	年月日	 */
	private GeneralDate ymd;
	/**使用数	 */
	private double useDays;
	/**参照元区分	 */
	ReferenceAtr referenceAtr;
	/**午前午後区分	 */
	private AmPmAtr amPmAtr;
}
