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
    private int unit;

    /**
     * 対象組織情報.職場ID
     */
    private String workplaceId;

    /**
     * 対象組織情報.職場グループID
     */
    private String workplaceGroupId;

    /**
     * 組織の表示情報.コード
     */
    private String code;

    /**
     * 組織の表示情報.表示名
     */
    private String displayName;

    /**
     * 組織の連続出勤できる上限日数.日数.日数
     */
    private Integer maxConsDays;
}
