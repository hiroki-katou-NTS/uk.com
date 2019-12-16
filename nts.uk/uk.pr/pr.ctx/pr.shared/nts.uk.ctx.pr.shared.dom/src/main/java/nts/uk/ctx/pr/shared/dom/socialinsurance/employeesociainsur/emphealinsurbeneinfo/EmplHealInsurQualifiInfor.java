package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.history.HistoryItem;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.history.strategic.ContinuousHistory;
import nts.uk.shr.com.history.strategic.ContinuousResidentHistory;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import java.util.List;

/**
* 社員健康保険資格情報
*/
@Getter
public class EmplHealInsurQualifiInfor extends AggregateRoot implements ContinuousResidentHistory<EmpHealthInsurBenefits, DatePeriod, GeneralDate> {
    
    /**
    * 社員ID
    */
    private String employeeId;
    
    /**
    * 得喪期間
    */
    private List<EmpHealthInsurBenefits> mourPeriod;
    
    public EmplHealInsurQualifiInfor(String employeeId, List<EmpHealthInsurBenefits> mourPeriod) {
        this.employeeId = employeeId;
        this.mourPeriod = mourPeriod;
    }

    @Override
    public List<EmpHealthInsurBenefits> items() {
        return mourPeriod;
    }
}
