package nts.uk.ctx.exio.app.command.exi.condset;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exi.condset.StdAcceptCondSetRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.ctx.exio.dom.exi.condset.StdAcceptCondSet;
import nts.uk.ctx.exio.dom.exi.condset.StdAcceptCondSetService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class AddStdAcceptCondSetCommandHandler extends CommandHandler<StdAcceptCondSetCommand> {

	@Inject
	private StdAcceptCondSetService condsetService;

	@Override
	protected void handle(CommandHandlerContext<StdAcceptCondSetCommand> context) {
		StdAcceptCondSetCommand addCommand = context.getCommand();
		// 会社ＩＤ
		String companyId = AppContexts.user().companyId();
		StdAcceptCondSet domain = new StdAcceptCondSet(companyId, addCommand.getSystemType(),
				addCommand.getConditionSettingCode(), addCommand.getConditionSettingName(),
				addCommand.getDeleteExistData(), addCommand.getAcceptMode(), NotUseAtr.USE.value,
				addCommand.getCategoryId(), addCommand.getCsvDataItemLineNumber(), addCommand.getCsvDataStartLine(),
				addCommand.getDeleteExistDataMethod());
		this.condsetService.registerConditionSetting(domain);
	}
}
