package nts.uk.ctx.at.schedule.dom.budget.premium;

import java.math.BigDecimal;
import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.attendance.AttendanceItem;
import nts.uk.shr.com.primitive.Memo;

public interface PersonCostCalculationDomainService {
	
	public PersonCostCalculation createPersonCostCalculationFromJavaType(String companyID, GeneralDate startDate, 
			UnitPrice unitPrice, Memo memo, List<PremiumSetting> premiumSettings);
	
	public void insertPersonCostCalculation(PersonCostCalculation personCostCalculation);
	
	public void updatePersonCostCalculation(PersonCostCalculation personCostCalculation);

	public void deletePersonCostCalculation(PersonCostCalculation personCostCalculation);
}
