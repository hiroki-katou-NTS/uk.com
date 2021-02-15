package nts.uk.ctx.at.request.dom.application.timeleaveapplication.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 時間特別休暇残数
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TimeSpecialVacationRemaining {

    // 特別休暇の残日数
    private double dayOfSpecialLeave;

    // 特別休暇の残時間
    private int timeOfSpecialLeave;

    // 特別休暇枠NO
    private int specialFrameNo;

}
