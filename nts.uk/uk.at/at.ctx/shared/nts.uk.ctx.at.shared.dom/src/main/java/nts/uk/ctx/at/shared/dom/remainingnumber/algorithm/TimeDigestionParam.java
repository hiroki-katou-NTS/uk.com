package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.application.timeleaveapplication.TimeLeaveApplicationDetailShare;

/**
 * @author anhnm
 * 時間消化パラメータ
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TimeDigestionParam {

    // 時間60超休
    private int over60HTime;
    
    // 時間介護
    private int nursingTime;
    
    // 時間子の看護
    private int childCareTime;
    
    // 時間代休
    private int subHolidayTime;
    
    // 時間年休
    private int annualTime;
    
    // 残業・休出時間
    private int overHolidayTime;
    
    // 時間休暇申請詳細
    private List<TimeLeaveApplicationDetailShare> timeLeaveApplicationDetails = new ArrayList<TimeLeaveApplicationDetailShare>();
    
    public TimeDigestionUsageInfor toTimeDigestionUsageInfor() {
        return new TimeDigestionUsageInfor(
                annualTime, 
                subHolidayTime, 
                over60HTime, 
                childCareTime, 
                nursingTime);
    }
}
