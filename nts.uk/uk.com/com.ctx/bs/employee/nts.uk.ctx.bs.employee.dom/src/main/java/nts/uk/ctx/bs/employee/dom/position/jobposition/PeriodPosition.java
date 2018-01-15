package nts.uk.ctx.bs.employee.dom.position.jobposition;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PeriodPosition {

	/** The StartDate 開始日 */
	GeneralDate startDate;

	/** The EndDate 終了日 */
	GeneralDate endDate;

	public static PeriodPosition createFromJavaType(String startDate, String endDate) {
		return new PeriodPosition(GeneralDate.legacyDate(new Date(startDate)), GeneralDate.legacyDate(new Date(endDate)));
	}
}
