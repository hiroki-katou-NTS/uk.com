package nts.uk.screen.at.app.monthlyperformance.correction.command;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.uk.screen.at.app.monthlyperformance.correction.MonthlyPerformanceScreenRepo;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.EditStateOfMonthlyPerformanceDto;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class MonthlyPerformanceCorrectionUpdateCommand {
	@Inject
	private MonthlyPerformanceScreenRepo monthlyPerformanceScreenRepo;
	
	public void handleAddOrUpdate(EditStateOfMonthlyPerformanceDto eDto ) {
		String employeeId = AppContexts.user().employeeId();
		if(!employeeId.equals(eDto.getEmployeeId())){
			eDto.setStateOfEdit(1);
		}
		this.monthlyPerformanceScreenRepo.insertOrUpdateEditStateOfMonthlyPer(eDto);
	}
}
