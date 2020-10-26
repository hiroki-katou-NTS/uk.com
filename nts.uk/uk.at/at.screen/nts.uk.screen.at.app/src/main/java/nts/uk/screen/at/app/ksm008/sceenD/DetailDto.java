package nts.uk.screen.at.app.ksm008.sceenD;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DetailDto {

    //D7_2
    private int typeWorkMethod;

    //D8_3
    private int specifiedMethod;

    //D10
    private int typeOfWorkMethods;

    //E9_2
    private List<WorkingHoursDto> workTimeSettings;

}
