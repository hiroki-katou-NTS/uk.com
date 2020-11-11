package nts.uk.screen.at.app.ksm008.company;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data

@AllArgsConstructor
public class WorkingHoursAndWorkMethodDto {
    /**
     * code
     */
    private String code;

    /**
     * name
     */
    private String name;

    private int typeWorkMethod;

}
