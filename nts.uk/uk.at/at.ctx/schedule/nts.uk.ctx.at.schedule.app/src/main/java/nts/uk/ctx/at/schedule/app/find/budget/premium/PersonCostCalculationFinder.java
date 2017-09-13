package nts.uk.ctx.at.schedule.app.find.budget.premium;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.uk.ctx.at.schedule.app.find.budget.premium.dto.PersonCostCalculationSettingDto;
import nts.uk.ctx.at.schedule.app.find.budget.premium.dto.PremiumItemDto;
import nts.uk.ctx.at.schedule.app.find.budget.premium.dto.PremiumSetDto;
import nts.uk.ctx.at.schedule.app.find.budget.premium.dto.ShortAttendanceItemDto;
import nts.uk.ctx.at.schedule.dom.budget.premium.PersonCostCalculation;
import nts.uk.ctx.at.schedule.dom.budget.premium.PersonCostCalculationRepository;
import nts.uk.ctx.at.schedule.dom.budget.premium.PremiumItemRepository;
import nts.uk.ctx.at.schedule.dom.budget.premium.PremiumSetting;
import nts.uk.ctx.at.schedule.dom.budget.premium.service.AttendanceTypePrimiumAdapter;
import nts.uk.ctx.at.schedule.dom.budget.premium.service.AttendanceNamePriniumAdapter;
import nts.uk.ctx.at.schedule.dom.budget.premium.service.AttendanceNamePriniumDto;
import nts.uk.ctx.at.schedule.dom.budget.premium.service.AttendanceTypePriServiceDto;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author Doan Duy Hung
 *
 */

@Stateless
@Transactional
public class PersonCostCalculationFinder {
	
	@Inject
	private PersonCostCalculationRepository personCostCalculationRepository;
	
	@Inject
	private PremiumItemRepository premiumItemRepository;
	
	@Inject
	private AttendanceTypePrimiumAdapter atType;
	@Inject
	private AttendanceNamePriniumAdapter atName;
	
	/**
	 * get all Person Cost Calculation by company ID
	 * @return list Person Cost Calculation by company ID
	 */
	public List<PersonCostCalculationSettingDto> findPersonCostCalculationByCompanyID() {
		String companyID = AppContexts.user().companyId();
		return this.personCostCalculationRepository.findByCompanyID(companyID)
				.stream()
				.map(x -> convertToDto(x))
				.collect(Collectors.toList());
	}
	
	/**
	 * get List Premium Item  by company ID
	 * @return List Premium Item  by company ID
	 */
	public List<PremiumItemDto> findPremiumItemByCompanyID(){
		String companyID = AppContexts.user().companyId();
		return this.premiumItemRepository.findByCompanyID(companyID)
				.stream()
				.map(x -> new PremiumItemDto(
						companyID, 
						x.getDisplayNumber(),
						x.getName().v(), 
						x.getUseAtr().value))
				.collect(Collectors.toList());
	}
	
	/**
	 * convert PersonCostCalculation to PersonCostCalculationSettingDto
	 * @param personCostCalculation PersonCostCalculation Object
	 * @return PersonCostCalculationSettingDto Object
	 */
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
	
	/**
	 * convert PremiumSetting to PremiumSetDto
	 * @param premiumSetting PremiumSetting Object
	 * @return PremiumSetDto Object
	 */
	private PremiumSetDto toPremiumSetDto(PremiumSetting premiumSetting){
		return new PremiumSetDto(
				premiumSetting.getCompanyID(), 
				premiumSetting.getHistoryID(), 
				premiumSetting.getDisplayNumber(), 
				premiumSetting.getRate().v(),
				premiumSetting.getName().v(),
				premiumSetting.getUseAtr().value,
				premiumSetting.getAttendanceItems().stream().map(x -> new ShortAttendanceItemDto(x, x.toString())).collect(Collectors.toList()));
	}
	/**
	 * get attendance at screen use
	 * @param screenUseAtr
	 * @return
	 */
	public List<AttendanceTypePriServiceDto> atTypes(int screenUseAtr){
		String companyID = AppContexts.user().companyId();
		List<AttendanceTypePriServiceDto> data = atType.getItemByScreenUseAtr(companyID, screenUseAtr);
		return data;
	}
	
	public List<AttendanceNamePriniumDto> atNames(List<Integer> dailyAttendanceItemIds){
		List<AttendanceNamePriniumDto> data = atName.getDailyAttendanceItemName(dailyAttendanceItemIds);
		return data;
	}
}
