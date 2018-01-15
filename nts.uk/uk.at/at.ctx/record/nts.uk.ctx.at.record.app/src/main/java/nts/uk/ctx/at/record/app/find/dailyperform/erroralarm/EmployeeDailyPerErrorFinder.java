package nts.uk.ctx.at.record.app.find.dailyperform.erroralarm;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.erroralarm.dto.EmployeeDailyPerErrorDto;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerErrorRepository;
import nts.uk.ctx.at.shared.app.util.attendanceitem.FinderFacade;

@Stateless
public class EmployeeDailyPerErrorFinder extends FinderFacade {

	@Inject
	private EmployeeDailyPerErrorRepository repo;
	
	@SuppressWarnings("unchecked")
	@Override
	public EmployeeDailyPerErrorDto find(String employeeId, GeneralDate baseDate) {
		EmployeeDailyPerErrorDto dto = new EmployeeDailyPerErrorDto();
		//TODO: confirm type return
		EmployeeDailyPerError domain = this.repo.find(employeeId, baseDate);
		if(domain != null){
			dto.setAttendanceItemList(domain.getAttendanceItemList());
			dto.setCompanyID(domain.getCompanyID());
			dto.setDate(domain.getDate());
			dto.setEmployeeID(domain.getEmployeeID());
			dto.setErrorCode(domain.getErrorAlarmWorkRecordCode().v());
		}
		return dto;
	}

}
