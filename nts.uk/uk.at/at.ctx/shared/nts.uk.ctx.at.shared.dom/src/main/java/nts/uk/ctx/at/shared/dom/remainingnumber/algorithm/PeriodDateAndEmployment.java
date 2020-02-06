package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.calendar.period.DatePeriod;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PeriodDateAndEmployment {
	/**
	 * 期間
	 */
	private DatePeriod dateData;
	/**
	 * 雇用コード
	 */
	private String employment;
}
