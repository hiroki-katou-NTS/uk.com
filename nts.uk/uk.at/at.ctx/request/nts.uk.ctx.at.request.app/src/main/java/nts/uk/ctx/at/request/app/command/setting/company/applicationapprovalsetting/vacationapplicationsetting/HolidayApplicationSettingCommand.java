package nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.vacationapplicationsetting;

import lombok.Data;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.HolidayApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.HolidayApplicationTypeDisplayName;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class HolidayApplicationSettingCommand {
    private int halfDayAnnualLeaveUsageLimitCheck;
    private List<HolidayAppTypeDispNameCommand> dispNames;

    public HolidayApplicationSetting toDomain(String companyId) {
        List<HolidayApplicationTypeDisplayName> displayNames = dispNames.stream()
                .map(d -> HolidayApplicationTypeDisplayName.create(d.getHolidayAppType(), d.getDisplayName()))
                .collect(Collectors.toList());
        return HolidayApplicationSetting.create(companyId, displayNames, halfDayAnnualLeaveUsageLimitCheck);
    }
}
