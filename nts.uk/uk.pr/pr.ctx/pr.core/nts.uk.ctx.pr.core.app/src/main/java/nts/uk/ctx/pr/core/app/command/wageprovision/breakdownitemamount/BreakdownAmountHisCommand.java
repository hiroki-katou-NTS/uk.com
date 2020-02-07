package nts.uk.ctx.pr.core.app.command.wageprovision.breakdownitemamount;

import lombok.Value;

import java.util.List;

@Value
public class BreakdownAmountHisCommand {

    /**
     * カテゴリ区分
     */
    private int categoryAtr;

    /**
     * 項目名コード
     */
    private String itemNameCd;

    /**
     * 社員ID
     */
    private String employeeId;

    /**
     * 給与賞与区分
     */
    private int salaryBonusAtr;

    /**
     * 期間
     */
    private List<YearMonthHistoryItemCommand> period;


    private List<BreakdownAmountListCommand> breakdownAmountList;

    private String lastHistoryId;

    private String historyUpdate;

}
