package nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.verticalsetting;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.app.find.scheduleitemmanagement.ScheduleItemDto;
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
	
	@Inject
	private ScheduleItemManagementRepository scheduleRepository;
	
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
	public List<DailyItemsDto> getDailyItems(int attribute) {
		// user contexts
		String companyId = AppContexts.user().companyId();
		
		List<DailyItemsDto> dailyItemsDtos = new ArrayList<>();

		// Get schedule data
		List<ScheduleItemDto> scheduleItems = this.scheduleRepository.findAllScheduleItemByAtr(companyId, attribute).stream().map(c -> ScheduleItemDto.fromDomain(c))
				.collect(Collectors.toList());
		
		// Sort schedule items by display order
		
		
		// Add schedule items to result list
		for(int i = 0; i < scheduleItems.size(); i++) {
			DailyItemsDto dailyItem = new DailyItemsDto();
			dailyItem.setCompanyId(companyId);
			dailyItem.setId(i);
			dailyItem.setItemId(scheduleItems.get(i).getScheduleItemId());
			dailyItem.setItemName(scheduleItems.get(i).getScheduleItemName());
			dailyItem.setItemType(0);
			dailyItem.setDispOrder(scheduleItems.get(i).getDispOrder());
			
			dailyItemsDtos.add(dailyItem);
		}
		
		// Get budget data
		
		
		// Get external data
		
				
		return dailyItemsDtos;
	}
}