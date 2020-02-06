package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.history.strategic.ContinuousResidentHistory;
import nts.arc.time.calendar.period.DatePeriod;

import java.util.List;
import java.util.stream.Collectors;

/**
* 社員健康保険資格情報
*/
@Getter
@Setter
@NoArgsConstructor
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
    	super();
        this.employeeId = employeeId;
        this.mourPeriod = mourPeriod;
    }

    @Override
    public List<EmpHealthInsurBenefits> items() {
        return mourPeriod;
    }

    public List<String> getHistoryIds() {
        return mourPeriod.stream().map(x -> x.identifier()).collect(Collectors.toList());
    }
}
