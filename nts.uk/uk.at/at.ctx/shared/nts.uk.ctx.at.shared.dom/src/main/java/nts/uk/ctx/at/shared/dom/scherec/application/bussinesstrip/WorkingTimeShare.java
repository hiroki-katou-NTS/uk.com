package nts.uk.ctx.at.shared.dom.scherec.application.bussinesstrip;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.worktime.predset.WorkNo;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkingTimeShare {
    
    // 勤務NO
    WorkNo workNo;
    
    // 終了時刻
    Optional<TimeWithDayAttr> startTime;
    
    // 開始時刻
    Optional<TimeWithDayAttr> endTime;

    
    public TimeZoneWithWorkNo toTimeZoneWithWorkNo() {
        return new TimeZoneWithWorkNo(workNo.v(), startTime.map(TimeWithDayAttr::v).orElse(null), endTime.map(TimeWithDayAttr::v).orElse(null));
    }
}
