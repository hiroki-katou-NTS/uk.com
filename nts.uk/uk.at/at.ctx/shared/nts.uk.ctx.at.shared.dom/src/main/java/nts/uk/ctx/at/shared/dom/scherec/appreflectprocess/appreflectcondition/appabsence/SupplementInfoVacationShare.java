package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.appabsence;

import java.util.Optional;

import lombok.Data;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * 
 * @author thanhnx
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.申請.休暇申請.特定の休暇指定時の補足情報
 * 特定の休暇指定時の補足情報
 * 
 */

@Data
public class SupplementInfoVacationShare {

    /**
     * 期間
     */
    private Optional<DatePeriod> datePeriod;
    
    /**
     * 特別休暇申請
     */
    private Optional<ApplyforSpecialLeaveShare> applyForSpeLeaveOptional;
    
    public SupplementInfoVacationShare(Optional<DatePeriod> datePeriod, Optional<ApplyforSpecialLeaveShare> applyForSpeLeaveOptional) {
        this.datePeriod = datePeriod;
        this.applyForSpeLeaveOptional = applyForSpeLeaveOptional;
    }
}
