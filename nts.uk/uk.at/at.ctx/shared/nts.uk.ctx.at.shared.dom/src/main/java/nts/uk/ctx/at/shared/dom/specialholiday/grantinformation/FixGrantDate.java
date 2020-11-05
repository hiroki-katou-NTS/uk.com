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
import nts.uk.ctx.at.shared.dom.specialholiday.periodinformation.GrantDeadline;

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
	private GrantDeadline grantPeriodic;

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
				GrantDeadline.createFromJavaType(
						companyId, specialHolidayCode, timeSpecifyMethod,
						expirationDate.year(), expirationDate.month(), limitCarryoverDays),
				Optional.of(new MonthDay(grantMonth, grantDay))
			);

	}
}
