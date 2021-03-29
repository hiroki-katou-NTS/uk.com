package nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.appovertime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.overtimerestappcommon.ApplicationDetailSettingCommand;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeLeaveAppCommonSetCommand;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.appovertime.OvertimeAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.appovertime.OvertimeQuotaSetUse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OvertimeAppSetCommand {
	
    private OvertimeLeaveAppCommonSetCommand overtimeLeaveAppCommonSetting;
    
    private ApplicationDetailSettingCommand applicationDetailSetting;
    
    private List<OvertimeQuotaSetUseCommand> overTimeQuotaSettings;

    public OvertimeAppSet toDomain(String companyId) {
        List<OvertimeQuotaSetUse> quotaSetUses = new ArrayList<>();
        Map<Object, List<OvertimeQuotaSetUseCommand>> group = overTimeQuotaSettings.stream().collect(Collectors.groupingBy(e -> new HashMap() {{
            put(e.getOvertimeAtr(), e.getFlexAtr());
        }}));
        group.forEach((key, value) -> {
            quotaSetUses.add(OvertimeQuotaSetUseCommand.toDomains(value));
        });
        return new OvertimeAppSet(
                companyId,
                overtimeLeaveAppCommonSetting.toDomain(),
                quotaSetUses,
                applicationDetailSetting.toDomain()
        );
    }
}
