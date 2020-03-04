package nts.uk.ctx.pr.report.app.command.printconfig.empinsreportsetting;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.EmpInsReportSetting;
import nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.EmpInsReportSettingRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Stateless
@Transactional
public class UpdateEmpInsRptSettingCommandHandler extends CommandHandler<EmpInsRptSettingCommand>{

    @Inject
    private EmpInsReportSettingRepository repository;

    @Override
    protected void handle(CommandHandlerContext<EmpInsRptSettingCommand> commandHandlerContext) {
        EmpInsRptSettingCommand command = commandHandlerContext.getCommand();
        repository.update(new EmpInsReportSetting(AppContexts.user().companyId(), AppContexts.user().userId(), command.getSubmitNameAtr(), command.getOutputOrderAtr(), command.getOfficeClsAtr(), command.getMyNumberClsAtr(), command.getNameChangeClsAtr()));
    }
}
