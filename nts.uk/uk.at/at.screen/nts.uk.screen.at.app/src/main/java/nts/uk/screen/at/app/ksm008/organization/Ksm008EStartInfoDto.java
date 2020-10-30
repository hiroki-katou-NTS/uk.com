package nts.uk.screen.at.app.ksm008.organization;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.screen.at.app.ksm008.ConsecutiveAttendanceOrg.OrgInfoDto;
import nts.uk.screen.at.app.ksm008.company.WorkingHoursAndWorkMethodDto;
import nts.uk.screen.at.app.ksm008.company.WorkingHoursDto;

import java.util.List;

@Data
@AllArgsConstructor
public class Ksm008EStartInfoDto {

    //D3_1
    private String conditionCode;

    //D3_1
    private String conditionName;

    //D5_2
    private List<String> subConditions;

    //E1_3, E1_4
    private OrgInfoDto orgInfoDto;

    //E3_3
    private List<WorkingHoursAndWorkMethodDto> workTimeSettings;

}
