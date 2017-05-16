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
public class PersonCostCalculationSettingFinder {
	
	@Inject
	private PersonCostCalculationRepository personCostCalculationRepository;
	
	/**
	 * get all Person Cost Calculation by company ID
	 * @return list Person Cost Calculation by company ID
	 */
	public List<PersonCostCalculationSettingDto> findByCompanyID() {
		String companyID = AppContexts.user().companyId();
		return this.personCostCalculationRepository.findByCompanyID(companyID)
				.stream()
				.map(x -> convertToDto(x))
				.collect(Collectors.toList());
	}
	
	private PersonCostCalculationSettingDto convertToDto(PersonCostCalculation personCostCalculation){
		return new PersonCostCalculationSettingDto(
				personCostCalculation.getCompanyID(), 
				personCostCalculation.getHistoryID(), 
				personCostCalculation.getStartDate(), 
				personCostCalculation.getEndDate(),
				personCostCalculation.getUnitPrice().value,
				personCostCalculation.getMemo().v(), 
				personCostCalculation.getPremiumSettings().stream().map(x -> toPremiumSetDto(x)).collect(Collectors.toList()));
	}
	
	private PremiumSetDto toPremiumSetDto(PremiumSetting premiumSetting){
		return new PremiumSetDto(
				premiumSetting.getCompanyID(), 
				premiumSetting.getHistoryID(), 
				premiumSetting.getPremiumID(), 
				premiumSetting.getRate().v(),
				premiumSetting.getAttendanceID(),
				premiumSetting.getName().v(),
				premiumSetting.getDisplayNumber(),
				premiumSetting.getUseAtr().value,
				premiumSetting.getAttendanceItems());
	}
}
