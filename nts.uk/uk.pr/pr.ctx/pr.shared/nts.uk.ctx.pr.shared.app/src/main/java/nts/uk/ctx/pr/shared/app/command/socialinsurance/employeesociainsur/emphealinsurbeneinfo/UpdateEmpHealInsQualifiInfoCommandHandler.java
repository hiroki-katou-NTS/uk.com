package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmplHealInsurQualifiInfor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmplHealInsurQualifiInforRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.command.PeregUpdateCommandHandler;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class UpdateEmpHealInsQualifiInfoCommandHandler
        extends CommandHandler<UpdateEmpHealInsQualifiInfoCommand>
        implements PeregUpdateCommandHandler<UpdateEmpHealInsQualifiInfoCommand> {
    @Inject
    private EmplHealInsurQualifiInforRepository emplHealInsurQualifiInforRepository;

    @Override
    public String targetCategoryCd() {
        return "CS00082";
    }

    @Override
    public Class<?> commandClass() {
        return UpdateEmpHealInsQualifiInfoCommand.class;
    }

    @Override
    protected void handle(CommandHandlerContext<UpdateEmpHealInsQualifiInfoCommand> context) {
        val command = context.getCommand();
        EmplHealInsurQualifiInfor domain = new EmplHealInsurQualifiInfor(command.getEmployeeId(), command.getMourningPeriod());
        emplHealInsurQualifiInforRepository.update(domain);
    }
}
