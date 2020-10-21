package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;

public interface GetPeriodFromPreviousToNextGrantDate {
	/**
	 * 指定した月を基準に、前回付与日から次回付与日までの期間を取得
	 * @param cid 会社ID
	 * @param sid 社員ID
	 * @param ym  指定年月
	 * @param ymd 基準日
	 * @return 期間
	 */
	Optional<DatePeriod> getPeriodGrantDate(String cid, String sid, YearMonth ym, GeneralDate ymd, Integer periodOutput, Optional<DatePeriod> fromTo);
	/**
	 * 指定した年月日を基準に、前回付与日から次回付与日までの期間を取得
	 * @param cid 会社ID
	 * @param sid 社員ID
	 * @param ymd 指定年月日
	 * @return 期間
	 */
	Optional<DatePeriod> getPeriodYMDGrant(String cid, String sid, GeneralDate ymd, Integer periodOutput, Optional<DatePeriod> fromTo);
	/**
	 * 指定した年月日を基準に、前回付与日から1年後までの期間を取得
	 * @param cid 会社ID
	 * @param sid 社員ID
	 * @param ymd 指定年月日
	 * @return 期間
	 */
	Optional<DatePeriod> getPeriodAfterOneYear(String cid, String sid, GeneralDate ymd, Integer periodOutput, Optional<DatePeriod> fromTo);
}
