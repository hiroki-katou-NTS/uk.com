package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.empsocialinsgradehis;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis.*;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.pereg.app.command.PeregDeleteCommandHandler;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.swing.text.html.Option;
import java.util.Optional;

@Stateless
public class DeleteEmpSocialInsGradeInforCommandHandler
        extends CommandHandler<DeleteEmpSocialInsGradeInforCommand>
        implements PeregDeleteCommandHandler<DeleteEmpSocialInsGradeInforCommand> {

    @Inject
    private EmpSocialInsGradeRepository repository;

    @Inject
    private EmpSocialInsGradeService service;

    @Override
    public String targetCategoryCd() {
        return "CS00092";
    }

    @Override
    public Class<?> commandClass() {
        return DeleteEmpSocialInsGradeInforCommand.class;
    }

    @Override
    protected void handle(CommandHandlerContext<DeleteEmpSocialInsGradeInforCommand> context) {
        val command = context.getCommand();
        String companyId = AppContexts.user().companyId();

        EmpSocialInsGrade empSocialInsGrade = repository.getByEmpId(companyId, command.getSId())
                .orElseThrow(() -> new RuntimeException("invalid EmpSocialInsGradeHis"));

        YearMonthHistoryItem itemToBeDeleted = empSocialInsGrade.getHistory().items().stream()
                .filter(h -> h.identifier().equals(command.getHistoryId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("invalid EmpSocialInsGradeHis"));

        empSocialInsGrade.getHistory().remove(itemToBeDeleted);
        repository.delete(companyId, empSocialInsGrade.getHistory().getEmployeeId(), itemToBeDeleted.identifier());
    }
}
