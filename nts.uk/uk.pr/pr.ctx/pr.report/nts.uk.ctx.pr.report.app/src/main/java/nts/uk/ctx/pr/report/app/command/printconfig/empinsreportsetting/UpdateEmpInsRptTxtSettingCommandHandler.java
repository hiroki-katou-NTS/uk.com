package nts.uk.ctx.pr.report.app.command.printconfig.empinsreportsetting;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.EmpInsReportTxtSetting;
import nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.EmpInsReportTxtSettingRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Stateless
@Transactional
public class UpdateEmpInsRptTxtSettingCommandHandler extends CommandHandler<EmpInsRptTxtSettingCommand> {

    @Inject
    private EmpInsReportTxtSettingRepository repository;

    @Override
    protected void handle(CommandHandlerContext<EmpInsRptTxtSettingCommand> commandHandlerContext) {
        EmpInsRptTxtSettingCommand command = commandHandlerContext.getCommand();
        repository.update(new EmpInsReportTxtSetting(AppContexts.user().companyId(), AppContexts.user().userId(), command.getOfficeAtr(), command.getFdNumber(), command.getLineFeedCode()));
    }
}
