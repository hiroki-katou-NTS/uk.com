package nts.uk.ctx.pr.core.dom.adapter.employee.employment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

/**
 * 労働契約履歴
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LaborContractHist {
    /**
     * 社員ID
     */
    private String sid;

    /**
     * 期間.契約更新条項の有無
     */
    private Integer workingSystem;

    /**
     * 期間.開始日
     */
    private GeneralDate startDate;

    /**
     * 期間.終了日
     */
    private GeneralDate endDate;
}
