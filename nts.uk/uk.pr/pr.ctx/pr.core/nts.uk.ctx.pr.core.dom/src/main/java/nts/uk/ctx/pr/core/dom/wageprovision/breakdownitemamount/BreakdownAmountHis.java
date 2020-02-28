package nts.uk.ctx.pr.core.dom.wageprovision.breakdownitemamount;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.CategoryAtr;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.ItemNameCode;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.history.strategic.ContinuousHistory;
import nts.arc.time.calendar.period.YearMonthPeriod;

import java.util.List;

/**
 * 給与内訳個人金額履歴
 */
@Getter
public class BreakdownAmountHis extends AggregateRoot implements ContinuousHistory<YearMonthHistoryItem, YearMonthPeriod, YearMonth> {

    /**
     * 会社ID
     */
    private String cid;

    /**
     * カテゴリ区分
     */
    private CategoryAtr categoryAtr;

    /**
     * 項目名コード
     */
    private ItemNameCode itemNameCd;

    /**
     * 社員ID
     */
    private String employeeId;

    /**
     * 給与賞与区分
     */
    private SalaryBonusAtr salaryBonusAtr;

    /**
     * 期間
     */
    private List<YearMonthHistoryItem> period;

    /**
     * @param cid            会社ID
     * @param categoryAtr    カテゴリ区分
     * @param itemNameCd     項目名コード
     * @param employeeId     社員ID
     * @param period         期間
     * @param salaryBonusAtr 給与賞与区分
     */

    public BreakdownAmountHis(String cid, int categoryAtr, String itemNameCd, String employeeId, List<YearMonthHistoryItem> period, int salaryBonusAtr) {
        this.cid = cid;
        this.categoryAtr = EnumAdaptor.valueOf(categoryAtr, CategoryAtr.class);
        this.itemNameCd = new ItemNameCode(itemNameCd);
        this.employeeId = employeeId;
        this.period = period;
        this.salaryBonusAtr = EnumAdaptor.valueOf(salaryBonusAtr, SalaryBonusAtr.class);
    }

    @Override
    public List<YearMonthHistoryItem> items() {
        return this.period;
    }
}
