package nts.uk.ctx.at.request.dom.application.timeleaveapplication.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 時間休暇管理
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TimeLeaveManagement {
    // 時間年休管理
    private TimeAnnualLeaveMng timeAnnualLeaveMng;

    // 時間代休管理
    private TimeSubstituteLeaveMng timeSubstituteLeaveMng;

    // 60H超休管理
    private Super60HLeaveMng super60HLeaveMng;

    // 子看護介護管理
    private NursingLeaveMng nursingLeaveMng;

    // 時間特別休暇管理
    private TimeSpecialLeaveMng timeSpecialLeaveMng;
}
