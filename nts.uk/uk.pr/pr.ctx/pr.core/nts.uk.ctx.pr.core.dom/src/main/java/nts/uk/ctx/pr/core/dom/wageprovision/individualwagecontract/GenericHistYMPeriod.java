package nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract;

import java.util.Optional;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

/**
 * 年月期間の汎用履歴項目
 */
@AllArgsConstructor
@Getter
public class GenericHistYMPeriod extends DomainObject {

    /**
     * 履歴ID
     */
    private String historyID;

    /**
     * 期間
     */
    private YearMonthPeriod periodYearMonth;

    public GenericHistYMPeriod(String historyId, Integer periodStartYm, Integer periodEndYm) {
        this.historyID = historyId;
        this.periodYearMonth = new YearMonthPeriod(new YearMonth(periodStartYm), new YearMonth(periodEndYm));
    }

}
