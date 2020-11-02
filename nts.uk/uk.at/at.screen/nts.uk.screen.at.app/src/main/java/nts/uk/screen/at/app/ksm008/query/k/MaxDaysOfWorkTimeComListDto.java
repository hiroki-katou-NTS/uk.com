package nts.uk.screen.at.app.ksm008.query.k;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MaxDaysOfWorkTimeComListDto {
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
    private Integer maxDay;

    /**
     * 就業時間帯名称
     */
    private List<WorkingHoursDTO> workingHours;

}

@NoArgsConstructor
@AllArgsConstructor
@Data
class WorkingHoursDTO {
    /**
     * コード
     */
    private String code;

    /**
     * 名称
     */
    private String name;
}