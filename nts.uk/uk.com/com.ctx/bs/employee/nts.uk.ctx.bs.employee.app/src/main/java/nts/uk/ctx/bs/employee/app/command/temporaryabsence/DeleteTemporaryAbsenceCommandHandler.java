package nts.uk.ctx.bs.employee.app.command.temporaryabsence;


import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsenceHistory;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsHistRepository;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsItemRepository;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.pereg.app.command.PeregDeleteCommandHandler;

@Stateless
public class DeleteTemporaryAbsenceCommandHandler extends CommandHandler<DeleteTemporaryAbsenceCommand>
	implements PeregDeleteCommandHandler<DeleteTemporaryAbsenceCommand>{

	@Inject
	private TempAbsItemRepository temporaryAbsenceRepository;
	
	@Inject
	private TempAbsHistRepository temporaryAbsenceHistRepository;
	
	@Override
	public String targetCategoryCd() {
		return "CS00018";
	}

	@Override
	public Class<?> commandClass() {
		return DeleteTemporaryAbsenceCommand.class;
	}

	@Override
	protected void handle(CommandHandlerContext<DeleteTemporaryAbsenceCommand> context) {
		val command = context.getCommand();
		
		Optional<TempAbsenceHistory> existHist = temporaryAbsenceHistRepository.getByEmployeeId(command.getEmployeeId());
		
		if (!existHist.isPresent()){
			throw new RuntimeException("invalid TempAbsenceHistory"); 
		}
		Optional<DateHistoryItem> itemToBeDelete = existHist.get().getDateHistoryItems().stream()
                .filter(h -> h.identifier().equals(command.getHistoyId()))
                .findFirst();
		
		if (!itemToBeDelete.isPresent()){
			throw new RuntimeException("invalid TempAbsenceHistory");
		}
		existHist.get().remove(itemToBeDelete.get());
		temporaryAbsenceHistRepository.deleteTemporaryAbsenceHist(existHist.get(),itemToBeDelete.get());
		
		temporaryAbsenceRepository.deleteTemporaryAbsence(command.getHistoyId());
	}

}
