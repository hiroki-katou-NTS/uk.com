package nts.uk.ctx.at.request.app.find.application.timeleaveapplication;

import lombok.Data;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.timeleaveapplication.dto.TimeLeaveAppDetailDto;
import nts.uk.ctx.at.request.app.find.application.timeleaveapplication.dto.TimeLeaveAppDisplayInfoDto;

import java.util.List;

@Data
public class ChangeSpecialLeaveFrameParams {

    private Integer specialLeaveFrameNo;

    //時間休暇申請の表示情報
    private TimeLeaveAppDisplayInfoDto timeLeaveAppDisplayInfo;

}
