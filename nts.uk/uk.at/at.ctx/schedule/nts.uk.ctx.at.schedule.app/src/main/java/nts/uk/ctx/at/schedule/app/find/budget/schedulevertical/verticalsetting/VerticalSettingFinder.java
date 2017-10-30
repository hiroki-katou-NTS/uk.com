package nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.verticalsetting;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.app.find.budget.external.ExternalBudgetDto;
import nts.uk.ctx.at.schedule.app.find.scheduleitemmanagement.ScheduleItemDto;
import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudgetRepository;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.VerticalCalSet;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.VerticalSettingRepository;
import nts.uk.ctx.at.schedule.dom.scheduleitemmanagement.ScheduleItemManagementRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author tanlv
 *
 */

@Stateless
public class VerticalSettingFinder {
	@Inject
	private VerticalSettingRepository repository;
	
	//@Inject
	//private DailyAttendanceItemRepository dailyAttItemRepository;
	
	@Inject
	private ScheduleItemManagementRepository scheduleRepository;
	
	@Inject
	private ExternalBudgetRepository externalBudgerRepository;
	
	/**
	 * Find by company id.
	 *
	 * @return the list
	 */
	public List<VerticalSettingDto> findAllVerticalCalSet() {
		// user contexts
		String companyId = AppContexts.user().companyId();

		return this.repository.findAllVerticalCalSet(companyId).stream().map(c -> VerticalSettingDto.fromDomain(c))
				.collect(Collectors.toList());
	}
	
	/**
	 * Find by Cd.
	 *
	 * @return the item
	 */
	public VerticalSettingDto getVerticalCalSetByCode(String verticalCalCd) {
		// user contexts
		String companyId = AppContexts.user().companyId();
		
		Optional<VerticalCalSet> data = this.repository.getVerticalCalSetByCode(companyId, verticalCalCd);
		
		if(data.isPresent()){
			return VerticalSettingDto.fromDomain(data.get());
		}
		
		return null;
	}
	
	/**
	 * Get daily items.
	 *
	 * @return the list
	 */
	public List<DailyItemsDto> getDailyItems(int attribute, int budgetAtr, int unitAtr) {
		// user contexts
		String companyId = AppContexts.user().companyId();
		
		List<DailyItemsDto> dailyItemsDtos = new ArrayList<>();

		// Get daily data
		DailyItemsDto dailyItem = new DailyItemsDto();
		dailyItem.setCompanyId(companyId);
		dailyItem.setId(0);
		dailyItem.setItemId("001");
		dailyItem.setItemName("Daily 001");
		dailyItem.setItemType(ItemTypes.Daily.value);
		dailyItem.setDispOrder(0);
		
		dailyItemsDtos.add(dailyItem);
		
		// Get schedule data
		List<ScheduleItemDto> scheduleItems = this.scheduleRepository.findAllScheduleItemByAtr(companyId, attribute).stream().map(c -> ScheduleItemDto.fromDomain(c))
				.collect(Collectors.toList());

		for(int i = 0; i < scheduleItems.size(); i++) {
			DailyItemsDto dailyItem2 = new DailyItemsDto();
			int index = i == 0 ? 1 : i;
			
			dailyItem2.setCompanyId(companyId);
			dailyItem2.setId(dailyItemsDtos.size() + index);
			dailyItem2.setItemId(scheduleItems.get(i).getScheduleItemId());
			dailyItem2.setItemName(scheduleItems.get(i).getScheduleItemName());
			dailyItem2.setItemType(ItemTypes.Schedule.value);
			dailyItem2.setDispOrder(scheduleItems.get(i).getDispOrder());
			
			dailyItemsDtos.add(dailyItem2);
		}
		
		// Get external data
		List<ExternalBudgetDto> externalItems = this.externalBudgerRepository.findByAtr(companyId, budgetAtr, unitAtr).stream().map(c -> ExternalBudgetDto.fromDomain(c))
				.collect(Collectors.toList());
				
		for(int i = 0; i < scheduleItems.size(); i++) {
			DailyItemsDto dailyItem3 = new DailyItemsDto();
			int index = i == 0 ? 1 : i;
			
			dailyItem3.setCompanyId(companyId);
			dailyItem3.setId(dailyItemsDtos.size() + index);
			dailyItem3.setItemId(externalItems.get(i).getExternalBudgetCode());
			dailyItem3.setItemName(externalItems.get(i).getExternalBudgetName());
			dailyItem3.setItemType(ItemTypes.External.value);
			dailyItem3.setDispOrder(Integer.parseInt(externalItems.get(i).getExternalBudgetCode()));
			
			dailyItemsDtos.add(dailyItem3);
		}
		
		return dailyItemsDtos;
	}
}

enum ItemTypes {
	Daily(0),
	Schedule(1),	
	External(2);
	
	int value;  
	
	ItemTypes(int value){  
		this.value=value;  
	}
}