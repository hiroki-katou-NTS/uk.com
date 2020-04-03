package nts.uk.ctx.pr.core.app.find.socialinsurance.healthinsurance;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@Getter
@Setter
@NoArgsConstructor
public class HealthInsStandardMonthlyInformation {

    /**
     * 社員ID
     */
    private String sId;

    /**
     * 期間.開始年月
     */
    private GeneralDate startYM;

    /**
     * 健康保険等級
     */
    private Integer healInsGrade;

    /** 健康保険標準報酬月額 */
    private Integer healInsStandMonthlyRemune;

    /** 厚生年金保険等級 */
    private Integer pensionInsGrade;

    /** 厚生年金保険標準報酬月額 */
    private Integer pensionInsStandCompenMonthly;
}
