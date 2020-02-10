package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo;
import java.util.Optional;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.enums.EnumAdaptor;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.arc.time.calendar.period.DatePeriod;

/**
* 社員健康保険得喪期間項目
*/
@Getter
public class EmpHealthInsurBenefits extends DomainObject
{
    
    /**
    * 健保得喪期間履歴ID
    */
    private String healInsurProfirMourHisId;
    
    /**
    * 期間
    */
    private DateHistoryItem datePeriod;
    
    public EmpHealthInsurBenefits( String healInsurProfirMourHisId,DateHistoryItem datePeriod) {
        this.healInsurProfirMourHisId = healInsurProfirMourHisId;
        this.datePeriod = datePeriod;
    }
    
}
