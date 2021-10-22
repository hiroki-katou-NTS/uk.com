package nts.uk.ctx.exio.app.command.exi.condset;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exi.condset.AcceptMode;
import nts.uk.ctx.exio.dom.exi.condset.AcceptanceConditionCode;
import nts.uk.ctx.exio.dom.exi.condset.AcceptanceConditionName;
import nts.uk.ctx.exio.dom.exi.condset.AcceptanceLineNumber;
import nts.uk.ctx.exio.dom.exi.condset.DeleteExistDataMethod;
import nts.uk.ctx.exio.dom.exi.condset.StdAcceptCondSet;
import nts.uk.ctx.exio.dom.exi.condset.StdAcceptCondSetService;
import nts.uk.ctx.exio.dom.exi.condset.SystemType;
import nts.uk.ctx.exio.dom.exi.csvimport.ExiCharset;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

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
		StdAcceptCondSet domain = addCommand.toDomain(companyId);
		this.condsetService.registerConditionSetting(domain);
	}
}
