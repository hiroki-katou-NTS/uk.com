package nts.uk.ctx.at.request.app.find.application.timeleaveapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.output.TimeLeaveApplicationOutput;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.output.TimeLeaveManagement;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.output.TimeLeaveRemaining;
import nts.uk.ctx.at.shared.app.find.workcheduleworkrecord.appreflectprocess.appreflectcondition.timeleaveapplication.TimeLeaveAppReflectDto;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;

/**
 * 時間休暇申請の表示情報
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TimeLeaveAppDisplayInfo {

    // 労働条件項目
    private WorkingConditionItem workingConditionItem;

    // 時間休暇残数
    private TimeLeaveRemaining timeLeaveRemaining;

    // 時間休暇申請の反映
    private TimeLeaveAppReflectDto reflectSetting;

    // 時間休暇管理
    private TimeLeaveManagement timeLeaveManagement;

    // 申請表示情報
    private AppDispInfoStartupDto appDispInfoStartupOutput;

    public static TimeLeaveApplicationOutput mappingData(TimeLeaveAppDisplayInfo info) {
        return new TimeLeaveApplicationOutput(info.workingConditionItem,
            TimeLeaveRemaining.setDataOutput(info.timeLeaveRemaining),
            TimeLeaveAppReflectDto.toDomain(info.reflectSetting),
            TimeLeaveManagement.setDtaOutput(info.timeLeaveManagement),
            info.appDispInfoStartupOutput.toDomain()
        );
    }
}
