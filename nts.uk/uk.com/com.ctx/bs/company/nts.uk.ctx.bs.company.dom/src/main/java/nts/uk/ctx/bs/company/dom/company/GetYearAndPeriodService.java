package nts.uk.ctx.bs.company.dom.company;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.Year;
import nts.arc.time.calendar.period.DatePeriod;

/**
* @author sakuratani
*
*			指定した締め期間の年期間を算出する
*
*/
public class GetYearAndPeriodService {

	public static YearAndPeriod get(Require require, String cid, DatePeriod period) {

		//会社情報を取得
		Optional<Company> company = require.find(cid);
		Year year = company.get().getYearBySpecifying(period.end().yearMonth());

		//年期間を算出する
		GeneralDate startDate = period.start().addMonths(period.end().month() - company.get().getStartMonth().value);

		if (period.end().yearMonth().month() < company.get().getStartMonth().value) {
			return YearAndPeriod.createByStartDate(year, startDate.addYears(-1));
		}

		return YearAndPeriod.createByStartDate(year, startDate);

		//		if (period.end().yearMonth().month() < company.get().getStartMonth().value) {
		//			GeneralDate startDate = period.start().addMonths(period.end().month() - company.get().getStartMonth().value)
		//					.addYears(-1);
		//			return YearAndPeriod.createByStartDate(year, startDate);
		//		} else {
		//			GeneralDate startDate = period.start()
		//					.addMonths(period.end().month() - company.get().getStartMonth().value);
		//			return YearAndPeriod.createByStartDate(year, startDate);
		//		}

	}

	// Require
	public static interface Require {

		// 会社情報を取得する(会社ID)
		Optional<Company> find(String companyId);

	}
}
