package nts.uk.ctx.at.schedule.dom.shift.management;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.DayOfWeek;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.CompanyEvent;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.EventName;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.WorkplaceEvent;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.specificdate.item.SpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.specificdate.primitives.SpecificDateItemNo;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.specificdate.primitives.SpecificName;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.company.CompanySpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.workplace.WorkplaceSpecificDateItem;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;

/**
 * 年月日情報 UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務予定.シフト管理
 * 
 * @author HieuLt
 *
 */
@Getter
@RequiredArgsConstructor
public class DateInformation {
	/** 年月日 **/
	private final GeneralDate ymd;
	/** 曜日 **/
	private final DayOfWeek dayOfWeek;
	/** 祝日であるか **/
	private final boolean isHoliday;
	/** 特定日であるか **/
	private final boolean isSpecificDay;
	/** 職場行事名称 **/
	private final Optional<EventName> optWorkplaceEventName;
	/** 会社行事名称 **/
	private final Optional<EventName> optCompanyEventName;
	/** 職場の特定日名称リスト **/
	private final List<SpecificName> listSpecDayNameWorkplace;
	/** 会社の特定日名称リスト **/
	private final List<SpecificName> listSpecDayNameCompany;

	/**
	 * [C-1] 作成する
	 * 
	 * @param require
	 * @param ymd
	 * @param targetOrgIdenInfor
	 * @return
	 */
	public static DateInformation create(Require require, GeneralDate ymd, TargetOrgIdenInfor targetOrgIdenInfor) {
		boolean isSpecificDay = false;
		Optional<EventName> workplaceEventName = Optional.empty();
		Optional<EventName> optCompanyEventName = Optional.empty();
		List<SpecificName> lstSpecDayNameWorkplace = new ArrayList<>();
		List<SpecificName> lstSpecDayNameCom = new ArrayList<>();
		boolean existPublicHoliday = require.getHolidaysByDate(ymd);

		// if 対象組織.単位 == 職場
		if (targetOrgIdenInfor.getUnit().value == TargetOrganizationUnit.WORKPLACE.value) {
			Optional<WorkplaceEvent> workplaceEvent = require.findByPK(targetOrgIdenInfor.getWorkplaceId().get(), ymd);
			if (workplaceEvent.isPresent()) {
				/*
				 * if $職場行事.isPresent
				 * 
				 * @職場行事名称 = $職場行事.行事名称 $職場特定日設定 =
				 * require.職場の特定日設定を取得する(対象組織.職場ID, 年月日)
				 */

				workplaceEventName = Optional.of(workplaceEvent.get().getEventName());
			}
			List<WorkplaceSpecificDateItem> listWorkplaceSpecificDateItem = require
					.getWorkplaceSpecByDate(targetOrgIdenInfor.getWorkplaceId().get(), ymd);

			if (!listWorkplaceSpecificDateItem.isEmpty()) {
				/*
				 * @特定日であるか = true
				 * 
				 * @職場の特定日名称リスト = require.特定日項目リストを取得する( $職場特定日設定.特定日項目リスト) :
				 * map $.名称
				 */
				// @特定日であるか QA http://192.168.50.4:3000/issues/110662 - code
				isSpecificDay = true;
				List<SpecificDateItemNo> listSpecificDateItemNo = listWorkplaceSpecificDateItem.stream()
						.map(c -> c.getSpecificDateItemNo()).collect(Collectors.toList());
				List<SpecificDateItem> zlistSpecDayNameWorkplace = require
						.getSpecifiDateByListCode(listSpecificDateItemNo);
				lstSpecDayNameWorkplace = zlistSpecDayNameWorkplace.stream().map(c -> c.getSpecificName())
						.collect(Collectors.toList());
			}

		}

		Optional<CompanyEvent> optCompanyEvent = require.findCompanyEventByPK(ymd);
		if (optCompanyEvent.isPresent()) {
			/*
			 * if $会社行事.isPresent
			 * 
			 * @行事名称 = $会社行事.行事名称
			 */
			optCompanyEventName = Optional.of(optCompanyEvent.get().getEventName());
		}
		List<CompanySpecificDateItem> listCompanySpecificDateItem = require.getComSpecByDate(ymd);
		if (!listCompanySpecificDateItem.isEmpty()) {
			isSpecificDay = true;
			List<SpecificDateItemNo> lstSpecificDateItemNo = listCompanySpecificDateItem.stream()
					.map(c -> c.getSpecificDateItemNo()).collect(Collectors.toList());
			List<SpecificDateItem> listSpecDayNameCompany = require.getSpecifiDateByListCode(lstSpecificDateItemNo);
			lstSpecDayNameCom = listSpecDayNameCompany.stream().map(c -> c.getSpecificName())
					.collect(Collectors.toList());

		}

		return new DateInformation(ymd, ymd.dayOfWeekEnum(), existPublicHoliday, isSpecificDay, workplaceEventName,
				optCompanyEventName, lstSpecDayNameWorkplace, lstSpecDayNameCom);

	}

	public static interface Require {
		/**
		 * WorkplaceSpecificDateRepository [R-1] 職場の特定日設定を取得する
		 * 
		 * @param workplaceId
		 * @param specificDate
		 * @return
		 */
		List<WorkplaceSpecificDateItem> getWorkplaceSpecByDate(String workplaceId, GeneralDate specificDate);

		/**
		 * CompanySpecificDateRepository [R-2] 会社の特定日設定を取得する
		 * 
		 * @param companyId
		 * @param specificDate
		 * @return
		 */
		List<CompanySpecificDateItem> getComSpecByDate(GeneralDate specificDate);

		/**
		 * WorkplaceEventRepository [R-3] 職場行事を取得する
		 * 
		 * @param workplaceId
		 * @param date
		 * @return
		 */
		Optional<WorkplaceEvent> findByPK(String workplaceId, GeneralDate date);

		/**
		 * [R-4] 会社行事を取得する CompanyEventRepository
		 * 
		 * @param companyId
		 * @param date
		 * @return
		 */
		Optional<CompanyEvent> findCompanyEventByPK(GeneralDate date);

		/**
		 * [R-5] 祝日が存在するか PublicHolidayRepository 祝日Repository.exists(会社ID, 年月日)
		 * 
		 * @param companyID
		 * @param date
		 * @return
		 */
		boolean getHolidaysByDate(GeneralDate date);

		/**
		 * [R-6] 特定日項目リストを取得する SpecificDateItemRepository
		 * 
		 * @param companyId
		 * @param lstSpecificDateItem
		 * @return
		 */
		List<SpecificDateItem> getSpecifiDateByListCode(List<SpecificDateItemNo> lstSpecificDateItemNo);

	}

}
