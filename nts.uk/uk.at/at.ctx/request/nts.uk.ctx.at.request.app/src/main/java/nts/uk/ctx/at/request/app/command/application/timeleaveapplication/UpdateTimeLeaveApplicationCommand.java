package nts.uk.ctx.at.request.app.command.application.timeleaveapplication;

import lombok.Data;
import nts.uk.ctx.at.request.app.command.application.common.ApplicationUpdateCmd;
import nts.uk.ctx.at.request.app.find.application.timeleaveapplication.dto.TimeLeaveAppDisplayInfoDto;

import java.util.List;

@Data
public class UpdateTimeLeaveApplicationCommand {

    private TimeLeaveAppDisplayInfoDto timeLeaveAppDisplayInfo;

    // 申請
    private ApplicationUpdateCmd application;

    // 時間休暇申請. 詳細
    private List<TimeLeaveAppDetailCommand> details;
}
