package nts.uk.ctx.at.record.app.command.dailyperformanceformat.businesstype;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee.BusinessTypeOfEmployee;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee.BusinessTypeOfEmployeeHistory;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee.repository.BusinessTypeEmpOfHistoryRepository;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee.repository.BusinessTypeOfEmployeeRepository;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee.repository.BusinessTypeOfHistoryGeneralRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.arc.time.calendar.period.DatePeriod;
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
		String companyId = AppContexts.user().companyId();
		// Hop.NT update if end date is null set to maxDate
		GeneralDate endDate = command.getEndDate()!= null? command.getEndDate():GeneralDate.max();
		String historyId = command.getHistoryId();
		if (command.getStartDate() != null){
			Optional<BusinessTypeOfEmployeeHistory> optional = typeEmployeeOfHistoryRepos.findByEmployee(companyId,employeeId);
			if (!optional.isPresent()) {
				throw new BusinessException("No data to update!");
			}
			BusinessTypeOfEmployeeHistory bEmployeeHistory = optional.get();
			Optional<DateHistoryItem> optionalHisItem = bEmployeeHistory.getHistory().stream()
					.filter(x -> x.identifier().equals(historyId)).findFirst();
			if (!optionalHisItem.isPresent()) {
	
				throw new BusinessException("invalid TypeOfEmployeeHistory!");
			}
			bEmployeeHistory.changeSpan(optionalHisItem.get(), new DatePeriod(startDate, endDate));
			typeOfHistoryGeneralRepos.updateBusinessTypeEmpOfHistory(bEmployeeHistory, optionalHisItem.get());
		}
		// update typeof employee
		if (!typeOfEmployeeRepos.findByHistoryId(historyId).isPresent()) {
			throw new BusinessException("No data to update!");
		}
		BusinessTypeOfEmployee bEmployee = BusinessTypeOfEmployee.createFromJavaType(businessTypeCode, historyId,
				employeeId);
		typeOfEmployeeRepos.update(bEmployee);
	}

}
