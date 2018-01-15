package nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.verticalsetting;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.adapter.dailyattendanceitem.ScDailyAttendanceItemAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.dailyattendanceitem.ScDailyAttendanceItemDto;
import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudget;
import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudgetRepository;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.VerticalCalSet;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.VerticalSettingRepository;
import nts.uk.ctx.at.schedule.dom.scheduleitemmanagement.ScheduleItem;
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
	private ScDailyAttendanceItemAdapter dailyAttItemRepository;
	
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
	public List<BaseItemsDto> getDailyItems(DailyItemsParamDto param) {
		// user contexts
		String companyId = AppContexts.user().companyId();

		// Get daily data
		List<BaseItemsDto> baseItems = new ArrayList<>();
		
		List<ScDailyAttendanceItemDto> dailyAttendanceItems = this.dailyAttItemRepository.findByAtr(companyId, param.getDailyAttendanceItemAtrs());
		
		for(int i = 0; i < dailyAttendanceItems.size(); i++) {	
			BaseItemsDto dailyItem = new BaseItemsDto();
			String itemId = Integer.toString(dailyAttendanceItems.get(i).getAttendanceItemId());
			String id = itemId + "" + ItemTypes.DAILY.value;
			
			dailyItem.setCompanyId(companyId);
			dailyItem.setId(id);
			dailyItem.setItemId(itemId);
			dailyItem.setItemName(dailyAttendanceItems.get(i).getAttendanceName());
			dailyItem.setItemType(ItemTypes.DAILY.value);
			dailyItem.setDispOrder(dailyAttendanceItems.get(i).getDisplayNumber());
			
			baseItems.add(dailyItem);
		}
		
		// Get schedule data
		List<ScheduleItem> scheduleItems = this.scheduleRepository.findAllScheduleItemByAtr(companyId, param.getScheduleAtr());

		for(int i = 0; i < scheduleItems.size(); i++) {
			BaseItemsDto scheduleItem = new BaseItemsDto();
			String id = scheduleItems.get(i).getScheduleItemId() + "" + ItemTypes.SCHEDULE.value;
			
			scheduleItem.setCompanyId(companyId);
			scheduleItem.setId(id);
			scheduleItem.setItemId(scheduleItems.get(i).getScheduleItemId());
			scheduleItem.setItemName(scheduleItems.get(i).getScheduleItemName());
			scheduleItem.setItemType(ItemTypes.SCHEDULE.value);
			scheduleItem.setDispOrder(scheduleItems.get(i).getDispOrder());
			
			baseItems.add(scheduleItem);
		}
		
		// Get external data
		List<ExternalBudget> externalItems = this.externalBudgerRepository.findByAtr(companyId, param.getBudgetAtr(), param.getUnitAtr());
				
		for(int i = 0; i < externalItems.size(); i++) {
			BaseItemsDto externalItem = new BaseItemsDto();
			String id = externalItems.get(i).getExternalBudgetCd().v() + "" + ItemTypes.EXTERNAL.value;
			externalItem.setCompanyId(companyId);
			externalItem.setId(id);
			externalItem.setItemId(externalItems.get(i).getExternalBudgetCd().v());
			externalItem.setItemName(externalItems.get(i).getExternalBudgetName().v());
			externalItem.setItemType(ItemTypes.EXTERNAL.value);
			externalItem.setDispOrder(i);
			
			baseItems.add(externalItem);
		}
		
		return baseItems;
	}
}