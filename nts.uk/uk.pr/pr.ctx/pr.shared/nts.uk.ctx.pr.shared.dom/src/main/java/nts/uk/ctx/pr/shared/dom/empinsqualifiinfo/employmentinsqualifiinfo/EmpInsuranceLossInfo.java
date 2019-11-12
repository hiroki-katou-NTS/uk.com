package nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo;

import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

import java.util.Optional;

@Getter
@Setter
/**
 * 雇用保険喪失時情報
 */
public class EmpInsuranceLossInfo extends AggregateRoot{

    /**
     * 社員ID
     */
    private String SID;

    /**
     * 喪失原因
     */
    private Optional<CauseOfLossAtr> causeOfLossAtr;

    /**
     * 離職票交付希望区分
     */
    private Optional<RequestForIssuance> requestForIssuance;

    /**
     * 補充予定区分
     */
    private Optional<ScheduleForReplenishment> scheduleForReplenishment;

    /**
     * 喪失原因
     */
    private Optional<CauseOfLossEmpInsurance> causeOfLossEmpInsurance;

    /**
     * 週間の所定労働時間
     */
    private Optional<WorkingTime> scheduleWorkingHourPerWeek;

    public EmpInsuranceLossInfo(){};

    public EmpInsuranceLossInfo(String SID,
                                Integer causeOfLossAtr,
                                Integer requestForIssuance,
                                Integer scheduleForReplenishment,
                                String causeOfLossEmpInsurance,
                                Integer scheduleWorkingHourPerWeek) {
        this.SID = SID;
        this.causeOfLossEmpInsurance = causeOfLossEmpInsurance == null ? Optional.empty() : Optional.of(new CauseOfLossEmpInsurance(causeOfLossEmpInsurance));
        this.causeOfLossAtr = causeOfLossAtr == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(causeOfLossAtr, CauseOfLossAtr.class));
        this.requestForIssuance = requestForIssuance == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(requestForIssuance, RequestForIssuance.class));
        this.scheduleForReplenishment = scheduleForReplenishment == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(scheduleForReplenishment, ScheduleForReplenishment.class));
        this.scheduleWorkingHourPerWeek = scheduleWorkingHourPerWeek == null ? Optional.empty() : Optional.of(new WorkingTime(scheduleWorkingHourPerWeek));

    }

}
