package nts.uk.ctx.at.shared.app.command.worktime.workplace;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.workplace.WorkTimeWorkplace;
import nts.uk.ctx.at.shared.dom.worktime.workplace.WorkTimeWorkplaceRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 就業時間帯の割り付けを更新する
 */
@Stateless
@Transactional
public class UpdateWorkTimeWorkplaceCommandHandler extends CommandHandler<RegisterWorkTimeWorkplaceCommand> {
    @Inject
    private WorkTimeWorkplaceRepository repository;

    @Override
    protected void handle(CommandHandlerContext<RegisterWorkTimeWorkplaceCommand> context) {

        RegisterWorkTimeWorkplaceCommand command = context.getCommand();

        String cid = AppContexts.user().companyId();
        Optional<WorkTimeWorkplace> oldWorkTimeWorkplace = repository.getByCIdAndWkpId(cid, command.getWorkplaceId());

        // 1 : delete(ログイン会社ID,職場割り当て就業時間帯. 職場ID)
        oldWorkTimeWorkplace.ifPresent(workTimeWorkplace -> repository.remove(workTimeWorkplace));

        // 2 : create()
        WorkTimeWorkplace workTimeWorkplace = new WorkTimeWorkplace(
            AppContexts.user().companyId(),
            command.getWorkplaceId(),
            command.getWorkTimeCodes().stream().map(WorkTimeCode::new).collect(Collectors.toList())
        );

        repository.add(workTimeWorkplace);
    }
}
