package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.history.HistoryItem;
import nts.arc.time.calendar.period.DatePeriod;

/**
* 社員健康保険得喪期間項目
*/
@Getter
@Setter
@NoArgsConstructor
public class EmpHealthInsurBenefits extends HistoryItem<DatePeriod, GeneralDate>
{
    
    /**
    * 健保得喪期間履歴ID
    */
    private String healInsurProfirMourHisId;
    
    /**
    * 期間
    */
    private DateHistoryItem datePeriod;
    
    public EmpHealthInsurBenefits(String healInsurProfirMourHisId, DateHistoryItem datePeriod) {
        this.healInsurProfirMourHisId = healInsurProfirMourHisId;
        this.datePeriod = datePeriod;
    }

    @Override
    public DatePeriod span() {
        return datePeriod.span();
    }

    @Override
    public String identifier() {
        return healInsurProfirMourHisId;
    }

    @Override
    public void changeSpan(DatePeriod generalPeriod) {
        this.datePeriod.changeSpan(generalPeriod);
    }
}
