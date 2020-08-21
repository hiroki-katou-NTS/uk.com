package nts.uk.ctx.at.schedule.app.command.shift.workcycle;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.app.command.shift.workcycle.command.DeleteWorkCycleCommand;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycle;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycleRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

/**
 * 勤務サイクルを削除する
 */
@Stateless
public class DeleteWorkCycleCommandHandler extends CommandHandler<DeleteWorkCycleCommand> {

    @Inject
    private WorkCycleRepository workCycleRepository;

    @Override
    protected void handle(CommandHandlerContext<DeleteWorkCycleCommand> context) {
        DeleteWorkCycleCommand command = context.getCommand();
        String cid = AppContexts.user().companyId();
        String code = command.getWorkCycleCode();
        Optional<WorkCycle> workingCycle = workCycleRepository.getByCidAndCode(cid, code);
        if (workingCycle.isPresent()) {
            workCycleRepository.delete(cid, code);
        }
    }
}
