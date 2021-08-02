package nts.uk.ctx.at.shared.app.command.employeeworkway.medicalworkstyle;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.EmpMedicalWorkStyleHistory;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.EmpMedicalWorkStyleHistoryRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.pereg.app.command.PeregDeleteCommandHandler;

@Stateless
public class DeleteEmpMedicalWorkCommandHandler extends CommandHandler<DeleteEmpMedicalWorkCommand>
	implements PeregDeleteCommandHandler<DeleteEmpMedicalWorkCommand> {

	@Inject
	private EmpMedicalWorkStyleHistoryRepository emwHistRepo;
	
	@Override
	public String targetCategoryCd() {
		return "CS00098";
	}

	@Override
	public Class<?> commandClass() {
		return DeleteEmpMedicalWorkCommand.class;
	}

	@Override
	protected void handle(CommandHandlerContext<DeleteEmpMedicalWorkCommand> context) {
		DeleteEmpMedicalWorkCommand command = context.getCommand();
		String cid = AppContexts.user().companyId();
		
		Optional<EmpMedicalWorkStyleHistory> emwHistOp = emwHistRepo.getHistBySid(cid, command.getEmployeeId());
		
		if (!emwHistOp.isPresent()){
			throw new RuntimeException("Invalid EmployeeUnitPriceHistory"); 
		}
		
		Optional<DateHistoryItem> itemToBeDeleted = emwHistOp.get().getListDateHistoryItem().stream()
                .filter(h -> h.identifier().equals(command.getHistId()))
                .findFirst();
		
		if (!itemToBeDeleted.isPresent()){
			throw new RuntimeException("Invalid EmployeeUnitPriceHistory");
		}
		
		emwHistOp.get().remove(itemToBeDeleted.get());
		
		emwHistRepo.delete(command.getEmployeeId(), command.getHistId());
		
		if (emwHistOp.get().getListDateHistoryItem().size() > 0) {
			List<DateHistoryItem> histItemList = emwHistOp.get().getListDateHistoryItem();
			Comparator<DateHistoryItem> compareByStartDate = 
    				(DateHistoryItem hist1, DateHistoryItem hist2) 
    				-> hist1.start().compareTo(hist2.start());
    		Collections.sort(histItemList, compareByStartDate);
			
			DateHistoryItem itemToBeUpdated = histItemList.get(histItemList.size() - 1);
			GeneralDate endDate = GeneralDate.max();
			DateHistoryItem itemUpdate = new DateHistoryItem(itemToBeUpdated.identifier(), new DatePeriod(itemToBeUpdated.start(), endDate));
			emwHistOp.get().getListDateHistoryItem().set(histItemList.size() - 1, itemUpdate);
			emwHistRepo.update(emwHistOp.get());
		}
	}
}
