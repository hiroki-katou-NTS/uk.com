package nts.uk.ctx.at.schedule.dom.budget.premium;


import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

public interface IHistPersonCostCalculationDomainService {

    public void updateHistPersonCalculation(PersonCostCalculation domain,String histotyId, DatePeriod period);

    public void registerLaborCalculationSetting(PersonCostCalculation domain, GeneralDate date);
}
