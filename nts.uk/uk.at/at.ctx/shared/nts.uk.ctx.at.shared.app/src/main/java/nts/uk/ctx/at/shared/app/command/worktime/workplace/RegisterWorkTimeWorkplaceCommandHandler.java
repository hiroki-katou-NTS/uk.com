package nts.uk.ctx.at.shared.app.command.worktime.workplace;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.workplace.WorkTimeWorkplace;
import nts.uk.ctx.at.shared.dom.worktime.workplace.WorkTimeWorkplaceRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.stream.Collectors;

/**
 * 職場割り当て就業時間帯を登録する
 */
@Stateless
public class RegisterWorkTimeWorkplaceCommandHandler extends CommandHandler<RegisterWorkTimeWorkplaceCommand> {
    @Inject
    private WorkTimeWorkplaceRepository repository;

    @Override
    protected void handle(CommandHandlerContext<RegisterWorkTimeWorkplaceCommand> context) {

        RegisterWorkTimeWorkplaceCommand command = context.getCommand();

        WorkTimeWorkplace workTimeWorkplace = new WorkTimeWorkplace(
            AppContexts.user().companyId(),
            command.getWorkplaceId(),
            command.getWorkTimeCodes().stream().map(WorkTimeCode::new).collect(Collectors.toList())
        );

        repository.add(workTimeWorkplace);
    }
}
