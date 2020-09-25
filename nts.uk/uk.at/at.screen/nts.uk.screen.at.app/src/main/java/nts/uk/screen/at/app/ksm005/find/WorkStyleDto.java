package nts.uk.screen.at.app.ksm005.find;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WorkStyleDto {

    /** The work monthly setting code. */
    private String workTypeCode;

    /** The working code. */
    private String workingCode;

    /** The type color. */
    // ATTENDANCE = 1 , HOLIDAY = 0
    private int typeColor;

}
