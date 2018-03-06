package nts.uk.ctx.at.function.ac.workrecord.erroralarm;

import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.ctx.at.function.dom.adapter.workrecord.erroralarm.FuncEmployeeDailyPerErrorAdapter;
import nts.uk.ctx.at.function.dom.adapter.workrecord.erroralarm.FuncEmployeeDailyPerErrorImport;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.EmployeeDailyPerErrorPub;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.EmployeeDailyPerErrorPubExport;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class FuncEmployeeDailyPerErrorAcAdapter implements FuncEmployeeDailyPerErrorAdapter {
	@Inject
	private EmployeeDailyPerErrorPub pub;

	@Override
	public List<FuncEmployeeDailyPerErrorImport> getByErrorAlarm(String employeeId, DatePeriod datePeriod,
			List<String> errorCodes) {
		List<EmployeeDailyPerErrorPubExport> employeeDailyExportList = pub.getByErrorCode(employeeId, datePeriod,
				errorCodes);
		if(employeeDailyExportList ==null || employeeDailyExportList.isEmpty()) throw new RuntimeException("domain 社員の日別実績エラー一覧 not found by list error alarm codeID");
		
		return employeeDailyExportList.stream()
				.map(e -> new FuncEmployeeDailyPerErrorImport(e.getCompanyID(), e.getEmployeeID(), e.getDate(),
						e.getErrorAlarmWorkRecordCode(), e.getAttendanceItemList(), e.getErrorCancelAble()))
				.collect(Collectors.toList());

	}

}
