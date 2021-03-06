package nts.uk.screen.at.app.ksm008.query.j;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Md Rafiqul Islam
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MaxDaysOfContinuousWorkTimeListOrgDto {
    /**
     * コード
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 日数
     */
    private Integer maxDaysContiWorktime;

    /**
     * 就業時間帯名称
     */
    private List<WorkingHoursOrgDTO> workingHours;

}

@NoArgsConstructor
@AllArgsConstructor
@Data
class WorkingHoursOrgDTO {
    /**
     * code
     */
    private String code;

    /**
     * name
     */
    private String name;
}

