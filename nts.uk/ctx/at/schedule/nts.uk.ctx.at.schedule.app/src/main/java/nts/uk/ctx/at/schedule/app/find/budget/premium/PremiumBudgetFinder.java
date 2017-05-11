package nts.uk.ctx.at.schedule.app.find.budget.premium;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.budget.premium.PersonCostCalculation;
import nts.uk.ctx.at.schedule.dom.budget.premium.PersonCostCalculationRepository;
import nts.uk.ctx.at.schedule.dom.budget.premium.PremiumSetting;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author Doan Duy Hung
 *
 */

@Stateless
@Transactional
public class PremiumBudgetFinder {
	
	@Inject
	private PersonCostCalculationRepository personCostCalculationRepository;
	
	/**
	 * get all Person Cost Calculation by company ID
	 * @return list Person Cost Calculation by company ID
	 */
	public List<PremiumBudgetDto> findByCompanyID() {
		String companyID = AppContexts.user().companyId();
		return this.personCostCalculationRepository.findByCompanyID(companyID)
				.stream()
				.map(x -> convertToDto(x))
				.collect(Collectors.toList());
	}
	
	/**
	 * get single Person Cost Calculation by company ID and HID
	 * @param HID history ID
	 * @return single Person Cost Calculation by company ID and HID
	 */
	public PremiumBudgetDto findPremiumBudget(String startDate) {
		String companyID = AppContexts.user().companyId();
		Optional<PersonCostCalculation> optional = this.personCostCalculationRepository.findItemByDate(companyID, GeneralDate.fromString(startDate, "yyyy/MM/dd"));
		if(!optional.isPresent()) return null;
		return this.convertToDto(optional.get());
	}
	
	private PremiumBudgetDto convertToDto(PersonCostCalculation personCostCalculation){
		return new PremiumBudgetDto(
				personCostCalculation.getCompanyID(), 
				personCostCalculation.getHistoryID(), 
				personCostCalculation.getMemo().v(), 
				personCostCalculation.getUnitprice().value, 
				personCostCalculation.getStartDate(), 
				personCostCalculation.getEndDate(),
				personCostCalculation.getPremiumSettings().stream().map(x -> toPremiumSetDto(x)).collect(Collectors.toList()));
	}
	
	private PremiumSetDto toPremiumSetDto(PremiumSetting premiumSetting){
		return new PremiumSetDto(
				premiumSetting.getCompanyID(), 
				premiumSetting.getHistoryID(), 
				premiumSetting.getAttendanceID(), 
				premiumSetting.getPremiumRate().v(),
				premiumSetting.getPremiumName().v(),
				premiumSetting.getInternalID(),
				premiumSetting.getUseAtr().value,
				premiumSetting.getTimeItemIDs());
	}
}
