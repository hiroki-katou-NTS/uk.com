package nts.uk.ctx.exio.app.command.exi.condset;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exi.condset.StdAcceptCondSetCopyParam;
import nts.uk.ctx.exio.dom.exi.condset.StdAcceptCondSetService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class CopyStdAcceptCondSetCommandHandler extends CommandHandler<CopyStdAcceptCondSetCommand> {

	@Inject
	StdAcceptCondSetService stdAcceptCondSetService;

	@Override
	protected void handle(CommandHandlerContext<CopyStdAcceptCondSetCommand> context) {
		CopyStdAcceptCondSetCommand command = context.getCommand();
		// 会社ＩＤ
		String companyId = AppContexts.user().companyId();
		// Copy param
		StdAcceptCondSetCopyParam param = new StdAcceptCondSetCopyParam(companyId, command.getSystemType(),
				command.getSourceCondSetCode(), command.getDestCondSetCode(), command.getDestCondSetName(),
				command.isOverride());
		// Copy condition setting
		stdAcceptCondSetService.copyConditionSetting(param);
	}
}
