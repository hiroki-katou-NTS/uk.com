package nts.uk.screen.at.app.ksm008.screenE;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.screen.at.app.ksm008.ConsecutiveAttendanceOrg.OrgInfoDto;
import nts.uk.screen.at.app.ksm008.sceenD.WorkingHoursDto;

import java.util.List;

@Data
@AllArgsConstructor
public class Ksm008EStartInfoDto {

    //E1_3, E1_4
    private OrgInfoDto orgInfoDto;

    //E3_3
    private List<WorkingHoursDto> workTimeSettings;

}
