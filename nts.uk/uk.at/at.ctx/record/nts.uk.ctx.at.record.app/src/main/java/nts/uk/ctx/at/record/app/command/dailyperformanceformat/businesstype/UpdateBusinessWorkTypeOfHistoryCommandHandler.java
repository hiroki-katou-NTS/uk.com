package nts.uk.ctx.at.record.app.command.dailyperformanceformat.businesstype;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.businesstype.BusinessTypeOfEmployee;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.businesstype.BusinessTypeOfEmployeeHistory;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.businesstype.repository.BusinessTypeEmpOfHistoryRepository;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.businesstype.repository.BusinessTypeOfEmployeeRepository;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.businesstype.repository.BusinessTypeOfHistoryGeneralRepository;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.primitivevalue.BusinessTypeCode;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.pereg.app.command.PeregUpdateCommandHandler;

@Stateless
public class UpdateBusinessWorkTypeOfHistoryCommandHandler
		extends CommandHandler<UpdateBusinessWorkTypeOfHistoryCommand>
		implements PeregUpdateCommandHandler<UpdateBusinessWorkTypeOfHistoryCommand> {

	@Inject
	private BusinessTypeOfEmployeeRepository typeOfEmployeeRepos;

	@Inject
	private BusinessTypeEmpOfHistoryRepository typeEmployeeOfHistoryRepos;

	@Inject
	private BusinessTypeOfHistoryGeneralRepository typeOfHistoryGeneralRepos;

	@Override
	public String targetCategoryCd() {
		return "CS00021";
	}

	@Override
	public Class<?> commandClass() {
		return UpdateBusinessWorkTypeOfHistoryCommand.class;
	}

	@Override
	protected void handle(CommandHandlerContext<UpdateBusinessWorkTypeOfHistoryCommand> context) {

		UpdateBusinessWorkTypeOfHistoryCommand command = context.getCommand();
		String employeeId = command.getEmployeeId();
		String businessTypeCode = command.getBusinessTypeCode();
		GeneralDate startDate = command.getStartDate();
		GeneralDate endDate = command.getEndDate();
		String historyId = command.getHistoryId();
		Optional<BusinessTypeOfEmployeeHistory> optional = typeEmployeeOfHistoryRepos.findByEmployee(employeeId);
		BusinessTypeOfEmployeeHistory bEmployeeHistory = new BusinessTypeOfEmployeeHistory();
		if (optional.isPresent()) {
			bEmployeeHistory = optional.get();
		} else {
			throw new BusinessException("No data to update!");
		}
		DateHistoryItem currentItem = new DateHistoryItem(historyId, new DatePeriod(startDate, endDate));
		typeOfHistoryGeneralRepos.updateBusinessTypeEmpOfHistory(bEmployeeHistory, currentItem);
		// update typeof employee
		if (typeOfEmployeeRepos.findByHistoryId(historyId).isPresent()) {
			BusinessTypeOfEmployee bEmployee = new BusinessTypeOfEmployee(new BusinessTypeCode(businessTypeCode),
					historyId, employeeId);
			typeOfEmployeeRepos.update(bEmployee);
		} else {
			throw new BusinessException("No data to update!");
		}

	}

}
