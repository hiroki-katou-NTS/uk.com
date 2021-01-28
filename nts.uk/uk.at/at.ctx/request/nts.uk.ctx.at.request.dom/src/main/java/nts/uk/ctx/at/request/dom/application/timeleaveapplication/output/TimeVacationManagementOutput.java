package nts.uk.ctx.at.request.dom.application.timeleaveapplication.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 時間休暇管理
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TimeVacationManagementOutput {

    // 60H超休管理
    private SupHolidayManagement supHolidayManagement;

    // 子看護介護管理
    private ChildNursingManagement childNursingManagement;

    // 時間代休管理
    private TimeAllowanceManagement timeAllowanceManagement;

    // 時間年休管理
    private TimeAnnualLeaveManagement timeAnnualLeaveManagement;

    // 時間特別休暇管理
    private TimeSpecialLeaveManagement timeSpecialLeaveMng;

}
