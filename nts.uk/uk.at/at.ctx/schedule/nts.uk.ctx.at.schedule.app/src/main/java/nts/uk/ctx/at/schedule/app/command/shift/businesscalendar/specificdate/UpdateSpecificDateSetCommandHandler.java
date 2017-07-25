package nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.specificdate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday.PublicHoliday;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday.PublicHolidayRepository;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.company.CompanySpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.company.CompanySpecificDateRepository;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.workplace.WorkplaceSpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.workplace.WorkplaceSpecificDateRepository;
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
	@Override
	protected void handle(CommandHandlerContext<UpdateSpecificDateSetCommand> context) {
		UpdateSpecificDateSetCommand data = context.getCommand();
		if(data.getStrDate()>data.getEndDate()){
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
			UpdatebyDayforCompany(data.getStrDate(),data.getEndDate(), data.getDayofWeek(), data.getLstTimeItemId(),data.getSetUpdate());
		}else{//workplace
			UpdatebyDayforWorkPlace(data.getStrDate(),data.getEndDate(), data.getDayofWeek(), data.getLstTimeItemId(),data.getSetUpdate(),data.getWorkplaceId());
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
	public List<PublicHoliday> checkSelectedHoliday(List<Integer> dayofWeek, int strDate, int endDate){
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
	public static boolean checkHoliday(List<PublicHoliday> lstHoliday, BigDecimal date){
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
		String dateStr = String.format("%04d%02d%02d", date.year(), date.month(),date.day());
		int dateInt = Integer.valueOf(dateStr);
		BigDecimal dateBigDecimal = BigDecimal.valueOf(dateInt);
		if(!checkDayofWeek(date,dayofWeek)&&!checkHoliday(lstHoliday,dateBigDecimal)){//not setting
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
	private void UpdatebyDayforWorkPlace(int strDate, int endDate, List<Integer> dayofWeek, List<Integer> lstTimeItemId ,int setUpdate,String workplaceId){
		GeneralDate sDate = GeneralDate.fromString(String.valueOf(strDate), "yyyyMMdd");
		GeneralDate eDate = GeneralDate.fromString(String.valueOf(endDate), "yyyyMMdd");
		GeneralDate date = sDate;
		String eDateStr = String.format("%04d%02d%02d", eDate.year(), eDate.month(),eDate.day());
		String dateStr = String.format("%04d%02d%02d", date.year(), date.month(),date.day());
		int dateInt = Integer.valueOf(dateStr);
		BigDecimal dateBigDecimal = BigDecimal.valueOf(dateInt);
		//check slected public holiday
		List<PublicHoliday> lstHoliday = checkSelectedHoliday(dayofWeek, strDate, endDate);
		while(dateStr.compareTo(eDateStr)<=0){
			if(!checkSet(lstHoliday,date,dayofWeek)){//not setting
				date = date.addDays(1);
				dateStr = String.format("%04d%02d%02d", date.year(), date.month(),date.day());
				continue;
			}
			//setting
			dateInt = Integer.valueOf(dateStr);
			dateBigDecimal = BigDecimal.valueOf(dateInt);
			if(setUpdate==1){
				//既に設定されている内容は据え置き、追加で設定する - complement
				//list item da co san trong db
				List<WorkplaceSpecificDateItem> lstOld = workplaceRepo.getWorkplaceSpecByDate(workplaceId, dateInt);
				List<WorkplaceSpecificDateItem> lstAdd = lstOld;
				List<Integer> lstAddNew = new ArrayList<Integer>();
				//find item not exist in db
				if(lstAdd.size()==0){
					//lst = lstTimeItemId
					lstAddNew = lstTimeItemId;
				}else{
					List<WorkplaceSpecificDateItem>	a1 = new ArrayList<>();
					
					for (Integer timeItemId : lstTimeItemId) {
						for (WorkplaceSpecificDateItem itemAdd : lstAdd) {
							BigDecimal a2 = BigDecimal.valueOf(timeItemId);
							if(a2.equals(itemAdd.getSpecificDateItemNo().v())){
								a1.add(itemAdd);
							}
						}
						if(a1.isEmpty()){
							lstAddNew.add(timeItemId);
						}
						a1 = new ArrayList<>();
					}
				}
				List<WorkplaceSpecificDateItem> listwpSpec = new ArrayList<>();
				for (Integer addNew : lstAddNew) {
					listwpSpec.add(WorkplaceSpecificDateItem.createFromJavaType(workplaceId,dateBigDecimal, BigDecimal.valueOf(addNew),""));
				}
				//add item new in db
				workplaceRepo.InsertWpSpecDate(listwpSpec);
			}else{
				//既に設定されている内容をクリアし、今回選択したものだけを設定する - add new: xoa het caus cu, them moi
				//delete setting old workplace
				
				workplaceRepo.deleteWorkplaceSpec(workplaceId, dateInt);
				//add new
				List<WorkplaceSpecificDateItem> lstWorkplaceSpecificDate = new ArrayList<>();
				
				for (Integer timeItemId : lstTimeItemId) {
					lstWorkplaceSpecificDate.add(WorkplaceSpecificDateItem.createFromJavaType(workplaceId,dateBigDecimal,BigDecimal.valueOf(timeItemId),""));
				}
				workplaceRepo.InsertWpSpecDate(lstWorkplaceSpecificDate);
			}
			date = date.addDays(1);
			dateStr = String.format("%04d%02d%02d", date.year(), date.month(),date.day());
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
	private void UpdatebyDayforCompany(int strDate, int endDate, List<Integer> dayofWeek, List<Integer> lstTimeItemId ,int setUpdate){
		String companyId = AppContexts.user().companyId();
		GeneralDate sDate = GeneralDate.fromString(String.valueOf(strDate), "yyyyMMdd");
		GeneralDate eDate = GeneralDate.fromString(String.valueOf(endDate), "yyyyMMdd");
		GeneralDate date = sDate;
		String eDateStr = String.format("%04d%02d%02d", eDate.year(), eDate.month(),eDate.day());
		String dateStr = String.format("%04d%02d%02d", date.year(), date.month(),date.day());
		//check slected public holiday
		List<PublicHoliday> lstHoliday = checkSelectedHoliday(dayofWeek, strDate, endDate);
		while(dateStr.compareTo(eDateStr)<=0){
			if(!checkSet(lstHoliday,date,dayofWeek)){//not setting
				date = date.addDays(1);
				dateStr = String.format("%04d%02d%02d", date.year(), date.month(),date.day());
				continue;
			}
			//setting
			int dateInt = Integer.valueOf(dateStr);
			BigDecimal dateBigDecimal = BigDecimal.valueOf(dateInt);
			if(setUpdate==1){
				//既に設定されている内容は据え置き、追加で設定する - complement
				//list item da co san trong db
				List<CompanySpecificDateItem> lstOld = companyRepo.getComSpecByDate(companyId, dateInt);
				List<CompanySpecificDateItem> lstAdd = lstOld;
				List<Integer> lstAddNew = new ArrayList<Integer>();
				//find item not exist in db
				if(lstAdd.size()==0){
					lstAddNew = lstTimeItemId;
				}else{
					List<CompanySpecificDateItem>	a1 = new ArrayList<>();
					for (Integer timeItemId : lstTimeItemId) {
						for (CompanySpecificDateItem itemAdd : lstAdd) {
							BigDecimal a2 = BigDecimal.valueOf(timeItemId);
							if(a2.equals(itemAdd.getSpecificDateItemNo().v())){
								a1.add(itemAdd);
							}
						}
						if(a1.isEmpty()){
							lstAddNew.add(timeItemId);
						}
						a1 = new ArrayList<>();
					}
				}
				//get by list aa
				List<CompanySpecificDateItem> listwpSpec = new ArrayList<>();
				for (Integer addNew : lstAddNew) {
					listwpSpec.add(CompanySpecificDateItem.createFromJavaType(companyId,dateBigDecimal, BigDecimal.valueOf(addNew),""));
				}
				//add item new in db
				companyRepo.addListComSpecDate(listwpSpec);
			}else{
				//既に設定されている内容をクリアし、今回選択したものだけを設定する - add new
				//delete setting old workplace
				companyRepo.deleteComSpecByDate(companyId, dateInt);
				//add new
				List<CompanySpecificDateItem> lstComSpecificDate = new ArrayList<>();
				
				for (Integer timeItemId : lstTimeItemId) {
					lstComSpecificDate.add(CompanySpecificDateItem.createFromJavaType(companyId, dateBigDecimal, BigDecimal.valueOf(timeItemId),""));
				}
				companyRepo.addListComSpecDate(lstComSpecificDate);
			}
			date = date.addDays(1);
			dateStr = String.format("%04d%02d%02d", date.year(), date.month(),date.day());
		}
	}

}
