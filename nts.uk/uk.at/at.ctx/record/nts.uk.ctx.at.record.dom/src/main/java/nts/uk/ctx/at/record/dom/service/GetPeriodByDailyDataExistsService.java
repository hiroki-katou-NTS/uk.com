package nts.uk.ctx.at.record.dom.service;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;

/**
* @author sakuratani
*
*			日別実績データが存在する期間を取得する
*
*/
public class GetPeriodByDailyDataExistsService {

	public static Optional<DatePeriod> get(Require require, String employeeId, DatePeriod period) {

		// 日別実績の勤務情報を取得する
		List<WorkInfoOfDailyPerformance> workInformation = require.findByPeriodOrderByYmd(employeeId,
				period);
		if (workInformation.isEmpty()) {
			return Optional.empty();
		}

		// 取得した日別実績の勤務情報から最大の日付を取得する
		GeneralDate maxYmd = workInformation.get(workInformation.size() - 1).getYmd();

		if (maxYmd.equals(period.end())) {
			return Optional.of(period);
		}
		if (maxYmd.before(period.end())) {
			return Optional.of(new DatePeriod(period.start(), maxYmd));
		}
		return Optional.empty();

	}

	// Require
	public static interface Require {

		// 日別実績の勤務情報を取得
		public List<WorkInfoOfDailyPerformance> findByPeriodOrderByYmd(String employeeId, DatePeriod period);

	}

}
