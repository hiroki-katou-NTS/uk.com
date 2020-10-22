package nts.uk.screen.at.app.ksm008.ConsecutiveAttendanceCom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsecutiveAttendanceComDto {

    /**
     * 勤務予定のアラームチェック条件.コード
     */
    private String code;

    /**
     * 勤務予定のアラームチェック条件.条件名
     */
    private String conditionName;

    /**
     * 勤務予定のアラームチェック条件.サブ条件リスト.説明
     */
    private List<String> explanationList;

    /**
     * 会社の連続出勤できる上限日数.日数.日数
     **/
    private Integer maxConsDays;
}
