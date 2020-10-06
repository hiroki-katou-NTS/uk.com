package nts.uk.screen.at.app.ksm008.ConsecutiveAttendanceOrg;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsecutiveAttendanceOrgDto {
    /**
     * 対象組織情報.単位
     */

    /**
     * 対象組織情報.Optional<職場ID>
     */

    /**
     * 対象組織情報.Optional<職場グループID>
     */

    /**
     * 組織の表示情報.コード
     */

    /**
     * 組織の表示情報.表示名
     */

    /**
     * 組織の連続出勤できる上限日数.日数.日数
     */
    private Integer maxConsDays;
}
