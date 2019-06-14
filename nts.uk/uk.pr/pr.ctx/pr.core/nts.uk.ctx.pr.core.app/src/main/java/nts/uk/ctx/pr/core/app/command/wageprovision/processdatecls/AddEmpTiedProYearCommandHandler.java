package nts.uk.ctx.pr.core.app.command.wageprovision.processdatecls;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.EmpTiedProYear;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.EmpTiedProYearRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.EmploymentCode;

import java.util.stream.Collectors;

@Stateless
@Transactional
public class AddEmpTiedProYearCommandHandler extends CommandHandler<EmpTiedProYearCommand>
{
    
    @Inject
    private EmpTiedProYearRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<EmpTiedProYearCommand> context) {
        EmpTiedProYearCommand addCommand = context.getCommand();


        repository.add(new EmpTiedProYear(addCommand.getCid(), addCommand.getProcessCateNo(), addCommand.getEmploymentCodes().stream().map(item -> new EmploymentCode(item)).collect(Collectors.toList())));
    
    }
}
