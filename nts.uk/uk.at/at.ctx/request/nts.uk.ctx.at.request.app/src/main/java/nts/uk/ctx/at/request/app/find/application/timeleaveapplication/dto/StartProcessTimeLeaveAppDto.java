package nts.uk.ctx.at.request.app.find.application.timeleaveapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.application.timeleaveapplication.CalculationResult;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StartProcessTimeLeaveAppDto {

    // 時間休暇申請の表示情報
    private TimeLeaveAppDisplayInfoDto timeLeaveAppDisplayInfo;

    // 時間休暇申請
    private TimeLeaveApplicationDto timeLeaveApplicationDto;

    CalculationResult calculationResult;

}
