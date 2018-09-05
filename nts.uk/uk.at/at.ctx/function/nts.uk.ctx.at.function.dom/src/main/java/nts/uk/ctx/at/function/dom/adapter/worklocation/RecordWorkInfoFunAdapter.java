package nts.uk.ctx.at.function.dom.adapter.worklocation;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface RecordWorkInfoFunAdapter {
	public Optional<RecordWorkInfoFunAdapterDto>  getInfoCheckNotRegister(String employeeId, GeneralDate ymd);
	
	public List<WorkInfoOfDailyPerFnImport> findByPeriodOrderByYmd(String employeeId);
	
	public List<RecordWorkInfoFunAdapterDto>  findByPeriodOrderByYmdAndEmps(List<String> employeeIds, DatePeriod datePeriod);
	
	public Optional<String> getWorkTypeCode(String employeeId, GeneralDate ymd);
}
