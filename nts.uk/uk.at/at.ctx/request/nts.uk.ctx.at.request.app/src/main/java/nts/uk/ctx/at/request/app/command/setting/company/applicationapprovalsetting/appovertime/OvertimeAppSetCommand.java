package nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.appovertime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.overtimerestappcommon.ApplicationDetailSettingCommand;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeLeaveAppCommonSetCommand;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.appovertime.OvertimeAppSet;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OvertimeAppSetCommand {
    private OvertimeLeaveAppCommonSetCommand overtimeLeaveAppCommonSetting;
    private ApplicationDetailSettingCommand applicationDetailSetting;
    private List<OvertimeQuotaSetUseCommand> overTimeQuotaSettings;

    public OvertimeAppSet toDomain(String companyId) {
        return new OvertimeAppSet(
                companyId,
                overtimeLeaveAppCommonSetting.toDomain(),
                new ArrayList<>(),
                applicationDetailSetting.toDomain()
        );
    }
}
