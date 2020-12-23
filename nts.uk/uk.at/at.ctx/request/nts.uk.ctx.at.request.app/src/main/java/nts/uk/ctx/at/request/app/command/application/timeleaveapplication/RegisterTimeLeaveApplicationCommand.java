package nts.uk.ctx.at.request.app.command.application.timeleaveapplication;

import lombok.Data;
import nts.uk.ctx.at.request.app.command.application.businesstrip.BusinessTripInfoOutputCommand;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;

@Data
public class RegisterTimeLeaveApplicationCommand {

    private TimeLeaveApplicationCommand timeLeaveApplicationCommand;

    // 申請
    private ApplicationDto application;


}
