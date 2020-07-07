package nts.uk.ctx.at.schedule.dom.shift.management;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.DayOfWeek;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.CompanyEvent;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.EventName;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.WorkplaceEvent;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday.PublicHoliday;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.specificdate.item.SpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.specificdate.primitives.SpecificName;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.company.CompanySpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.workplace.WorkplaceSpecificDateItem;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;


/**
 * 年月日情報
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務予定.シフト管理
 * @author HieuLt
 *
 */
@Getter
@RequiredArgsConstructor
public class DateInformation {
	/** 年月日**/
	private final GeneralDate ymd;
	/** 曜日 **/
	private final  DayOfWeek dayOfWeek;
	/** 祝日であるか **/ 
	private final boolean isHoliday;
	/** 特定日であるか **/ 
	private final boolean isSpecificDay;
	/** 職場行事名称 **/
	private final Optional<EventName> OptWorkplaceEventName;
	/** 会社行事名称	**/
	private final Optional<EventName> companyEventName;
	/** 職場の特定日名称リスト**/
	private final List<SpecificName> listSpecDayNameWorkplace;
	/** 会社の特定日名称リスト**/
	private final List<SpecificName> listSpecDayNameCompany;
	
	/**
	 * 	[C-1] 作成する
	 * @param require
	 * @param ymd
	 * @param targetOrgIdenInfor
	 * @return
	 */
	public static DateInformation create(Require require, GeneralDate ymd,TargetOrgIdenInfor targetOrgIdenInfor ){
		return null;
	}
	
	public static interface Require{
		/**
		 * WorkplaceSpecificDateRepository
		 * [R-1] 職場の特定日設定を取得する
		 * @param workplaceId
		 * @param specificDate
		 * @return
		 */
		List<WorkplaceSpecificDateItem> getWorkplaceSpecByDate(String workplaceId, GeneralDate specificDate);
		
		/**
		 * CompanySpecificDateRepository
		 * [R-2] 会社の特定日設定を取得する
		 * @param companyId
		 * @param specificDate
		 * @return
		 */
		List<CompanySpecificDateItem> getComSpecByDate(String companyId, GeneralDate specificDate);
		/**
		 * WorkplaceEventRepository
		 * [R-3] 職場行事を取得する
		 * @param workplaceId
		 * @param date
		 * @return
		 */
		Optional<WorkplaceEvent> findByPK(String workplaceId, GeneralDate date);
		
		/**
		 * [R-4] 会社行事を取得する
		 * CompanyEventRepository
		 * @param companyId
		 * @param date
		 * @return
		 */
		Optional<CompanyEvent> findCompanyEventByPK(String companyId, GeneralDate date);
		
		/**
		 * [R-5] 祝日が存在するか
		 * PublicHolidayRepository
		 * @param companyID
		 * @param date
		 * @return
		 */
		Optional<PublicHoliday> getHolidaysByDate(String companyID, GeneralDate date);
		
		/**
		 * [R-6] 特定日項目リストを取得する
		 * SpecificDateItemRepository
		 * @param companyId
		 * @param lstSpecificDateItem
		 * @return
		 */
		List<SpecificDateItem> getSpecifiDateByListCode(String companyId, List<Integer> lstSpecificDateItem);
		
		
	}
	
}
