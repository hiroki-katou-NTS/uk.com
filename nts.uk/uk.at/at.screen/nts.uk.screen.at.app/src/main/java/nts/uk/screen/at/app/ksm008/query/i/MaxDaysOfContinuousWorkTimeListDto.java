package nts.uk.screen.at.app.ksm008.query.i;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;

import java.util.List;

/**
 * @author Md Rafiqul Islam
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MaxDaysOfContinuousWorkTimeListDto {
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
    private MaxDaysOfConsecutiveWorkTimeDTO maxDaysContiWorktime;
}

@NoArgsConstructor
@AllArgsConstructor
@Data
class MaxDaysOfConsecutiveWorkTimeDTO {
    /**
     * 就業時間帯コードリスト
     */
    private List<String> workTimeCodes;

    /**
     * 日数
     */
    private Integer numberOfDays;
}
