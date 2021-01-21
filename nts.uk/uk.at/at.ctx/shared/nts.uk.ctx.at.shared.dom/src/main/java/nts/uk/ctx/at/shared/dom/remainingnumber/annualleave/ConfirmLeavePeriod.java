package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave;

import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeImport;
import nts.arc.time.calendar.period.DatePeriod;

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
	public static DatePeriod sumPeriod(DatePeriod target, EmployeeImport employee){
		
		if (target == null) return null;
		if (employee == null) return null;

		// 在職期間
		DatePeriod termInOffice = new DatePeriod(employee.getEntryDate(), employee.getRetiredDate());
		
		DatePeriod overlap = null;		// 集計期間（在籍期間との重複期間）
		
		// 開始前
		if (target.isBefore(termInOffice)) return overlap;
		
		// 終了後
		if (target.isAfter(termInOffice)) return overlap;
		
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

		return overlap;
	}
}
