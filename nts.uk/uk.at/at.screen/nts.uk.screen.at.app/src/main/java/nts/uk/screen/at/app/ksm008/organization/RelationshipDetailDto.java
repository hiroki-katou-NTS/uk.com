package nts.uk.screen.at.app.ksm008.organization;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.screen.at.app.ksm008.company.WorkingHoursDto;

import java.util.List;

@Data
@AllArgsConstructor
public class RelationshipDetailDto {

    //E4_2
    private int typeWorkMethod;

    //E5_3
    private int specifiedMethod;

    //E7
    private int typeOfWorkMethods;

    //E9_2
    private List<WorkingHoursDto> workTimeSettings;

}
