package nts.uk.ctx.at.shared.dom.remainingnumber.common;

import java.util.Optional;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeImport;

/**
 * 休暇の集計期間を確認する
 * @author shuichi_ishida
 */
public class ConfirmLeavePeriod {

	/**
	 * 休暇の集計期間から入社前、退職後を除く
	 * @param target 集計期間
	 * @param employee 社員
	 * @return 集計期間　（null=失敗）
	 */
	public static Optional<DatePeriod> sumPeriod(DatePeriod target, EmployeeImport employee){

		if (target == null) return Optional.empty();
		if (employee == null) return Optional.empty();

		// 在職期間
		DatePeriod termInOffice = new DatePeriod(employee.getEntryDate(), employee.getRetiredDate());

		DatePeriod overlap = null;		// 集計期間（在籍期間との重複期間）

		// 開始前
		if (target.isBefore(termInOffice)) return Optional.empty();

		// 終了後
		if (target.isAfter(termInOffice)) return Optional.empty();

		// 重複あり
		overlap = target;

		// 開始日より前を除外
		if (overlap.contains(termInOffice.start())){
			overlap = overlap.cutOffWithNewStart(termInOffice.start());
		}

		// 終了日より後を除外
		if (overlap.contains(termInOffice.end())){
			overlap = overlap.cutOffWithNewEnd(termInOffice.end());
		}

		return Optional.of(overlap);
	}
}
