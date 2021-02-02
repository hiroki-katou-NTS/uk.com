package nts.uk.ctx.at.request.app.find.application.timeleaveapplication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoWithDateDto;
import nts.uk.ctx.at.request.app.find.application.timeleaveapplication.dto.TimeLeaveAppDisplayInfoDto;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChangeAppDateParams {
    // 申請日
    private GeneralDate appDate;

    // 時間休暇申請の表示情報
    private TimeLeaveAppDisplayInfoDto appDisplayInfo;
}
