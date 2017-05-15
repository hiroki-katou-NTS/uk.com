package nts.uk.ctx.at.schedule.dom.budget.premium;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.shr.com.primitive.Memo;

public interface PersonCostCalculationDomainService {
	
	/**
	 * validate input history after last record history
	 * @param CID company ID
	 * @param date input date
	 * @return true input history after last record history
	 * 
	 */
	public PersonCostCalculation createFromJavaType(String companyID, Memo memo,
			UnitPrice unitPrice, GeneralDate startDate, List<PremiumSetting> premiumSettings);
	
	public void insertPersonCostCalculation(PersonCostCalculation personCostCalculation);
	
	public void updatePersonCostCalculation(PersonCostCalculation personCostCalculation);

	public void deletePersonCostCalculation(PersonCostCalculation personCostCalculation);
}
