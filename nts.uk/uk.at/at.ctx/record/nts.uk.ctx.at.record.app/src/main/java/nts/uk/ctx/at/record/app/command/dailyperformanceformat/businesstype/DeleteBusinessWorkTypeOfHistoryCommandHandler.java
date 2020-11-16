package nts.uk.ctx.at.record.app.command.dailyperformanceformat.businesstype;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee.BusinessTypeOfEmployeeHistory;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee.repository.BusinessTypeEmpOfHistoryRepository;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee.repository.BusinessTypeOfEmployeeRepository;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee.repository.BusinessTypeOfHistoryGeneralRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.pereg.app.command.PeregDeleteCommandHandler;

@Stateless
public class DeleteBusinessWorkTypeOfHistoryCommandHandler
		extends CommandHandler<DeleteBusinessWorkTypeOfHistoryCommand>
		implements PeregDeleteCommandHandler<DeleteBusinessWorkTypeOfHistoryCommand> {

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
		return DeleteBusinessWorkTypeOfHistoryCommand.class;
	}

	@Override
	protected void handle(CommandHandlerContext<DeleteBusinessWorkTypeOfHistoryCommand> context) {
		DeleteBusinessWorkTypeOfHistoryCommand command = context.getCommand();
		String employeeId = command.getEmployeeId();
		String historyId = command.getHistoryId();
		String companyId = AppContexts.user().companyId();
		
		Optional<BusinessTypeOfEmployeeHistory> optional = typeEmployeeOfHistoryRepos.findByEmployee(companyId,employeeId);
		
		if (!optional.isPresent()) {
			throw new BusinessException("No data to update!");
		}
		
		BusinessTypeOfEmployeeHistory bEmployeeHistory = optional.get();
		
		Optional<DateHistoryItem> currentItem = bEmployeeHistory.getHistory().stream().filter(x -> {
			return x.identifier().equals(historyId);
		}).findFirst();
		if (currentItem.isPresent()) {
			bEmployeeHistory.remove(currentItem.get());
			typeOfHistoryGeneralRepos.deleteBusinessTypeEmpOfHistory(bEmployeeHistory, currentItem.get());
		}
		// Delete typeof employee
		if (typeOfEmployeeRepos.findByHistoryId(historyId).isPresent()) {
			typeOfEmployeeRepos.delete(historyId);
		} else {
			throw new BusinessException("No data to delete!");
		}
	}

}
