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
    private GeneralDate applyDate;
    private TimeLeaveAppDisplayInfoDto appDisplayInfo;
}
