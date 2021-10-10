package nts.uk.ctx.at.record.app.command.kdw.kdw006.l;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.jobmanagement.tasksupplementaryinforitemsetting.TaskSupInfoChoicesDetail;
import nts.uk.ctx.at.record.dom.jobmanagement.tasksupplementaryinforitemsetting.TaskSupInfoChoicesHistoryRepository;

/**
 * Command: 履歴を削除する
 * UKDesign.UniversalK.就業.KDW_日別実績.KDW006_前準備.L：履歴の選択.メニュー別OCD.履歴を削除する
 * 
 * @author chungnt
 *
 */

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class DeleteHistoryCommandHandler extends CommandHandler<DeleteHistoryCommand> {

	@Inject
	private TaskSupInfoChoicesHistoryRepository taskSupInfoChoicesHistoryRepo;

	@Override
	protected void handle(CommandHandlerContext<DeleteHistoryCommand> context) {
		DeleteHistoryCommand command = context.getCommand();

		List<TaskSupInfoChoicesDetail> details = this.taskSupInfoChoicesHistoryRepo.get(command.getHistoryId());

		if (!details.isEmpty()) {
			throw new BusinessException("履歴を削除する");
		}

		this.taskSupInfoChoicesHistoryRepo.delete(command.getHistoryId());
	}
}
