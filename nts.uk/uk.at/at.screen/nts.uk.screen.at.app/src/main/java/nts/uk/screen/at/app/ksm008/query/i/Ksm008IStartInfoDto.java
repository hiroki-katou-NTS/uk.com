package nts.uk.screen.at.app.ksm008.query.i;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ksm008IStartInfoDto {

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
    private String explanation;

    /**
     * I5_1
     */

    List<MaxDaysOfContinuousWorkTimeDto> workTimeList;
}
