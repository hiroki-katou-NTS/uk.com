package nts.uk.ctx.at.shared.app.command.workingcondition;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingCondition;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.pereg.app.command.PeregUpdateCommandHandler;

@Stateless
public class UpdateWorkingCondition2CommandHandler extends CommandHandler<UpdateWorkingCondition2Command>
		implements PeregUpdateCommandHandler<UpdateWorkingCondition2Command> {

	@Inject
	private WorkingConditionItemRepository workingConditionItemRepository;

	@Inject
	private WorkingConditionRepository workingConditionRepository;

	@Inject
	private UpdateWorkingConditionCommandAssembler updateWorkingConditionCommandAssembler;

	@Override
	public String targetCategoryCd() {
		return "CS00070";
	}

	@Override
	public Class<?> commandClass() {
		return UpdateWorkingCondition2Command.class;
	}

	@Override
	protected void handle(CommandHandlerContext<UpdateWorkingCondition2Command> context) {
		String companyId = AppContexts.user().companyId();
		UpdateWorkingCondition2Command command = context.getCommand();

		if (command.getStartDate() != null) {
			Optional<WorkingCondition> listHistBySid = workingConditionRepository.getBySid(companyId,
					command.getEmployeeId());

			if (!listHistBySid.isPresent()) {
				throw new RuntimeException("Invalid item to be updated");
			}

			WorkingCondition workingCond = listHistBySid.get();

			Optional<DateHistoryItem> itemToBeUpdated = workingCond.getDateHistoryItem().stream()
					.filter(hist -> hist.identifier().equals(command.getHistId())).findFirst();

			if (!itemToBeUpdated.isPresent()) {
				throw new RuntimeException("Invalid item to be updated");
			}

			GeneralDate endDate = command.getEndDate() != null ? command.getEndDate() : GeneralDate.max();
			workingCond.changeSpan(itemToBeUpdated.get(), new DatePeriod(command.getStartDate(), endDate));

			workingConditionRepository.save(workingCond);
		}

		WorkingConditionItem workingCondItem = updateWorkingConditionCommandAssembler.fromDTO2(command);

		workingConditionItemRepository.updateWorkCond2(workingCondItem);
	}
}
