package nts.uk.screen.at.app.ksm008.query.j;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.screen.at.app.ksm008.query.i.MaxDaysOfContinuousWorkTimeDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ksm008JStartOrgInfoDto {

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
     * 対象組織情報.職場グループID
     */
    private String workplaceTarget;

    /**
     * 組織の表示情報.コード
     */
    private String workplaceCode;

    /**
     * 組織の表示情報.表示名
     */
    private String workplaceName;

    /**
     * J2_1[就業時間帯の連続一覧]
     */

    List<MaxDaysOfContinuousWorkTimeDto> workTimeList;
}
