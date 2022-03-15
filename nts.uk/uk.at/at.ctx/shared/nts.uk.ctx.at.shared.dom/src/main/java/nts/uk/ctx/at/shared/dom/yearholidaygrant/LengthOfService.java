package nts.uk.ctx.at.shared.dom.yearholidaygrant;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;

@Setter
@Getter
@AllArgsConstructor
public class LengthOfService {
	/* 付与回数 */
	private GrantNum grantNum;

	/* 一斉付与する */
	private GrantSimultaneity allowStatus;

	/* 付与基準日 */
	private GrantReferenceDate standGrantDay;

	/* 年数 */
	private LimitedTimeHdDays year;

	/* 月数 */
	private Month month;

	public static LengthOfService createFromJavaType(int grantNum ,int allowStatus, int standGrantDay,Integer year,Integer month) {
		return new LengthOfService(new GrantNum(grantNum),
				EnumAdaptor.valueOf(allowStatus, GrantSimultaneity.class),
				EnumAdaptor.valueOf(standGrantDay, GrantReferenceDate.class),
				year == null ? null : new LimitedTimeHdDays(year), month == null ? null : new Month(month));
	}

	/**
	 *
	 * @param grantHolidayList
	 */

	public Optional<GeneralDate> calculateGrantDate(int useSimultaneousGrant, GeneralDate referDate,GeneralDate simultaneousGrantDate, Optional<GeneralDate> previousGrantDate) {

		GeneralDate c = GeneralDate.ymd(referDate.year(), referDate.month(), referDate.day());
		c = c.addMonths(this.month.v());
		c = c.addYears(this.year.v());

		if (UseSimultaneousGrant.USE.ordinal()==useSimultaneousGrant) {
			if (GrantSimultaneity.USE.equals(this.allowStatus)) {
				// 処理名4.付与日シュミレーション計算処理について
				if (previousGrantDate.isPresent()) {
					if (previousGrantDate.get().afterOrEquals(GeneralDate.ymd(c.year() ,
							simultaneousGrantDate.month(), simultaneousGrantDate.day()))) {
						return Optional.empty();
					} else if ((c.month() != simultaneousGrantDate.month() || c.day() < simultaneousGrantDate.day())
							&& c.month() <= simultaneousGrantDate.month()) {
						return Optional.of(GeneralDate.ymd(c.year() - 1, simultaneousGrantDate.month(),
								simultaneousGrantDate.day()));
					}else {
						return Optional.of(GeneralDate.ymd(c.year(), simultaneousGrantDate.month(),
								simultaneousGrantDate.day()));
					}

				} else {
					return Optional.of(GeneralDate.ymd(c.year(), simultaneousGrantDate.month(),
							simultaneousGrantDate.day()));
				}

			} else {
				return Optional.of(c);
			}

		} else {
			// 処理名3.付与日シュミレーション計算処理について
			return Optional.of(c);
		}
	}

}
