package nts.uk.ctx.at.aggregation.dom.common;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily.IntegrationOfDailyHelper;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;

class DlyAtdServiceHelper {

	/**
	 * システム日付の年月から期間を作成する
	 * @param start 開始日の日付
	 * @param end 終了日の日付
	 * @return
	 */
	public static DatePeriod createPeriod(int start, int end) {
		return new DatePeriod(
						IntegrationOfDailyHelper.createDate(start)
					,	IntegrationOfDailyHelper.createDate(end)
				);
	}

	/**
	 * 期間(Dummy)を作成する
	 * @return 期間(Dummy)
	 */
	public static DatePeriod createDummyPeriod() {
		return new DatePeriod( GeneralDate.min(), GeneralDate.max() );
	}

	/**
	 * 社員IDリスト(Dummy)を作成する
	 * @param start 社員ID連番 開始
	 * @param end 社員ID連番 終了
	 * @return 社員IDリスト(Dummy)
	 */
	public static List<EmployeeId> createDummyEmpIds(int start, int end) {
		return IntStream.rangeClosed( start, end ).boxed()
				.map( IntegrationOfDailyHelper::createEmpId )
				.collect(Collectors.toList());
	}

	/**
	 * 日別勤怠(Work)を作成する
	 * @param empId 社員ID
	 * @param ymd 年月日
	 * @param atr 予実区分
	 * @return 日別勤怠(Work)
	 */
	public static IntegrationOfDaily createDlyAtdList(String empId, GeneralDate ymd, ScheRecAtr atr) {

		return IntegrationOfDailyHelper.createWithAffInfo(empId,  ymd
				, IntegrationOfDailyHelper.AffInfoHelper.createWithRequired("EmpCd", "JTId", "WkpId", String.format( "%s-%d", atr,  ymd.day()) ));

	}

}
