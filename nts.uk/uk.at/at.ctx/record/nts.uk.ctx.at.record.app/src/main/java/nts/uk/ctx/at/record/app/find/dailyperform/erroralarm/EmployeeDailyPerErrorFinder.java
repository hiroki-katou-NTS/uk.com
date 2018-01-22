package nts.uk.ctx.at.record.app.find.dailyperform.erroralarm;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.erroralarm.dto.EmployeeDailyPerErrorDto;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerErrorRepository;
import nts.uk.ctx.at.shared.app.util.attendanceitem.FinderFacade;

@Stateless
public class EmployeeDailyPerErrorFinder extends FinderFacade {

	@Inject
	private EmployeeDailyPerErrorRepository repo;
	
	@SuppressWarnings("unchecked")
	@Override
	public EmployeeDailyPerErrorDto find(String employeeId, GeneralDate baseDate) {
		//TODO: confirm type return
		return EmployeeDailyPerErrorDto.getDto(this.repo.find(employeeId, baseDate));
	}

}
