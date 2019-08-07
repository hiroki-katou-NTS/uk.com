package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.empcomofficehis;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.EmpCorpHealthOffHisRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Stateless
@Transactional
public class RemoveEmpCorpHealthOffHisCommandHandler extends CommandHandler<EmpCorpHealthOffHisCommand>
{
    
    @Inject
    private EmpCorpHealthOffHisRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<EmpCorpHealthOffHisCommand> context) {

    }
}
