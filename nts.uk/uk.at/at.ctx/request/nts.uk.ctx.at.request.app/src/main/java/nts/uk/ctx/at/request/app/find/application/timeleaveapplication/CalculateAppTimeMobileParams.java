package nts.uk.ctx.at.request.app.find.application.timeleaveapplication;

import lombok.Data;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.timeleaveapplication.dto.TimeLeaveAppDetailDto;
import nts.uk.ctx.at.request.app.find.application.timeleaveapplication.dto.TimeLeaveAppDisplayInfoDto;

import java.util.List;

@Data
public class CalculateAppTimeMobileParams {

    //時間休暇種類
    private int timeDigestAppType;

    //時間休暇申請
    private List<TimeLeaveAppDetailDto> details;

    // 申請
    private ApplicationDto application;

    //時間休暇申請の表示情報
    private TimeLeaveAppDisplayInfoDto timeLeaveAppDisplayInfo;

    //画面モード true: Edit mode ; false: Display mode
    private boolean screenMode;

}
