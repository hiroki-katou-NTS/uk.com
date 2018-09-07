package nts.uk.ctx.pr.core.app.command.wageprovision.processdatecls;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.DetailPrintingMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.DetailPrintingMonRepository;

@Stateless
@Transactional
public class AddDetailPrintingMonCommandHandler extends CommandHandler<DetailPrintingMonCommand> {

	@Inject
	private DetailPrintingMonRepository repository;

	@Override
	protected void handle(CommandHandlerContext<DetailPrintingMonCommand> context) {
		DetailPrintingMonCommand addCommand = context.getCommand();
		repository.add(new DetailPrintingMonth(addCommand.getPrintingMonth()));

	}
}
