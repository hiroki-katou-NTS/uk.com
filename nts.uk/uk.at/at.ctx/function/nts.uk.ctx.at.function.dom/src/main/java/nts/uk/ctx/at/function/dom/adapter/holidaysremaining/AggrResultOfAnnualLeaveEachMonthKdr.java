package nts.uk.ctx.at.function.dom.adapter.holidaysremaining;


import lombok.Getter;
import lombok.Setter;
import nts.arc.time.YearMonth;

@Getter
@Setter
public class AggrResultOfAnnualLeaveEachMonthKdr {
    private YearMonth yearMonth;
    private AggrResultOfAnnualLeaveKdr aggrResultOfAnnualLeave;


    public AggrResultOfAnnualLeaveEachMonthKdr(YearMonth yearMonth, AggrResultOfAnnualLeaveKdr aggrResultOfAnnualLeave) {
        super();
        this.yearMonth = yearMonth;
        this.aggrResultOfAnnualLeave = aggrResultOfAnnualLeave;
    }
}
