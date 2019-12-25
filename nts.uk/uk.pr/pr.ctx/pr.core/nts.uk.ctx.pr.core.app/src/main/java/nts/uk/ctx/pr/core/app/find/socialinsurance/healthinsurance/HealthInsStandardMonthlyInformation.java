package nts.uk.ctx.pr.core.app.find.socialinsurance.healthinsurance;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HealthInsStandardMonthlyInformation {
    /**社員ID*/
    private String employeeId;

    /** 履歴ID */
    private String historyId;

    /**
     * 期間.開始年月
     */
    private Integer startYM;

    /** 期間.終了年月 */
    private Integer endYM;

    /** 現在の等級 */
    private Integer currentGrade;

    /** 算定区分 */
    private Integer calculationAtr;

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

    /** 社会保険報酬月額（実質） */
    private Integer socInsMonthlyRemune;
}
