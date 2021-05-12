package nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.algorithm.monthly;

import java.util.Optional;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeImport;

/** 入社前、退職後を除く期間を取得する */
public class GetPeriodExcluseEntryRetireTime {

	/** 入社前、退職後を期間から除く */
	public static Optional<DatePeriod> getPeriodExcluseEntryRetireTime(Require require, CacheCarrier cacheCarrier, String sid, DatePeriod period) {

		// 社員を取得する
		EmployeeImport employee = require.employeeInfo(cacheCarrier, sid);

		val termInOffice = new DatePeriod(employee.getEntryDate(), employee.getRetiredDate());
		return confirmProcPeriod(period, termInOffice);
	}
	
	/**
	 * 処理期間との重複を確認する （重複期間を取り出す）
	 *
	 * @param target 処理期間
	 * @param comparison 比較対象期間
	 * @return 重複期間 （null = 重複なし）
	 */
	public static Optional<DatePeriod> confirmProcPeriod(DatePeriod target, DatePeriod comparison) {

		DatePeriod overlap = null; // 重複期間

		// 開始前
		if (target.isBefore(comparison))
			return Optional.of(overlap);

		// 終了後
		if (target.isAfter(comparison))
			return Optional.of(overlap);

		// 重複あり
		overlap = target;

		// 開始日より前を除外
		if (overlap.contains(comparison.start())) {
			overlap = overlap.cutOffWithNewStart(comparison.start());
		}

		// 終了日より後を除外
		if (overlap.contains(comparison.end())) {
			overlap = overlap.cutOffWithNewEnd(comparison.end());
		}

		return Optional.of(overlap);
	}
	
	public static interface Require {
		
		EmployeeImport employeeInfo(CacheCarrier cacheCarrier, String empId);
	}
}
