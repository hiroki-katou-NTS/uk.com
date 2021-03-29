package nts.uk.ctx.at.schedule.dom.budget.premium;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.shr.com.primitive.Memo;
/**
 * 
 * @author Doan Duy Hung
 *
 */
public interface PersonCostCalculationDomainService {
	

	public void deletePersonCostCalculation(String companyId, String histotyId);

	public void updateHistPersonCalculation(PersonCostCalculation domain,String histotyId, GeneralDate generalDate);

	public void registerLaborCalculationSetting(PersonCostCalculation domain, GeneralDate date);






}
