package nts.uk.ctx.at.record.app.command.dailyperformanceformat.businesstype;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.businesstype.BusinessTypeOfEmployeeHistory;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.businesstype.repository.BusinessTypeEmpOfHistoryRepository;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.businesstype.repository.BusinessTypeOfEmployeeRepository;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.businesstype.repository.BusinessTypeOfHistoryGeneralRepository;
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
		Optional<BusinessTypeOfEmployeeHistory> optional = typeEmployeeOfHistoryRepos.findByEmployee(employeeId);
		BusinessTypeOfEmployeeHistory bEmployeeHistory = new BusinessTypeOfEmployeeHistory();
		if (optional.isPresent()) {
			bEmployeeHistory = optional.get();
		} else {
			throw new BusinessException("No data to update!");
		}
		Optional<DateHistoryItem> currentItem = bEmployeeHistory.getHistory().stream().filter(x -> {
			return x.identifier() == historyId;
		}).findFirst();
		if (currentItem.isPresent()) {
			bEmployeeHistory.remove(currentItem.get());
			typeOfHistoryGeneralRepos.deleteBusinessTypeEmpOfHistory(bEmployeeHistory, currentItem.get());
		}
		// update typeof employee
		if (typeOfEmployeeRepos.findByHistoryId(historyId).isPresent()) {
			typeOfEmployeeRepos.delete(historyId);
		} else {
			throw new BusinessException("No data to update!");
		}
	}

}
