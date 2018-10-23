package nts.uk.ctx.pr.core.app.command.wageprovision.orginfo.salarycls.salaryclsmaster;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.orginfo.salarycls.salaryclsmaster.SalaryClsInfo;
import nts.uk.ctx.pr.core.dom.wageprovision.orginfo.salarycls.salaryclsmaster.SalaryClsInfoRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class UpdateSalaryClsInfoCommandHandler extends CommandHandler<UpdateSalaryClsInfoCommand> {

    @Inject
    private SalaryClsInfoRepository repository;

    @Override
    protected void handle(CommandHandlerContext<UpdateSalaryClsInfoCommand> context) {
        SalaryClsInfo salaryClsInfo = context.getCommand().toDomain();
        repository.update(salaryClsInfo);
    }
}
