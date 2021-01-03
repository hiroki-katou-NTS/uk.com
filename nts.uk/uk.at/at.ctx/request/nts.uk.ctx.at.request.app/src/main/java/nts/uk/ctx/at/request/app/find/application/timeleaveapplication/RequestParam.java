package nts.uk.ctx.at.request.app.find.application.timeleaveapplication;

import lombok.Data;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.timeleaveapplication.dto.TimeLeaveAppDisplayInfoDto;

@Data
public class RequestParam {

    //時間休暇種類
    private int timeDigestAppType;

    //時間休暇申請
    private TimeLeaveApplicationDto timeLeaveApplicationDto;

    // 申請
    private ApplicationDto application;

    //時間休暇申請の表示情報
    private TimeLeaveAppDisplayInfoDto timeLeaveAppDisplayInfo;

    //代休申請区分

}
