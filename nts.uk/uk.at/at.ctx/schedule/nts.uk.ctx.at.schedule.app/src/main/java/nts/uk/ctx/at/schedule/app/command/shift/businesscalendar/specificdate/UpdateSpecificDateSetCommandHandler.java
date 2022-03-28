package nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.specificdate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday.PublicHoliday;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday.PublicHolidayRepository;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.CompanySpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.CompanySpecificDateRepository;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.OneDaySpecificItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.SpecificDateItemNo;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.WorkplaceSpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.WorkplaceSpecificDateRepository;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class UpdateSpecificDateSetCommandHandler extends CommandHandler<UpdateSpecificDateSetCommand>{
	@Inject
	private WorkplaceSpecificDateRepository workplaceRepo;
	@Inject
	private CompanySpecificDateRepository companyRepo;
	@Inject
	private PublicHolidayRepository holidayRepo;
	/**
	 * Update Specific Date Set 
	 */
	private static final String DATE_FORMAT = "yyyy/MM/dd";
	
	@Override
	protected void handle(CommandHandlerContext<UpdateSpecificDateSetCommand> context) {
		UpdateSpecificDateSetCommand data = context.getCommand();
		GeneralDate startDate = GeneralDate.fromString(data.getStrDate(), DATE_FORMAT);
		GeneralDate endDate = GeneralDate.fromString(data.getEndDate(), DATE_FORMAT);
		if(startDate.after(endDate)){
			//strDate > endDate => display msg_136
			throw new BusinessException("Msg_136");
		}
		if(data.getDayofWeek().isEmpty()){
			//date set is emtity => display msg_136
			throw new BusinessException("Msg_137");
		}
		if(data.getLstTimeItemId().isEmpty()){
			//time item seleced is emtity => display msg_136
			throw new BusinessException("Msg_138");
		}
		if(data.getUtil()==1){//company
			UpdatebyDayforCompany(startDate, endDate, data.getDayofWeek(), data.getLstTimeItemId(),data.getSetUpdate());
		}else{//workplace
			UpdatebyDayforWorkPlace(startDate, endDate, data.getDayofWeek(), data.getLstTimeItemId(),data.getSetUpdate(),data.getWorkplaceId());
		}
	}
	/**
	 * check setting date
	 * @param dateInString
	 * @param dayofWeek
	 * @return
	 */
	public static boolean checkDayofWeek(GeneralDate dateInString, List<Integer> dayofWeek){
		Integer dateInInt = dateInString.dayOfWeek();
		boolean value = false;
		for (Integer integer : dayofWeek) {
			if(integer == dateInInt){
				value = true;
				break;
			}
		}
		return value;
	}
	/**
	 * check Selected Holiday
	 * @param dayofWeek
	 * @param strDate
	 * @param endDate
	 * @return
	 */
	public List<PublicHoliday> checkSelectedHoliday(List<Integer> dayofWeek, GeneralDate strDate, GeneralDate endDate){
		String companyId = AppContexts.user().companyId();
		boolean check = false;
		for (Integer day : dayofWeek) {
			if(day==0){
				check = true;
			}
		}
		List<PublicHoliday> lstHoliday = new ArrayList<>();
		if(check){
			lstHoliday = holidayRepo.getpHolidayWhileDate(companyId, strDate, endDate);
		}
		return lstHoliday;
	}
	/**
	 * check Holiday: check date is holiday?
	 * @param lstHoliday
	 * @param date
	 * @return
	 */
	public static boolean checkHoliday(List<PublicHoliday> lstHoliday, GeneralDate date){
		boolean check = false;
		if(lstHoliday.isEmpty()){
			return false;
		}
		for (PublicHoliday publicHoliday : lstHoliday) {
			if(date.equals(publicHoliday.getDate())){
				return true;
			}
		}
		return check;
	}
	/**
	 * check Set: seting or not setting date
	 * @param lstHoliday
	 * @param date
	 * @param dayofWeek
	 * @return
	 */
	public boolean checkSet(List<PublicHoliday> lstHoliday,GeneralDate date,List<Integer> dayofWeek ){
		if(lstHoliday.isEmpty() && !checkDayofWeek(date,dayofWeek)){
			return false;
		}
		if(!checkDayofWeek(date,dayofWeek)&&!checkHoliday(lstHoliday,date)){//not setting
			return false;
		}
		return true;
	}
	/**
	 * Update by Day for WorkPlace
	 * @param strDate
	 * @param endDate
	 * @param dayofWeek
	 * @param lstTimeItemId
	 * @param setUpdate
	 * @param workplaceId
	 */
	private void UpdatebyDayforWorkPlace(GeneralDate strDate, GeneralDate endDate, List<Integer> dayofWeek, List<Integer> lstTimeItemId, int setUpdate, String workplaceId) {
		String companyId = AppContexts.user().companyId();
		GeneralDate date = strDate;
		//check selected public holiday
		List<PublicHoliday> lstHoliday = checkSelectedHoliday(dayofWeek, strDate, endDate);
		
		while (strDate.beforeOrEquals(endDate)) {
			if (!checkSet(lstHoliday, strDate, dayofWeek)) {//not setting
				date = date.addDays(1);
				strDate = date;
				continue;
			}
			if (setUpdate == 1) {
				List<SpecificDateItemNo> oldSpecificDayItems = this.workplaceRepo.get(workplaceId, strDate)
						.map(t -> t.getOneDaySpecificItem().getSpecificDayItems())
						.orElse(Collections.emptyList());
				List<SpecificDateItemNo> specificDayItems = null;
				
				if (oldSpecificDayItems.isEmpty()) {
					specificDayItems = lstTimeItemId.stream()
							.map(t -> new SpecificDateItemNo(t))
							.collect(Collectors.toList());
				} else {
					specificDayItems = new ArrayList<>();
					List<Integer> oldSpecDayItemNos = oldSpecificDayItems.stream()
							.map(t -> t.v())
							.collect(Collectors.toList());
					for (Integer itemId : lstTimeItemId) {
						if (!oldSpecDayItemNos.contains(itemId)) {
							specificDayItems.add(new SpecificDateItemNo(itemId));
						}
					}
				}
				
				this.workplaceRepo.insert(companyId, new WorkplaceSpecificDateItem(workplaceId, strDate, OneDaySpecificItem.create(specificDayItems)));
				
			} else {
				//既に設定されている内容をクリアし、今回選択したものだけを設定する - add new
				//delete setting old workplace
				this.workplaceRepo.delete(workplaceId, strDate);
				//add new
				List<SpecificDateItemNo> specificDayItems = lstTimeItemId.stream()
						.map(t -> new SpecificDateItemNo(t))
						.collect(Collectors.toList());
				this.workplaceRepo.insert(companyId, new WorkplaceSpecificDateItem(workplaceId, strDate, OneDaySpecificItem.create(specificDayItems)));
			}
			date = date.addDays(1);
			strDate = date;
		}
	}
	/**
	 * Update by Day for Company
	 * @param strDate
	 * @param endDate
	 * @param dayofWeek
	 * @param lstTimeItemId
	 * @param setUpdate
	 */
	private void UpdatebyDayforCompany(GeneralDate strDate, GeneralDate endDate, List<Integer> dayofWeek, List<Integer> lstTimeItemId, int setUpdate) {
		String companyId = AppContexts.user().companyId();
		GeneralDate date = strDate;
		//check slected public holiday
		List<PublicHoliday> lstHoliday = checkSelectedHoliday(dayofWeek, strDate, endDate);
		
		while (strDate.beforeOrEquals(endDate)) {
			if (!checkSet(lstHoliday, strDate, dayofWeek)) {//not setting
				date = date.addDays(1);
				strDate = date;
				continue;
			}
			if (setUpdate == 1) {
				List<SpecificDateItemNo> oldSpecificDayItems = this.companyRepo.get(companyId, strDate)
						.map(t -> t.getOneDaySpecificItem().getSpecificDayItems())
						.orElse(Collections.emptyList());
				List<SpecificDateItemNo> specificDayItems = null;
				
				if (oldSpecificDayItems.isEmpty()) {
					specificDayItems = lstTimeItemId.stream()
							.map(t -> new SpecificDateItemNo(t))
							.collect(Collectors.toList());
				} else {
					specificDayItems = new ArrayList<>();
					List<Integer> oldSpecDayItemNos = oldSpecificDayItems.stream()
							.map(t -> t.v())
							.collect(Collectors.toList());
					for (Integer itemId : lstTimeItemId) {
						if (!oldSpecDayItemNos.contains(itemId)) {
							specificDayItems.add(new SpecificDateItemNo(itemId));
						}
					}
				}
				
				this.companyRepo.insert(new CompanySpecificDateItem(companyId, strDate, OneDaySpecificItem.create(specificDayItems)));
				
			} else {
				//既に設定されている内容をクリアし、今回選択したものだけを設定する - add new
				//delete setting old company
				this.companyRepo.delete(companyId, strDate);
				//add new
				List<SpecificDateItemNo> specificDayItems = lstTimeItemId.stream()
						.map(t -> new SpecificDateItemNo(t))
						.collect(Collectors.toList());
				this.companyRepo.insert(new CompanySpecificDateItem(companyId, strDate, OneDaySpecificItem.create(specificDayItems)));
			}
			date = date.addDays(1);
			strDate = date;
		}
	}

}
