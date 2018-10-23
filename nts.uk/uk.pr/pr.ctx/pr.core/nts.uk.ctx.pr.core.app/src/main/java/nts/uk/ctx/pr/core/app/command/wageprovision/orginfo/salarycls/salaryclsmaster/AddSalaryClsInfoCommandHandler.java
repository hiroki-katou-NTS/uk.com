package nts.uk.ctx.pr.core.app.command.wageprovision.orginfo.salarycls.salaryclsmaster;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.orginfo.salarycls.salaryclsmaster.SalaryClsInfo;
import nts.uk.ctx.pr.core.dom.wageprovision.orginfo.salarycls.salaryclsmaster.SalaryClsInfoRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.orginfo.salarycls.salaryclsmaster.CompanyId;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class AddSalaryClsInfoCommandHandler extends CommandHandler<AddSalaryClsInfoCommand> {

    @Inject
    private SalaryClsInfoRepository repository;

    @Override
    protected void handle(CommandHandlerContext<AddSalaryClsInfoCommand> context) {
        SalaryClsInfo salaryClsInfo = context.getCommand().toDomain();
        String companyId = AppContexts.user().companyId();
        salaryClsInfo.setCompanyId(new CompanyId(companyId));
        salaryClsInfo.validate();
        Optional<SalaryClsInfo> salaryClsInfoOptional = repository.get(salaryClsInfo.getSalaryClsCode().v());
        if(salaryClsInfoOptional.isPresent()) {
            throw new BusinessException("Msg_03");
        } else {
            repository.insert(salaryClsInfo);
        }
    }
}
