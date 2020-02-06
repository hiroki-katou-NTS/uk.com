package nts.uk.ctx.at.record.pub.workinformation;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface RecordWorkInfoPub {

	/**
	 * RequestList5
	 * 
	 * @param employeeId 社員ID
	 * @param ymd　日付
	 * @return　RecordWorkInfoPubExport
	 */
	RecordWorkInfoPubExport getRecordWorkInfo(String employeeId, GeneralDate ymd);
	
	List<WorkInfoOfDailyPerExport> findByEmpId(String employeeId);

	Optional<InfoCheckNotRegisterPubExport> getInfoCheckNotRegister(String employeeId, GeneralDate ymd);
	
	List<InfoCheckNotRegisterPubExport> findByPeriodOrderByYmdAndEmps(List<String> employeeIds, DatePeriod datePeriod);
	
	Optional<String> getWorkTypeCode(String employeeId, GeneralDate ymd);
	
	
}
