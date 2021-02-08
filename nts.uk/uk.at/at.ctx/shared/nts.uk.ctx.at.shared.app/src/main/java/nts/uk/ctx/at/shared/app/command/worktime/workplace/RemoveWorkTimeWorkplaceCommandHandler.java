package nts.uk.ctx.at.shared.app.command.worktime.workplace;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.worktime.workplace.WorkTimeWorkplace;
import nts.uk.ctx.at.shared.dom.worktime.workplace.WorkTimeWorkplaceRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

/**
 * 職場割り当て就業時間帯を削除する
 */
@Stateless
public class RemoveWorkTimeWorkplaceCommandHandler extends CommandHandler<RemoveWorkTimeWorkplaceCommand> {
    @Inject
    private WorkTimeWorkplaceRepository repository;

    @Override
    protected void handle(CommandHandlerContext<RemoveWorkTimeWorkplaceCommand> context) {

        RemoveWorkTimeWorkplaceCommand command = context.getCommand();

        String cid = AppContexts.user().companyId();
        Optional<WorkTimeWorkplace> workTimeWorkplace = repository.getByCIdAndWkpId(cid, command.getWorkplaceId());

        workTimeWorkplace.ifPresent(x -> repository.remove(x));
    }
}
