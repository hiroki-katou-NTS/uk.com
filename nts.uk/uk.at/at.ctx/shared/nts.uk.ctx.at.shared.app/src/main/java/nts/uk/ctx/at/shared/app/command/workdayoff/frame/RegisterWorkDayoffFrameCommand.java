package nts.uk.ctx.at.shared.app.command.workdayoff.frame;

import lombok.Data;

import java.util.List;

@Data
public class RegisterWorkDayoffFrameCommand {

    private List<WorkDayoffRoleCmd> roleFrameList;

}
