/**
 * 
 */
package nts.uk.screen.at.app.ksu001.eventinformationandpersonal;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.RequiredArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.CompanyEvent;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.CompanyEventRepository;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.WorkplaceEvent;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.WorkplaceEventRepository;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday.PublicHoliday;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday.PublicHolidayRepository;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.specificdate.item.SpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.specificdate.repository.SpecificDateItemRepository;
import nts.uk.ctx.at.schedule.dom.shift.management.DateInformation;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.company.CompanySpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.company.CompanySpecificDateRepository;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.workplace.WorkplaceSpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.workplace.WorkplaceSpecificDateRepository;
import nts.uk.ctx.at.schedule.dom.workschedule.displaysetting.DisplayControlPersonalCondition;
import nts.uk.ctx.at.schedule.dom.workschedule.displaysetting.DisplayControlPersonalConditionRepo;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author laitv <<ScreenQuery>> 期間中のイベント情報と個人条件を取得する
 * 
 */
@Stateless
public class EventInfoAndPersonalConditionsPeriod {
	
	@Inject
	private WorkplaceSpecificDateRepository workplaceSpecificDateRepo;
	@Inject
	private CompanySpecificDateRepository companySpecificDateRepo;
	@Inject
	private WorkplaceEventRepository workplaceEventRepo;
	@Inject
	private CompanyEventRepository companyEventRepo;
	@Inject
	private PublicHolidayRepository publicHolidayRepo;
	@Inject
	private SpecificDateItemRepository specificDateItemRepo;
	@Inject
	private DisplayControlPersonalConditionRepo displayControlPerCondRepo;
	

	public DataSpecDateAndHolidayDto get(EventInfoAndPerCondPeriodParam param) {
		
		// step 1
		// ・List<Temporary「年月日情報」>
		List<DateInformation> listDateInfo = new ArrayList<DateInformation>();
		
		for (GeneralDate date = param.startDate; date.beforeOrEquals(param.endDate); date = date.addDays(1)){
			RequireImpl require = new RequireImpl(workplaceSpecificDateRepo, companySpecificDateRepo,
					workplaceEventRepo, companyEventRepo, publicHolidayRepo, specificDateItemRepo);
			TargetOrgIdenInfor targetOrgIdenInfor = new  TargetOrgIdenInfor(param.workplaceGroupId == null ? TargetOrganizationUnit.WORKPLACE : TargetOrganizationUnit.WORKPLACE_GROUP, param.workplaceId, param.workplaceGroupId);
			DateInformation dateInformation = null;
			dateInformation = DateInformation.create(require, param.startDate, targetOrgIdenInfor);
			listDateInfo.add(dateInformation);
		}
		
		// step2
		Optional<DisplayControlPersonalCondition> displayControlPerCond =  displayControlPerCondRepo.get(AppContexts.user().companyId());
		if (!displayControlPerCond.isPresent()) {
			// step 3
			return new DataSpecDateAndHolidayDto(listDateInfo, new ArrayList<>(), Optional.empty());
		}
		
		// step 4
		return null;
		
		
		
	}

	@RequiredArgsConstructor
	private static class RequireImpl implements DateInformation.Require {
		
		private final WorkplaceSpecificDateRepository workplaceSpecificDateRepo;
		
		private final CompanySpecificDateRepository companySpecificDateRepo;
		
		private final WorkplaceEventRepository workplaceEventRepo;
		
		private final CompanyEventRepository companyEventRepo;
		
		private final PublicHolidayRepository publicHolidayRepo;
		
		private final SpecificDateItemRepository specificDateItemRepo;
		
		@Override
		public List<WorkplaceSpecificDateItem> getWorkplaceSpecByDate(String workplaceId, GeneralDate specificDate) {
			List<WorkplaceSpecificDateItem> data = workplaceSpecificDateRepo.getWorkplaceSpecByDate(workplaceId, specificDate);
			return data;
		}

		@Override
		public List<CompanySpecificDateItem> getComSpecByDate(GeneralDate specificDate) {
			List<CompanySpecificDateItem> data = companySpecificDateRepo.getComSpecByDate(AppContexts.user().companyId(), specificDate);
			return data;
		}

		@Override
		public Optional<WorkplaceEvent> findByPK(String workplaceId, GeneralDate date) {
			Optional<WorkplaceEvent> data = workplaceEventRepo.findByPK(workplaceId, date);
			return data;
		}

		@Override
		public Optional<CompanyEvent> findCompanyEventByPK(GeneralDate date) {
			Optional<CompanyEvent> data = companyEventRepo.findByPK(AppContexts.user().companyId(), date);
			return data;
		}

		@Override
		public Optional<PublicHoliday> getHolidaysByDate(GeneralDate date) {
			Optional<PublicHoliday> data = publicHolidayRepo.getHolidaysByDate(AppContexts.user().companyId(), date);
			return data;
		}

		@Override
		public List<SpecificDateItem> getSpecifiDateByListCode(List<Integer> lstSpecificDateItem) {
			List<SpecificDateItem> data = specificDateItemRepo.getSpecifiDateByListCode(AppContexts.user().companyId(), lstSpecificDateItem);
			return data;
		}
	}
}
