package nts.uk.ctx.at.shared.dom.specialholiday.grantinformation;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * 期間付与
 * @author masaaki_jinno
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PeriodGrantDate {

	/** 期間 */
	private DatePeriod period;

	/** 付与日数 */
	private RegularGrantDays grantDays;

	static public PeriodGrantDate of(
			/** 期間 */
			DatePeriod period
			/** 付与日数 */
			, RegularGrantDays grantDays
		){
			PeriodGrantDate c = new PeriodGrantDate();
			/** 期間 */
			c.period=period;
			/** 付与日数 */
			c.grantDays=grantDays;

			return c;
		}
}
