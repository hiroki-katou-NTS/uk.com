package nts.uk.screen.at.app.ksm008.screenE;

import lombok.AllArgsConstructor;
import nts.uk.screen.at.app.ksm008.sceenD.WorkingHoursDto;

import java.util.List;

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
