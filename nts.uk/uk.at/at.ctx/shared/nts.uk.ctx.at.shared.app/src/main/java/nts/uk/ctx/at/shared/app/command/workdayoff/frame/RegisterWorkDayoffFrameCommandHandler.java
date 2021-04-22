package nts.uk.ctx.at.shared.app.command.workdayoff.frame;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrame;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameRepository;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameRole;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Stateless
public class RegisterWorkDayoffFrameCommandHandler extends CommandHandler<List<WorkDayoffRoleCmd>> {

    /** The repository. */
    @Inject
    private WorkdayoffFrameRepository repository;

    @Override
    protected void handle(CommandHandlerContext<List<WorkDayoffRoleCmd>> context) {

        String cid = AppContexts.user().companyId();

        List<WorkDayoffRoleCmd> roles = context.getCommand();

        List<WorkdayoffFrame> overtimeWorkFrames = repository.getAllWorkdayoffFrame(cid);

        Map<Integer, WorkDayoffRoleCmd> mapOvertimeWorkFrame = roles
                .stream().collect(Collectors.toMap(WorkDayoffRoleCmd::getBreakoutFrNo, i -> i));

        overtimeWorkFrames.forEach( i -> {
            WorkDayoffRoleCmd dto = mapOvertimeWorkFrame.get(i.getWorkdayoffFrNo().v().intValue());
            if (dto != null) {
                i.setRole(EnumAdaptor.valueOf(dto.getRoleOfOpenPeriodEnum(), WorkdayoffFrameRole.class));
            }
        });

        repository.updateAll(overtimeWorkFrames);

    }

}
