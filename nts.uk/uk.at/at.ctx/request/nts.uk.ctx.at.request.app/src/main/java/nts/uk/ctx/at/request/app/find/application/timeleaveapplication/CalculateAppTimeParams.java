package nts.uk.ctx.at.request.app.find.application.timeleaveapplication;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.application.timeleaveapplication.dto.OutingTimeZoneDto;
import nts.uk.ctx.at.request.app.find.application.timeleaveapplication.dto.TimeLeaveAppDisplayInfoDto;
import nts.uk.ctx.at.request.app.find.application.timeleaveapplication.dto.TimeZoneDto;

import java.util.List;

@Data
public class CalculateAppTimeParams {
    private Integer timeLeaveType;

    // 申請日
    private GeneralDate appDate;

    // 時間休暇申請の表示情報
    private TimeLeaveAppDisplayInfoDto appDisplayInfo;

    // 時間帯<List>
    private List<TimeZoneDto> timeZones;

    // 外出時間帯<List>
    private List<OutingTimeZoneDto> outingTimeZones;
}
