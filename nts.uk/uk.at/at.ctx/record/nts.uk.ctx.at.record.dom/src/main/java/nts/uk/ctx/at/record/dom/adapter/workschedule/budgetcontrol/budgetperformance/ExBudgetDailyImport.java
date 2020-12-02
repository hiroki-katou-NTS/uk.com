package nts.uk.ctx.at.record.dom.adapter.workschedule.budgetcontrol.budgetperformance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;

/**
 * 日次の外部予算実績
 *
 * @author Le Huu Dat
 */
@Getter
@AllArgsConstructor
public class ExBudgetDailyImport {

    /**
     * 対象組織識別情報
     */
    private TargetOrgIdenInforImport targetOrg;

    /**
     * 外部予算実績項目コード
     */
    private String itemCode;

    /**
     * 年月日
     */
    private GeneralDate ymd;

    /**
     * 値- 外部予算実績値
     */
    private int actualValue;


}
