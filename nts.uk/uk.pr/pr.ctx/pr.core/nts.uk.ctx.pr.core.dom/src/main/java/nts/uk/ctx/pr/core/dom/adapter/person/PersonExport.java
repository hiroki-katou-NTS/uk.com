package nts.uk.ctx.pr.core.dom.adapter.person;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonExport {
    /** The Birthday */
    // 生年月日
    private GeneralDate birthDate;

    /** The Gender - 性別 */
    private int gender;

    /** The person id - 個人ID */
    private String personId;

    /** The PersonNameGroup - 個人名グループ*/
    private PersonNameGroupExport personNameGroup;
}
