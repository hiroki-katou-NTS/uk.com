package nts.uk.ctx.at.record.pub.company;

import java.util.List;

import nts.arc.time.calendar.period.DatePeriod;

public interface SyCompanyRecordPub {
	/**
	 * 社員の指定期間中の所属期間を取得する
	 * @param sid
	 * @param datePeriod
	 * @return
	 */
	List<StatusOfEmployeeExportPub> getAffCompanyHistByEmployee(List<String> sid, DatePeriod datePeriod);
}
