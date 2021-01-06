package nts.uk.ctx.at.request.app.find.application.timeleaveapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.timeleaveapplication.CalculationResult;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StartProcessTimeLeaveAppDto {

    // 時間休暇申請の表示情報
    private TimeLeaveAppDisplayInfoDto timeLeaveAppDisplayInfo;

    // 時間休暇申請
    //詳細
    private List<TimeLeaveAppDetailDto> details;

    // 申請
    private ApplicationDto application;

    // 計算結果
    private CalculationResult calculationResult;

}
