package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmplHealInsurQualifiInforRepository;
import nts.uk.shr.pereg.app.command.PeregDeleteCommandHandler;
import nts.uk.shr.pereg.app.command.PeregUpdateCommandHandler;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class DeleteEmpHealInsQualifiInfoCommandHandler
        extends CommandHandler<DeleteEmpHealInsQualifiInfoCommand>
        implements PeregDeleteCommandHandler<DeleteEmpHealInsQualifiInfoCommand> {
    @Inject
    private EmplHealInsurQualifiInforRepository emplHealInsurQualifiInforRepository;

    @Override
    public String targetCategoryCd(){
        return "CS00082";
    }

    @Override
    public Class<?> commandClass(){
        return DeleteEmpHealInsQualifiInfoCommand.class;
    }

    @Override
    protected void handle(CommandHandlerContext<DeleteEmpHealInsQualifiInfoCommand> context){
        val command = context.getCommand();
        emplHealInsurQualifiInforRepository.remove(command.getEmployeeId(), command.getHistoryId());
    }
}
