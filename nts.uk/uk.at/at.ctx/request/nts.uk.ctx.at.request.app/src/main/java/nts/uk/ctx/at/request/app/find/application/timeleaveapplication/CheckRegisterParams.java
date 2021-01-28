package nts.uk.ctx.at.request.app.find.application.timeleaveapplication;

import lombok.Data;
import nts.uk.ctx.at.request.app.command.application.common.ApplicationUpdateCmd;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.timeleaveapplication.dto.TimeLeaveAppDetailDto;
import nts.uk.ctx.at.request.app.find.application.timeleaveapplication.dto.TimeLeaveAppDisplayInfoDto;

import java.util.List;

@Data
public class CheckRegisterParams {

    //時間休暇種類
    private int timeDigestAppType;

    // 申請
    private ApplicationDto applicationNew;

    private ApplicationUpdateCmd applicationUpdate;

    // 時間休暇申請. 詳細
    private List<TimeLeaveAppDetailDto> details;

    // 時間休暇申請の表示情報
    private TimeLeaveAppDisplayInfoDto timeLeaveAppDisplayInfo;

    // 代休申請区分
    private boolean agentMode;
}
