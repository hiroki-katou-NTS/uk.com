package nts.uk.ctx.at.request.app.find.application.timeleaveapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.output.TimeLeaveApplicationOutput;
import nts.uk.ctx.at.shared.app.find.workcheduleworkrecord.appreflectprocess.appreflectcondition.timeleaveapplication.TimeLeaveAppReflectDto;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;

import java.util.List;

/**
 * 時間休暇申請の表示情報
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TimeLeaveAppDisplayInfoDto {

    // 時間休暇残数
    private TimeLeaveRemaining timeLeaveRemaining;

    // 時間休暇申請の反映
    private TimeLeaveAppReflectDto reflectSetting;

    // 時間休暇管理
    private TimeLeaveManagement timeLeaveManagement;

    // 申請表示情報
    private AppDispInfoStartupDto appDispInfoStartupOutput;

    //時間休暇申請. 詳細
    private List<TimeLeaveAppDetailDto> details;

    public static TimeLeaveApplicationOutput mappingData(TimeLeaveAppDisplayInfoDto info) {
        return new TimeLeaveApplicationOutput(
                TimeLeaveRemaining.setDataOutput(info.timeLeaveRemaining),
                TimeLeaveAppReflectDto.toDomain(info.reflectSetting),
                TimeLeaveManagement.setDtaOutput(info.timeLeaveManagement),
                info.appDispInfoStartupOutput.toDomain()
        );
    }

    public static TimeLeaveAppDisplayInfoDto fromOutput(TimeLeaveApplicationOutput output) {
        TimeLeaveAppDisplayInfoDto dto = new TimeLeaveAppDisplayInfoDto();
        dto.timeLeaveRemaining = TimeLeaveRemaining.fromOutput(output.getTimeVacationRemaining());
        dto.reflectSetting = TimeLeaveAppReflectDto.fromDomain(output.getTimeLeaveApplicationReflect());
        dto.timeLeaveManagement = TimeLeaveManagement.fromOutput(output.getTimeVacationManagement());
        dto.appDispInfoStartupOutput = AppDispInfoStartupDto.fromDomain(output.getAppDispInfoStartup());
        return dto;
    }
}
