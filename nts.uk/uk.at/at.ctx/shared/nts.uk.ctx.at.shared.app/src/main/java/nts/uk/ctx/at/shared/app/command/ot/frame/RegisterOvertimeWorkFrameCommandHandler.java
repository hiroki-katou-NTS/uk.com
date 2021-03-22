package nts.uk.ctx.at.shared.app.command.ot.frame;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrame;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameRepository;
import nts.uk.ctx.at.shared.dom.ot.frame.RoleOvertimeWork;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * KMK013Q Ver 27 Update
 * Save OvertimeWorkFrame
 * @author Hai.tt
 */

@Stateless
public class RegisterOvertimeWorkFrameCommandHandler extends CommandHandler<List<OvertimeRoleCmd>> {

    /** The repository. */
    @Inject
    private OvertimeWorkFrameRepository repository;

    @Override
    protected void handle(CommandHandlerContext<List<OvertimeRoleCmd>> context) {

        // Get Company Id
        String companyId = AppContexts.user().companyId();

        // Get Command
        List<OvertimeRoleCmd> roles = context.getCommand();

        List<OvertimeWorkFrame> overtimeWorkFrames = repository.getAllOvertimeWorkFrame(companyId);

        Map<Integer, OvertimeRoleCmd> mapOvertimeWorkFrame = roles
                .stream().collect(Collectors.toMap(OvertimeRoleCmd::getOvertimeFrNo, i -> i));

        overtimeWorkFrames.forEach( i -> {
            OvertimeRoleCmd dto = mapOvertimeWorkFrame.get(i.getOvertimeWorkFrNo().v().intValue());
            if (dto != null) {
                i.setRole(EnumAdaptor.valueOf(dto.getRoleOTWorkEnum(), RoleOvertimeWork.class));
            }
        });

        repository.updateAll(overtimeWorkFrames);

    }

}
