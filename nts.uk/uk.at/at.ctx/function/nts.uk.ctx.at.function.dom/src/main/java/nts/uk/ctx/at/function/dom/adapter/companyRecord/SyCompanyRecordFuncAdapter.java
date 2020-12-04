package nts.uk.ctx.at.function.dom.adapter.companyRecord;

import java.util.List;

import nts.arc.time.calendar.period.DatePeriod;

public interface SyCompanyRecordFuncAdapter {
	/**
	 * 社員の指定期間中の所属期間を取得する
	 * @param sid
	 * @param datePeriod
	 * @return
	 */
	public List<StatusOfEmployeeAdapter> getAffCompanyHistByEmployee(List<String> sid, DatePeriod datePeriod);
}
