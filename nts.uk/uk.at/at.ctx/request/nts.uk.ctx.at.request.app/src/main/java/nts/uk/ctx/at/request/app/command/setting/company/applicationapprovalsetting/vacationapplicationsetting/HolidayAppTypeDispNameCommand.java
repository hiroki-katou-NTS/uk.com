package nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.vacationapplicationsetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HolidayAppTypeDispNameCommand {
    private int holidayAppType;
    private String displayName;
}
