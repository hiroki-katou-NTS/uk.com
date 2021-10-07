package nts.uk.ctx.at.record.app.command.kdw.kdw006.l;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.jobmanagement.tasksupplementaryinforitemsetting.TaskSupInfoChoicesHistory;
import nts.uk.ctx.at.record.dom.jobmanagement.tasksupplementaryinforitemsetting.TaskSupInfoChoicesHistoryRepository;

/**
 * Command: 履歴を更新する
 * UKDesign.UniversalK.就業.KDW_日別実績.KDW006_前準備.L：履歴の選択.メニュー別OCD.履歴を更新する
 * 
 * @author chungnt
 *
 */

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class UpdateHistoryCommandHandler extends CommandHandler<AddHistoryCommand> {

	@Inject
	private TaskSupInfoChoicesHistoryRepository taskSupInfoChoicesHistoryRepo;

	@Override
	protected void handle(CommandHandlerContext<AddHistoryCommand> context) {
		AddHistoryCommand command = context.getCommand();

		Optional<TaskSupInfoChoicesHistory> domain = taskSupInfoChoicesHistoryRepo.get(command.getHistoryId(),
				Integer.valueOf(command.getItemId()));

		if (domain.isPresent()) {
			TaskSupInfoChoicesHistory result = domain.get();
			result.getDateHistoryItems().get(0).newSpan(command.getStartDate(), command.getEndDate());
			this.taskSupInfoChoicesHistoryRepo.update(result);
		}
	}
}
