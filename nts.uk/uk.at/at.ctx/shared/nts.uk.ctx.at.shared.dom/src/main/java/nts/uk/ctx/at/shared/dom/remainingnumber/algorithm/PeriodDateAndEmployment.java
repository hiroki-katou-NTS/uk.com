package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
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
