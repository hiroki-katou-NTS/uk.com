package nts.uk.ctx.pr.core.app.command.wageprovision.individualwagecontract;

import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.GenericHistYMPeriod;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

public class GenericHistYMPeriodCommand {
    public String historyId;
    public Integer startMonth;
    public Integer endMonth;
    public GenericHistYMPeriod fromCommandToDomain (){
        return new GenericHistYMPeriod(this.historyId, new YearMonthPeriod(new YearMonth(this.startMonth), new YearMonth(this.endMonth)));
    }
}
