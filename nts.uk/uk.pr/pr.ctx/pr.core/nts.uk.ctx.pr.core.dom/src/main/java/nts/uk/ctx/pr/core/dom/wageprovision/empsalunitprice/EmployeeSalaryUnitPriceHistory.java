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

/**
* 社員給与単価履歴
*/
@Getter
public class EmployeeSalaryUnitPriceHistory extends AggregateRoot {
    
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
    private List<GenericHistYMPeriod> period;
    
    public EmployeeSalaryUnitPriceHistory(String personalUnitPriceCode, String employeeId, List<GenericHistYMPeriod> period) {
        this.employeeID = employeeId;
        this.period = period;//new GenericHistYMPeriod(historyId,startYearMonth,endYearMonth,indvidualUnitPrice);
        this.personalUnitPriceCode = new PerUnitPriceCode(personalUnitPriceCode);
    }
    
}
