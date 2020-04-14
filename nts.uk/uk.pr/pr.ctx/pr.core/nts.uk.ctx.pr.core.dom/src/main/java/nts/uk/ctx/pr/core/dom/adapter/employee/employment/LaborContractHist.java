package nts.uk.ctx.pr.core.dom.adapter.employee.employment;

import lombok.*;
import nts.arc.time.GeneralDate;

/**
 * 労働契約履歴
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class LaborContractHist {
    /**
     * 社員ID
     */
    private String sid;

    /**
     * 期間.契約更新条項の有無
     */
    private Integer contractRenewalProvision;

    /**
     * 期間.開始日
     */
    private GeneralDate startDate;

    /**
     * 期間.終了日
     */
    private GeneralDate endDate;
}
