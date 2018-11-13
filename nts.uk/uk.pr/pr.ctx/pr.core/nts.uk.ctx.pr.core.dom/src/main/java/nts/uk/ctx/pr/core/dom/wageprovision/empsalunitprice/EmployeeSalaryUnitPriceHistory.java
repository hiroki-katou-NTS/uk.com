package nts.uk.ctx.pr.core.dom.wageprovision.empsalunitprice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.history.strategic.ContinuousHistory;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

/**
 * 社員給与単価履歴
 */
@Getter
public class EmployeeSalaryUnitPriceHistory extends AggregateRoot implements ContinuousHistory<YearMonthHistoryItem, YearMonthPeriod, YearMonth> {

    /**
     * 個人単価コード
     */
    private PerUnitPriceCode personalUnitPriceCode;

    /**
     * 社員ID
     */
    private String employeeID;

    /**
     * 期間
     */
    private List<YearMonthHistoryItem> period;

    /**
     * 社員給与単価履歴
     *
     * @param personalUnitPriceCode 個人単価コード
     * @param employeeId            社員ID
     * @param period                期間
     */
    public EmployeeSalaryUnitPriceHistory(String personalUnitPriceCode, String employeeId, List<YearMonthHistoryItem> period) {
        this.employeeID = employeeId;
        this.period = period;
        this.personalUnitPriceCode = new PerUnitPriceCode(personalUnitPriceCode);
    }

    @Override
    public List<YearMonthHistoryItem> items() {
        return period;
    }
}