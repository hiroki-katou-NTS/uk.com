package nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.appovertime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.overtimerestappcommon.ApplicationDetailSettingDto;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeLeaveAppCommonSetDto;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.appovertime.OvertimeAppSet;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OvertimeAppSetDto {
    private OvertimeLeaveAppCommonSetDto overtimeLeaveAppCommonSetting;
    private ApplicationDetailSettingDto applicationDetailSetting;
    private List<OvertimeQuotaSetUseDto> overTimeQuotaSetting;
    public static OvertimeAppSetDto fromDomain(OvertimeAppSet domain) {
        return new OvertimeAppSetDto(
                OvertimeLeaveAppCommonSetDto.fromDomain(domain.getOvertimeLeaveAppCommonSet()),
                ApplicationDetailSettingDto.fromDomain(domain.getApplicationDetailSetting()),
                domain.getOvertimeQuotaSet().stream().map(OvertimeQuotaSetUseDto::fromDomain).collect(Collectors.toList())
        );
    }
}
