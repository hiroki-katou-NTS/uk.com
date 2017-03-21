package nts.uk.ctx.pr.report.app.salarydetail.command;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.report.dom.salarydetail.SalaryPrintSetting;
import nts.uk.ctx.pr.report.dom.salarydetail.SalaryPrintSettingRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class SaveSalaryPrintSettingCommandHandler extends CommandHandler<SaveSalaryPrintSettingCommand> {

	@Inject
	private SalaryPrintSettingRepository repository;

	@Override
	protected void handle(CommandHandlerContext<SaveSalaryPrintSettingCommand> context) {
		SaveSalaryPrintSettingCommand command = context.getCommand();
		String companyCode = AppContexts.user().companyCode();
		SalaryPrintSetting salaryPrintSetting = command.toDomain();
		repository.save(salaryPrintSetting);
	}
}
