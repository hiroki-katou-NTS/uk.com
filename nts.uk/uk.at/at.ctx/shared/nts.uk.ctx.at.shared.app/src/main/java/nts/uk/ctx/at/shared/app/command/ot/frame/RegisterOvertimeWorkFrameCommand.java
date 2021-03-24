package nts.uk.ctx.at.shared.app.command.ot.frame;

import lombok.Data;

import java.util.List;

@Data
public class RegisterOvertimeWorkFrameCommand {

    private List<OvertimeRoleCmd> overtimeList;

}
