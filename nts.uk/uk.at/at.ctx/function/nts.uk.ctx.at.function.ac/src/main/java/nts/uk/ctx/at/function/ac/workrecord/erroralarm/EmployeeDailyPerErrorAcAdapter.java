package nts.uk.ctx.at.function.ac.workrecord.erroralarm;

import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.ctx.at.function.dom.adapter.workrecord.erroralarm.EmployeeDailyPerErrorAdapter;
import nts.uk.ctx.at.function.dom.adapter.workrecord.erroralarm.EmployeeDailyPerErrorImport;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.EmployeeDailyPerErrorPub;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.EmployeeDailyPerErrorPubExport;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class EmployeeDailyPerErrorAcAdapter implements EmployeeDailyPerErrorAdapter {
	@Inject
	private EmployeeDailyPerErrorPub pub;

	@Override
	public List<EmployeeDailyPerErrorImport> getByErrorAlarm(String employeeId, DatePeriod datePeriod,
			List<String> errorCodes) {
		List<EmployeeDailyPerErrorPubExport> employeeDailyExportList = pub.getByErrorCode(employeeId, datePeriod,
				errorCodes);
		if(employeeDailyExportList == null)
			throw new RuntimeException("EmployeeDailyPerErrorPub.getByErrorCode return NULL");
		
		return employeeDailyExportList.stream()
				.map(e -> new EmployeeDailyPerErrorImport(e.getCompanyID(), e.getEmployeeID(), e.getDate(),
						e.getErrorAlarmWorkRecordCode(), e.getAttendanceItemList(), e.getErrorCancelAble()))
				.collect(Collectors.toList());

	}

	@Override
	public List<EmployeeDailyPerErrorImport> getByErrorAlarm(List<String> employeeId, DatePeriod datePeriod,
			List<String> errorCodes) {
		List<EmployeeDailyPerErrorPubExport> employeeDailyExportList = pub.getByErrorCode(employeeId, datePeriod,
				errorCodes);
		if(employeeDailyExportList == null)
			throw new RuntimeException("EmployeeDailyPerErrorPub.getByErrorCode return NULL");
		
		return employeeDailyExportList.stream()
				.map(e -> new EmployeeDailyPerErrorImport(e.getCompanyID(), e.getEmployeeID(), e.getDate(),
						e.getErrorAlarmWorkRecordCode(), e.getAttendanceItemList(), e.getErrorCancelAble()))
				.collect(Collectors.toList());
	}

}
