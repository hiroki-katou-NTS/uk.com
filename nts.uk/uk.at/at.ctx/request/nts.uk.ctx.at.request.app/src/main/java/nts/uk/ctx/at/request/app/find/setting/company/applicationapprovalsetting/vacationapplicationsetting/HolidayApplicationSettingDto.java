package nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.vacationapplicationsetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.HolidayApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.HolidayApplicationTypeDisplayName;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HolidayApplicationSettingDto {
    private int halfDayAnnualLeaveUsageLimitCheck;
    private List<HolidayAppTypeDispNameDto> dispNames;

    public static HolidayApplicationSettingDto fromDomain(HolidayApplicationSetting domain) {
        return new HolidayApplicationSettingDto(
                domain.getHalfDayAnnualLeaveUsageLimitCheck().value,
                domain.getHolidayApplicationTypeDisplayName().stream().map(d -> new HolidayAppTypeDispNameDto(d.getHolidayApplicationType().value, d.getDisplayName().v())).collect(Collectors.toList())
        );
    }
    
    public HolidayApplicationSetting toDomain(String companyId) {
        return HolidayApplicationSetting.create(
                companyId, 
                dispNames.stream().map(x -> HolidayApplicationTypeDisplayName.create(x.getHolidayAppType(), x.getDisplayName())).collect(Collectors.toList()), 
                halfDayAnnualLeaveUsageLimitCheck);
    }
}
