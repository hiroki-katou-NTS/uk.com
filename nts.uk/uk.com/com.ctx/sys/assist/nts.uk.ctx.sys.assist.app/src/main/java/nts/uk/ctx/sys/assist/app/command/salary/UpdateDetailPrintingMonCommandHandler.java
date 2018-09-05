package nts.uk.ctx.sys.assist.app.command.salary;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.assist.dom.salary.DetailPrintingMonRepository;
import nts.uk.ctx.sys.assist.dom.salary.DetailPrintingMon;

@Stateless
@Transactional
public class UpdateDetailPrintingMonCommandHandler extends CommandHandler<DetailPrintingMonCommand> {

	@Inject
	private DetailPrintingMonRepository repository;

	@Override
	protected void handle(CommandHandlerContext<DetailPrintingMonCommand> context) {
		DetailPrintingMonCommand updateCommand = context.getCommand();
		repository.update(new DetailPrintingMon(updateCommand.getProcessCateNo(), updateCommand.getCid(),
				updateCommand.getPrintingMonth()));

	}
}
