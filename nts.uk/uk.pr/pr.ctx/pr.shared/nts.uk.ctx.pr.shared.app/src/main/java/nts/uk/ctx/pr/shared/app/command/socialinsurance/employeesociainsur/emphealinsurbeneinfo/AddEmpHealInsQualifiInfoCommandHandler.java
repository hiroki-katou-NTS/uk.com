package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmplHealInsurQualifiInfor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmplHealInsurQualifiInforRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.command.PeregAddCommandHandler;
import nts.uk.shr.pereg.app.command.PeregAddCommandResult;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class AddEmpHealInsQualifiInfoCommandHandler
        extends CommandHandlerWithResult<AddEmpHealInsQualifiInfoCommand, PeregAddCommandResult>
        implements PeregAddCommandHandler<AddEmpHealInsQualifiInfoCommand> {
    @Inject
    private EmplHealInsurQualifiInforRepository emplHealInsurQualifiInforRepository;

    @Override
    public String targetCategoryCd() {
        return "CS00082";
    }

    @Override
    public Class<?> commandClass() {
        return AddEmpHealInsQualifiInfoCommand.class;
    }

    @Override
    protected PeregAddCommandResult handle(CommandHandlerContext<AddEmpHealInsQualifiInfoCommand> context) {
        val command = context.getCommand();
        EmplHealInsurQualifiInfor domain = EmplHealInsurQualifiInfor.createFromJavaType(command.getEmployeeId(), command.getMourningPeriod());
        emplHealInsurQualifiInforRepository.add(domain);
        return new PeregAddCommandResult(command.getEmployeeId());
    }
}
