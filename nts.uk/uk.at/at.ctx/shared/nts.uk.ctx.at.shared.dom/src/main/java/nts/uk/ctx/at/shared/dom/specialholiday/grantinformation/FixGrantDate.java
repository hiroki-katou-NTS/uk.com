package nts.uk.ctx.at.shared.dom.specialholiday.grantinformation;

import java.util.ArrayList;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayCode;
import nts.uk.ctx.at.shared.dom.specialholiday.periodinformation.GrantPeriodic;

/**
 * 指定日付与
 * @author masaaki_jinno
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FixGrantDate {

	/** 付与日数 */
	private RegularGrantDays grantDays;

	/** 期限 */
	private GrantPeriodic grantPeriodic;

	/** 付与月日 */
	private Optional<MonthDay> grantMonthDay;

	/**
	 * Create from Java Type
	 * @return
	 */
	public static FixGrantDate createFromJavaType(
			String companyId,
			int specialHolidayCode,
			int grantDays,
			int timeSpecifyMethod,
			int limitCarryoverDays,
			GeneralDate expirationDate,
			int grantMonth,
			int grantDay) {

		return new FixGrantDate(
				RegularGrantDays.createFromJavaType(grantDays),
				GrantPeriodic.createFromJavaType(
						companyId, specialHolidayCode, timeSpecifyMethod, limitCarryoverDays, expirationDate),
				Optional.of(new MonthDay(grantMonth, grantDay))
			);

	}
}
