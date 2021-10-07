package nts.uk.ctx.at.record.app.command.kdw.kdw006.l;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.jobmanagement.tasksupplementaryinforitemsetting.TaskSupInfoChoicesHistory;
import nts.uk.ctx.at.record.dom.jobmanagement.tasksupplementaryinforitemsetting.TaskSupInfoChoicesHistoryRepository;
import nts.uk.shr.com.history.DateHistoryItem;

/**
 * Command: 履歴を追加する
 * UKDesign.UniversalK.就業.KDW_日別実績.KDW006_前準備.L：履歴の選択.メニュー別OCD.履歴を追加する
 * @author chungnt
 *
 */

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class AddHistoryCommandHander extends CommandHandler<AddHistoryCommand> {

	@Inject
	private TaskSupInfoChoicesHistoryRepository taskSupInfoChoicesHistoryRepo;

	@Override
	protected void handle(CommandHandlerContext<AddHistoryCommand> context) {
		AddHistoryCommand command = context.getCommand();

		List<DateHistoryItem> historys = new ArrayList<>();
		historys.add(DateHistoryItem.createNewHistory(new DatePeriod(command.getStartDate(), command.getEndDate())));

		TaskSupInfoChoicesHistory domain = new TaskSupInfoChoicesHistory(Integer.parseInt(command.getItemId()),
				historys);

		this.taskSupInfoChoicesHistoryRepo.insert(domain);
	}
}
