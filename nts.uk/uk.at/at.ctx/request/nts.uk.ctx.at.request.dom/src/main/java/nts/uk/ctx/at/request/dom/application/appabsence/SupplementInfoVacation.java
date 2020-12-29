package nts.uk.ctx.at.request.dom.application.appabsence;

import java.util.Optional;

import lombok.Data;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.dom.application.appabsence.appforspecleave.ApplyforSpecialLeave;

/**
 * 
 * @author anhnm
 * Refactor 5
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.申請.休暇申請.特定の休暇指定時の補足情報
 * 特定の休暇指定時の補足情報
 * 
 */

@Data
public class SupplementInfoVacation {

    /**
     * 期間
     */
    private Optional<DatePeriod> datePeriod;
    
    /**
     * 特別休暇申請
     */
    private Optional<ApplyforSpecialLeave> applyForSpeLeaveOptional;
    
    public SupplementInfoVacation(Optional<DatePeriod> datePeriod, Optional<ApplyforSpecialLeave> applyForSpeLeaveOptional) {
        this.datePeriod = datePeriod;
        this.applyForSpeLeaveOptional = applyForSpeLeaveOptional;
    }
}
