package nts.uk.ctx.at.request.app.command.application.timeleaveapplication;

import lombok.Data;
import nts.uk.ctx.at.request.app.command.application.common.ApplicationInsertCmd;
import nts.uk.ctx.at.request.app.find.application.timeleaveapplication.dto.TimeLeaveAppDisplayInfoDto;

@Data
public class RegisterTimeLeaveApplicationCommand {

    private TimeLeaveApplicationCommand timeLeaveApplicationCommand;

    private TimeLeaveAppDisplayInfoDto timeLeaveAppDisplayInfo;

    // 申請
    private ApplicationInsertCmd application;


}
