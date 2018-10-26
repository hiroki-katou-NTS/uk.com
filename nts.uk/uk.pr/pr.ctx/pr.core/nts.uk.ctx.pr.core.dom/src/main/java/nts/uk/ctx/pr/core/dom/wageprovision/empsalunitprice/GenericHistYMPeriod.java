package nts.uk.ctx.pr.core.dom.wageprovision.empsalunitprice;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.enums.EnumAdaptor;

/**
* 年月期間の汎用履歴項目
*/
@AllArgsConstructor
@Getter
public class GenericHistYMPeriod extends DomainObject
{
    
    /**
    * 履歴ID
    */
    private String historyID;
    
    /**
    * 期間
    */
    private YearMonthPeriod periodYearMonth;
    /**
    * 個人単価
     */
    private SalaryUnitPrice individualUnitPrice;


    public GenericHistYMPeriod(String historyId, int startYearMonth, int endYearMonth, BigDecimal indvidualUnitPrice) {
        this.historyID = historyId;
        this.periodYearMonth = new YearMonthPeriod(startYearMonth,endYearMonth);
        this.individualUnitPrice=new SalaryUnitPrice(indvidualUnitPrice);
    }
    
}
