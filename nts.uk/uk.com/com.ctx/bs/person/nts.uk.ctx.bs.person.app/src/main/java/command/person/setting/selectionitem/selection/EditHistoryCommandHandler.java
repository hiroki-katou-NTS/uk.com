package command.person.setting.selectionitem.selection;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.person.dom.person.setting.selectionitem.PerInfoHistorySelection;
import nts.uk.ctx.bs.person.dom.person.setting.selectionitem.PerInfoHistorySelectionRepository;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 
 * @author tuannv
 *
 */
@Stateless
public class EditHistoryCommandHandler extends CommandHandler<EditHistoryCommand> {

	@Inject
	private PerInfoHistorySelectionRepository historySelectionRepository;

	@Override
	protected void handle(CommandHandlerContext<EditHistoryCommand> context) {
		EditHistoryCommand command = context.getCommand();

		// Kim tra loi lich su: アルゴリズム「履歴エラーチェック」を実行する
		// TODO:

		// Cap nhat domain: ドメインモデル「選択肢履歴」を更新する
		GeneralDate startDate = command.getStartDate();
		GeneralDate endDate = GeneralDate.ymd(9999, 12, 31);
		DatePeriod period = new DatePeriod(startDate, endDate);
		PerInfoHistorySelection domain = PerInfoHistorySelection.createHistorySelection(
				command.getHistId(), command.getSelectionItemId(), command.getCompanyCode(), period);
		
		this.historySelectionRepository.update(domain);
	}

}
