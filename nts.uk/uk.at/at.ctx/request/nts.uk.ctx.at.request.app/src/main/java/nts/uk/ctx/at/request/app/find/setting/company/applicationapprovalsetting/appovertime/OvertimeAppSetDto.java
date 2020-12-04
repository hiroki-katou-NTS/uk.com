package nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.appovertime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.overtimerestappcommon.ApplicationDetailSettingDto;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeLeaveAppCommonSetDto;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.appovertime.OvertimeAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.appovertime.OvertimeQuotaSetUse;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.ApplicationDetailSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeLeaveAppCommonSet;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OvertimeAppSetDto {
    private OvertimeLeaveAppCommonSetDto overtimeLeaveAppCommonSetting;
    private ApplicationDetailSettingDto applicationDetailSetting;
    private List<OvertimeQuotaSetUseDto> overTimeQuotaSettings;

    public static OvertimeAppSetDto fromDomain(OvertimeAppSet domain) {
        
    	List<OvertimeQuotaSetUseDto> overTimeQuotaSettings = new ArrayList<>();
        domain.getOvertimeQuotaSet().forEach(f -> {
            overTimeQuotaSettings.addAll(OvertimeQuotaSetUseDto.fromDomain(f));
        });
        
        return new OvertimeAppSetDto(
                OvertimeLeaveAppCommonSetDto.fromDomain(domain.getOvertimeLeaveAppCommonSet()),
                ApplicationDetailSettingDto.fromDomain(domain.getApplicationDetailSetting()),
                overTimeQuotaSettings
        );
    }

    public OvertimeAppSet toDomain(String companyId) {
        List<OvertimeQuotaSetUse> overTimeQuotaSet = new ArrayList<>();
        overTimeQuotaSettings.stream().collect(Collectors.groupingBy(o -> o.getOvertimeAtr())).forEach((overtimeAtr, values) -> {
            values.stream().collect(Collectors.groupingBy(o2 -> o2.getFlexAtr())).forEach((flexAtr, values2) ->  {
                overTimeQuotaSet.add(OvertimeQuotaSetUse.create(overtimeAtr, flexAtr, values2.stream().map(v -> v.getOverTimeFrame()).collect(Collectors.toList())));
            });
        });
        return new OvertimeAppSet(
                companyId,
                OvertimeLeaveAppCommonSet.create(
                        overtimeLeaveAppCommonSetting.getPreExcessDisplaySetting(),
                        overtimeLeaveAppCommonSetting.getExtratimeExcessAtr(),
                        overtimeLeaveAppCommonSetting.getExtratimeDisplayAtr(),
                        overtimeLeaveAppCommonSetting.getPerformanceExcessAtr(),
                        overtimeLeaveAppCommonSetting.getCheckOvertimeInstructionRegister(),
                        overtimeLeaveAppCommonSetting.getCheckDeviationRegister(),
                        overtimeLeaveAppCommonSetting.getOverrideSet()
                ),
                overTimeQuotaSet,
                ApplicationDetailSetting.create(
                        applicationDetailSetting.getRequiredInstruction(),
                        applicationDetailSetting.getPreRequireSet(),
                        applicationDetailSetting.getTimeInputUse(),
                        applicationDetailSetting.getTimeCalUse(),
                        applicationDetailSetting.getAtworkTimeBeginDisp(),
                        applicationDetailSetting.getDispSystemTimeWhenNoWorkTime()
                )
        );
    }
}
