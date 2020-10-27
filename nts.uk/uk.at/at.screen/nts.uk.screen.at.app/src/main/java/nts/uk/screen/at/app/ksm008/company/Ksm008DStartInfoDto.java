package nts.uk.screen.at.app.ksm008.company;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Ksm008DStartInfoDto {

    //D3_1
    private String conditionCode;

    //D3_1
    private String conditionName;

    //D5_2
    private List<String> subConditions;

    //D6_3
    private List<WorkingHoursDto> workTimeSettings;

}

