package nts.uk.ctx.at.request.app.command.application.timeleaveapplication;

import lombok.Data;
import nts.uk.ctx.at.request.app.command.application.common.ApplicationUpdateCmd;
import nts.uk.ctx.at.request.app.command.application.common.CreateApplicationCommand;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.timeleaveapplication.dto.TimeLeaveAppDisplayInfo;

@Data
public class UpdateTimeLeaveApplicationCommand {

    private TimeLeaveApplicationCommand timeLeaveApplicationCommand;

    private TimeLeaveAppDisplayInfo timeLeaveAppDisplayInfo;

    // 申請
    private ApplicationUpdateCmd application;


}
