package nts.uk.ctx.at.request.app.command.application.timeleaveapplication;

import lombok.Data;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.output.TimeLeaveApplicationOutput;

@Data
public class RegisterTimeLeaveApplicationCommand {

    private TimeLeaveApplicationCommand timeLeaveApplicationCommand;


    private TimeLeaveApplicationOutput timeLeaveApplicationOutput;

    // 申請
    private ApplicationDto application;


}
