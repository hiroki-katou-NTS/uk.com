/**
 * 
 */
package nts.uk.screen.at.app.ksu001.getevent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.CompanyEvent;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.CompanyEventRepository;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.WorkplaceEvent;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.WorkplaceEventRepository;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday.PublicHoliday;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday.PublicHolidayRepository;
import nts.uk.ctx.at.schedule.dom.shift.management.DateInformation;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.CompanySpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.CompanySpecificDateRepository;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.SpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.SpecificDateItemNo;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.SpecificDateItemRepository;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.WorkplaceSpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.WorkplaceSpecificDateRepository;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.screen.at.app.ksu001.eventinformationandpersonal.DateInformationDto;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author laitv
 */
@Stateless
public class EventFinder {
	
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
	
	private static final String DATE_FORMAT = "yyyy/MM/dd";
	
	public List<DateInformationDto> getEvent(EventFinderParam param) {
		
		List<DateInformationDto> listDateInfo = new ArrayList<DateInformationDto>();
		
		RequireImpl require = new RequireImpl(workplaceSpecificDateRepo, companySpecificDateRepo, workplaceEventRepo,
				companyEventRepo, publicHolidayRepo, specificDateItemRepo);
		GeneralDate startDate = GeneralDate.fromString(param.startDate, DATE_FORMAT);
		GeneralDate endDate   = GeneralDate.fromString(param.endDate, DATE_FORMAT);
		 
		DatePeriod period = new DatePeriod(startDate, endDate);
		TargetOrgIdenInfor targetOrgIdenInfor = null;
		if (param.wkpGrId == null || param.wkpGrId == "") {
			targetOrgIdenInfor = new TargetOrgIdenInfor(TargetOrganizationUnit.WORKPLACE,
					Optional.of(param.wkpId),
					Optional.empty());
		}else{
			targetOrgIdenInfor = new TargetOrgIdenInfor(
					TargetOrganizationUnit.WORKPLACE_GROUP,
					Optional.empty(),
					Optional.of(param.wkpGrId));
		}
		
		param.targetOrgIdenInfor = targetOrgIdenInfor;
		
		period.datesBetween().stream().forEach(date -> {
			DateInformation dateInformation = null;
			dateInformation = DateInformation.create(require, date, param.targetOrgIdenInfor);
			listDateInfo.add(new DateInformationDto(dateInformation));
		});
		return listDateInfo;
	}
	
	@AllArgsConstructor
	private static class RequireImpl implements DateInformation.Require {

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

		@Override
		public Optional<WorkplaceSpecificDateItem> getWorkplaceSpecByDate(String workplaceId, GeneralDate specificDate) {
			return workplaceSpecificDateRepo.get(workplaceId, specificDate);
		}

		@Override
		public Optional<CompanySpecificDateItem> getComSpecByDate(GeneralDate specificDate) {
			return companySpecificDateRepo.get(AppContexts.user().companyId(), specificDate);
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
			return publicHolidayRepo.getHolidaysByDate(AppContexts.user().companyId(), date);
		}

		@Override
		public List<SpecificDateItem> getSpecifiDateByListCode(List<SpecificDateItemNo> lstSpecificDateItemNo) {
			if (lstSpecificDateItemNo.isEmpty()) {
				return new ArrayList<>();
			}
			List<SpecificDateItem> data = specificDateItemRepo.getSpecifiDateByListCode(AppContexts.user().companyId(),
					lstSpecificDateItemNo);
			return data;
		}
	}

}
