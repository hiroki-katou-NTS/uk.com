package nts.uk.ctx.at.schedule.dom.budget.premium;

import java.util.Optional;

import nts.arc.time.GeneralDate;

public class PersonCostCalculationDomainService {
	
	private static PersonCostCalculationRepository personCostCalculationRepository;
	
	/**
	 * validate input history after last record history
	 * @param CID company ID
	 * @param date input date
	 * @return true input history after last record history
	 */
	public static boolean validateHistory(String CID, GeneralDate date){
		Optional<PersonCostCalculation> optional = personCostCalculationRepository.findAfterDay(CID, date);
		if(optional.isPresent()) return false;
		return true;
	}
}
