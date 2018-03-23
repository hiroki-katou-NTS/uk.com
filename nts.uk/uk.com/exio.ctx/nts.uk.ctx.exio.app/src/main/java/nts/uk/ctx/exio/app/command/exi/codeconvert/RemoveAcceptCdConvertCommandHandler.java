package nts.uk.ctx.exio.app.command.exi.codeconvert;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exi.codeconvert.AcceptCdConvertRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class RemoveAcceptCdConvertCommandHandler extends CommandHandler<AcceptCdConvertCommand> {

	@Inject
	private AcceptCdConvertRepository repository;

	@Override
	protected void handle(CommandHandlerContext<AcceptCdConvertCommand> context) {
		String cid = AppContexts.user().companyId();
		String convertCd = context.getCommand().getConvertCd();
		repository.remove(cid, convertCd);
	}
}
