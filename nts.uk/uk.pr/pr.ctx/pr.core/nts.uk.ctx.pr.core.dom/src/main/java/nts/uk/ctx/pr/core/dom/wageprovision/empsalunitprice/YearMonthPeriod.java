package nts.uk.ctx.pr.core.dom.wageprovision.empsalunitprice;

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
* 年月期間
*/
@AllArgsConstructor
@Getter
@Data
public class YearMonthPeriod extends DomainObject
{
    
    /**
    * 開始年月
    */
    private int startYearMonth;
    
    /**
    * 終了年月
    */
    private int endYearMonth;
    
}
