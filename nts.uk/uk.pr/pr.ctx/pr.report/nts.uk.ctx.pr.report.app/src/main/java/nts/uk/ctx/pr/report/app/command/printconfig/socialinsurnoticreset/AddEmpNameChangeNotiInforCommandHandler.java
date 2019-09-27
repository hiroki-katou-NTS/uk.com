package nts.uk.ctx.pr.report.app.command.printconfig.socialinsurnoticreset;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.EmpNameChangeNotiInfor;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.EmpNameChangeNotiInforRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Optional;

@Stateless
@Transactional
public class AddEmpNameChangeNotiInforCommandHandler extends CommandHandler<EmpNameChangeNotiInforCommand>
{

    @Inject
    private EmpNameChangeNotiInforRepository repository;

    @Override
    protected void handle(CommandHandlerContext<EmpNameChangeNotiInforCommand> context) {
        String cid = AppContexts.user().companyId();
        EmpNameChangeNotiInforCommand command = context.getCommand();
        Optional<EmpNameChangeNotiInfor> empNameChangeNotiInfor = repository.getEmpNameChangeNotiInforById(command.getEmployeeId(),cid);
        EmpNameChangeNotiInfor domain = new EmpNameChangeNotiInfor(command.getEmployeeId(),cid,command.getHealInsurPersonNoNeed(),command.getOther(),command.getOtherRemarks());
        if(empNameChangeNotiInfor.isPresent()){
            repository.update(domain);
        }else{
            repository.add(domain);
        }

    }
}
