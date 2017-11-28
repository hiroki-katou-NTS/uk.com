package nts.uk.ctx.bs.employee.app.command.department;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.employee.dom.department.affiliate.AffDepartmentHistory;
import nts.uk.ctx.bs.employee.dom.department.affiliate.AffDepartmentHistoryItemRepository;
import nts.uk.ctx.bs.employee.dom.department.affiliate.AffDepartmentHistoryRepository;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.pereg.app.command.PeregDeleteCommandHandler;

@Stateless
public class DeleteAffiliationDepartmentCommandHandler extends CommandHandler<DeleteAffiliationDepartmentCommand>
	implements PeregDeleteCommandHandler<DeleteAffiliationDepartmentCommand>{

	@Inject
	private AffDepartmentHistoryRepository affDepartmentHistoryRepository;
	
	@Inject
	private AffDepartmentHistoryItemRepository affDepartmentHistoryItemRepository;
	
	
	@Override
	public String targetCategoryCd() {
		return "CS00015";
	}

	@Override
	public Class<?> commandClass() {
		return DeleteAffiliationDepartmentCommand.class;
	}

	@Override
	protected void handle(CommandHandlerContext<DeleteAffiliationDepartmentCommand> context) {
		val command = context.getCommand();
		Optional<AffDepartmentHistory> itemHist = affDepartmentHistoryRepository.getAffDepartmentHistorytByEmployeeId(command.getEmployeeId());
		if (!itemHist.isPresent()){
			throw new RuntimeException("Invalid AffDepartmentHistory");
		}
		Optional<DateHistoryItem> itemToBeRemoved = itemHist.get().getHistoryItems().stream()
				.filter(d->d.identifier().equals(command.getHistoryId())).findFirst();
		
		if (!itemToBeRemoved.isPresent()){
			throw new RuntimeException("Invalid AffDepartmentHistory");
		}
		itemHist.get().remove(itemToBeRemoved.get());
		affDepartmentHistoryRepository.deleteAffDepartment(itemHist.get(), itemToBeRemoved.get());
		
		affDepartmentHistoryItemRepository.deleteAffDepartment(command.getHistoryId());
	}

}
