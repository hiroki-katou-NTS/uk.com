package nts.uk.ctx.at.record.pubimp.workrecord.erroralarm;

import java.util.List;
import java.util.Optional;
import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerErrorRepository;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.EmployeeDailyPerErrorPub;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.EmployeeDailyPerErrorPubExport;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class EmployeeDailyPerErrorPubImpl implements EmployeeDailyPerErrorPub {
	@Inject
	private EmployeeDailyPerErrorRepository repo;

	@Override
	public Optional<EmployeeDailyPerErrorPubExport> getByErrorCode(String employeeId, DatePeriod datePeriod,
			String errorCode) {
		List<EmployeeDailyPerError> employeeDailyList = repo.findByPeriodOrderByYmd(employeeId, datePeriod);
		Optional<EmployeeDailyPerError> employeeDailyOpt = employeeDailyList.stream()
				.filter(e -> e.getErrorAlarmWorkRecordCode().v().equals(errorCode)).findFirst();
		if (employeeDailyOpt.isPresent()) {
			EmployeeDailyPerError e = employeeDailyOpt.get();
			return Optional.of(new EmployeeDailyPerErrorPubExport(e.getCompanyID(), e.getEmployeeID(), e.getDate(),
					e.getErrorAlarmWorkRecordCode().v(), e.getAttendanceItemList(), e.getErrorCancelAble()));
		}
		return Optional.empty();
	}

}
