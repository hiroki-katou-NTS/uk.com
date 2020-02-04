package nts.uk.ctx.at.shared.dom.adapter.employment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.calendar.period.DatePeriod;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AffPeriodEmpCodeImport {
	/** The code. */
	// 期間
	private DatePeriod period;

	/** The employment code. */
	// 雇用コード
	private String employmentCode;
}
