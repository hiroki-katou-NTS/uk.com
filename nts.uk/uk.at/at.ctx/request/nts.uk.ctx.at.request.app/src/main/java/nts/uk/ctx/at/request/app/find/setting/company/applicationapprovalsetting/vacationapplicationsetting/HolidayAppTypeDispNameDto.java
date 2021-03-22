package nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.vacationapplicationsetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.HolidayApplicationTypeDisplayName;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HolidayAppTypeDispNameDto {
    private int holidayAppType;
    private String displayName;

    public HolidayApplicationTypeDisplayName toDomain() {
        return HolidayApplicationTypeDisplayName.create(holidayAppType, displayName);
    }
}
