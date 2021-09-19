package nts.uk.ctx.at.request.dom.application.businesstrip.service;

import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.application.bussinesstrip.WorkingTimeShare;
import nts.uk.ctx.at.shared.dom.worktime.predset.WorkNo;
import nts.uk.shr.com.time.TimeWithDayAttr;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author anhnm
 * 出張勤務時刻
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkingTime {
    
    // 勤務NO
    WorkNo workNo;
    
    // 終了時刻
    Optional<TimeWithDayAttr> startTime;
    
    // 開始時刻
    Optional<TimeWithDayAttr> endTime;

    
    public WorkingTimeShare toShare() {
        return new WorkingTimeShare(workNo, startTime, endTime);
    }
}
