package nts.uk.ctx.pr.core.app.command.salary;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.salary.DetailPrintingMon;
import nts.uk.ctx.pr.core.dom.salary.DetailPrintingMonRepository;

@Stateless
@Transactional
public class AddDetailPrintingMonCommandHandler extends CommandHandler<DetailPrintingMonCommand> {

	@Inject
	private DetailPrintingMonRepository repository;

	@Override
	protected void handle(CommandHandlerContext<DetailPrintingMonCommand> context) {
		DetailPrintingMonCommand addCommand = context.getCommand();
		repository.add(new DetailPrintingMon(addCommand.getProcessCateNo(), addCommand.getCid(),
				addCommand.getPrintingMonth()));

	}
}
