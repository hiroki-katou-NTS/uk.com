package nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.verticalsetting;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.app.find.budget.external.ExternalBudgetDto;
import nts.uk.ctx.at.schedule.app.find.scheduleitemmanagement.ScheduleItemDto;
import nts.uk.ctx.at.schedule.dom.adapter.dailyattendanceitem.ScDailyAttendanceItemAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.dailyattendanceitem.ScDailyAttendanceItemDto;
import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudgetRepository;
import nts.uk.ctx.at.schedule.dom.budget.external.UnitAtr;
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
	public BaseItemsDto getDailyItems(int attribute) {
		// user contexts
		String companyId = AppContexts.user().companyId();
		
		BaseItemsDto items = new BaseItemsDto();

		// Get daily data
		List<BaseItem> dailyItems = new ArrayList<>();
		List<BaseItem> scheItems = new ArrayList<>();
		List<BaseItem> extenalItems = new ArrayList<>();
		List<Integer> attr = new ArrayList<>();
		attr.add(DailyAttendanceAtr.TIME.value);
		
		List<ScDailyAttendanceItemDto> dailyAttendanceItems = this.dailyAttItemRepository.findByAtr(companyId, attr);
		
		for(int i = 0; i < dailyAttendanceItems.size(); i++) {	
			BaseItem dailyItem = new BaseItem();
			String itemId = Integer.toString(dailyAttendanceItems.get(i).getAttendanceItemId());
			
			dailyItem.setCompanyId(companyId);
			dailyItem.setId(i);
			dailyItem.setItemId(itemId);
			dailyItem.setItemName(dailyAttendanceItems.get(i).getAttendanceName());
			dailyItem.setItemType(ItemTypes.DAILY.value);
			dailyItem.setDispOrder(dailyAttendanceItems.get(i).getDisplayNumber());
			
			dailyItems.add(dailyItem);
			items.setDailyAttItems(dailyItems);
		}
		
		// Get schedule data
		List<ScheduleItemDto> scheduleItems = this.scheduleRepository.findAllScheduleItemByAtr(companyId, attribute).stream().map(c -> ScheduleItemDto.fromDomain(c))
				.collect(Collectors.toList());

		for(int i = 0; i < scheduleItems.size(); i++) {
			BaseItem scheduleItem = new BaseItem();
			int id = items.getDailyAttItems().size() + i;
			
			scheduleItem.setCompanyId(companyId);
			scheduleItem.setId(id);
			scheduleItem.setItemId(scheduleItems.get(i).getScheduleItemId());
			scheduleItem.setItemName(scheduleItems.get(i).getScheduleItemName());
			scheduleItem.setItemType(ItemTypes.SCHEDULE.value);
			scheduleItem.setDispOrder(scheduleItems.get(i).getDispOrder());
			
			scheItems.add(scheduleItem);
			items.setScheduleItems(scheItems);
		}
		
		// Get external data
		List<ExternalBudgetDto> externalItems = this.externalBudgerRepository.findByAtr(companyId, attribute, UnitAtr.DAILY.value).stream().map(c -> ExternalBudgetDto.fromDomain(c))
				.collect(Collectors.toList());
				
		for(int i = 0; i < externalItems.size(); i++) {
			BaseItem externalItem = new BaseItem();
			int id = items.getDailyAttItems().size() + items.getScheduleItems().size() + i;
			
			externalItem.setCompanyId(companyId);
			externalItem.setId(id);
			externalItem.setItemId(externalItems.get(i).getExternalBudgetCode());
			externalItem.setItemName(externalItems.get(i).getExternalBudgetName());
			externalItem.setItemType(ItemTypes.EXTERNAL.value);
			externalItem.setDispOrder(Integer.parseInt(externalItems.get(i).getExternalBudgetCode()));
			
			extenalItems.add(externalItem);
			items.setExternalItems(extenalItems);
		}
		
		return items;
	}
}