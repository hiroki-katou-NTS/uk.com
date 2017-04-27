package nts.uk.ctx.at.schedule.app.find.budget.premium;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.budget.premium.PersonCostCalculation;
import nts.uk.ctx.at.schedule.dom.budget.premium.PersonCostCalculationRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author Doan Duy Hung
 *
 */

public class PremiumBudgetFinder {
	
	@Inject
	private PersonCostCalculationRepository personCostCalculationRepository;
	
	/**
	 * get all Person Cost Calculation by company ID
	 * @return list Person Cost Calculation by company ID
	 */
	public List<PremiumBudgetDto> findByCompanyID() {
		String CID = AppContexts.user().companyCode();
		return this.personCostCalculationRepository.findByCompanyID(CID)
				.stream()
				.map(x -> convertToDto(x))
				.collect(Collectors.toList());
	}
	
	/**
	 * get single Person Cost Calculation by company ID and HID
	 * @param HID history ID
	 * @return single Person Cost Calculation by company ID and HID
	 */
	public PremiumBudgetDto findPremiumBudget(String HID) {
		String CID = AppContexts.user().companyCode();
		Optional<PersonCostCalculation> optional = this.personCostCalculationRepository.find(CID, HID);
		if(!optional.isPresent()) return null;
		return this.convertToDto(optional.get());
	}
	
	private PremiumBudgetDto convertToDto(PersonCostCalculation personCostCalculation){
		return new PremiumBudgetDto(
				personCostCalculation.getCID(), 
				personCostCalculation.getHID(), 
				personCostCalculation.getMemo().v(), 
				personCostCalculation.getUnitprice().unitPrice, 
				personCostCalculation.getStartDate(), 
				personCostCalculation.getEndDate());
	}
}
