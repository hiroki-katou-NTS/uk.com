package nts.uk.ctx.at.schedule.dom.budget.premium;


import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

public interface IHistPersonCostCalculationDomainService {
    public void createHistPersonCostCalculation(GeneralDate date);
    public void updateHistPersonCalculation(String histotyId, DatePeriod period);
}
