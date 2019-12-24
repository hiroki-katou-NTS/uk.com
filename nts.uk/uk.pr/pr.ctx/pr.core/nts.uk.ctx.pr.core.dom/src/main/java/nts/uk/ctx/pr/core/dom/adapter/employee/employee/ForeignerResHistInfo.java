package nts.uk.ctx.pr.core.dom.adapter.employee.employee;

import lombok.*;
import nts.arc.time.GeneralDate;

/**
 * 外国人在留履歴情報
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ForeignerResHistInfo {

    /**
     * 社員ID
     */
    private String sid;

    /**
     * 派遣・請負就労区分
     */
    private Integer contractWorkAtr;

    /**
     * 資格外活動許可の有無
     */
    private Integer unqualifiedActivityPermission;

    /**
     * 在留期間（西暦）.開始日
     */
    private GeneralDate startDate;

    /**
     * 在留期間（西暦）.終了日
     */
    private GeneralDate endDate;

    /**
     * 在留資格
     */
    private String residenceStatusCode;

    private String residenceStatusName;

    /**
     * 国籍・地域
     */
    private String nationalityCode;

    private String nationalityName;
}
