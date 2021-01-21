package nts.uk.ctx.at.request.dom.application.businesstrip;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;

import java.util.List;
import java.util.Optional;
// 出張勤務情報
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BusinessTripInfo {

    // 勤務情報
    private WorkInformation workInformation;

    // 年月日
    private GeneralDate date;

    // 勤務時間帯
    private Optional<List<TimeZoneWithWorkNo>> workingHours;

}
