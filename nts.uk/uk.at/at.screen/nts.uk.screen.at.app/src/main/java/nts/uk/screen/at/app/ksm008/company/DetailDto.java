package nts.uk.screen.at.app.ksm008.company;

import lombok.AllArgsConstructor;

import java.util.List;

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
