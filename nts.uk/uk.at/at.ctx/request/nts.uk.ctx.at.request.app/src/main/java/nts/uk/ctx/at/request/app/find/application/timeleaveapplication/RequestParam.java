package nts.uk.ctx.at.request.app.find.application.timeleaveapplication;

import lombok.Data;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.output.TimeLeaveApplicationOutput;

@Data
public class RequestParam {

    private int timeDigestAppType;

    private TimeLeaveApplicationDto timeLeaveApplicationDto;

    // 申請
    private ApplicationDto application;

    private TimeLeaveApplicationOutput timeLeaveApplicationOutput;

}
