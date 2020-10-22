package nts.uk.screen.at.app.ksm008.screenE;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.screen.at.app.ksm008.ConsecutiveAttendanceOrg.OrgInfoDto;
import nts.uk.screen.at.app.ksm008.sceenD.WorkingHoursDto;

import java.util.List;

@Data
@AllArgsConstructor
public class Ksm008EStartInfoDto {

    private OrgInfoDto orgInfoDto;

    private List<WorkingHoursDto> workTimeSettings;

}
