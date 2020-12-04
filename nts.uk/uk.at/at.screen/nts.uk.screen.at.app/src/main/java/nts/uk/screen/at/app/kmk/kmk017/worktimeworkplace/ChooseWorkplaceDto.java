package nts.uk.screen.at.app.kmk.kmk017.worktimeworkplace;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChooseWorkplaceDto {

    //A5_13
    private String workTimeCode;

    //A5_14
    private String workTimeName;

    //A5_15
    private TimezoneUseDto Timezone1;

    //A5_16
    private TimezoneUseDto Timezone2;

    //A5_17
    private String note;

}
