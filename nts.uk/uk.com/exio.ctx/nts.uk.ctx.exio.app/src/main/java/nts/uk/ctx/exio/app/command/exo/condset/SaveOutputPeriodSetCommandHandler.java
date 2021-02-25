package nts.uk.ctx.exio.app.command.exo.condset;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exo.condset.OutputPeriodSetting;
import nts.uk.ctx.exio.dom.exo.condset.OutputPeriodSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 「出力期間設定」に更新登録する
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class SaveOutputPeriodSetCommandHandler extends CommandHandler<SaveOutputPeriodSetCommand> {

	@Inject
	private OutputPeriodSettingRepository repo;

	@Override
	protected void handle(CommandHandlerContext<SaveOutputPeriodSetCommand> context) {
		SaveOutputPeriodSetCommand command = context.getCommand();
		String cId = AppContexts.user().companyId();
		command.setCid(cId);
		OutputPeriodSetting domain = OutputPeriodSetting.createFromMemento(command);
		if (command.getIsNew()) {
			this.repo.add(domain);
		} else {
			this.repo.update(cId, command.getConditionSetCode(), domain);
		}
	}

}
