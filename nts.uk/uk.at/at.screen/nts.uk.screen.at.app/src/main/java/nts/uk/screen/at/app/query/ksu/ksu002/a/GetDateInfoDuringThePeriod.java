package nts.uk.screen.at.app.query.ksu.ksu002.a;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.specificdate.item.SpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.specificdate.primitives.SpecificDateItemNo;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.specificdate.repository.SpecificDateItemRepository;
import nts.uk.ctx.at.schedule.dom.shift.management.DateInformation;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.company.CompanySpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.company.CompanySpecificDateRepository;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.workplace.WorkplaceSpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.workplace.WorkplaceSpecificDateRepository;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistory;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryItem;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryItemRepository;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryRepository;
import nts.uk.screen.at.app.query.ksu.ksu002.a.input.GetDateInfoDuringThePeriodInput;
import nts.uk.shr.com.context.AppContexts;

/**
 * 期間中の年月日情報を取得する
 * 
 * @author chungnt
 *
 */
@Stateless
public class GetDateInfoDuringThePeriod {

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
	private AffWorkplaceHistoryRepository affWorkplaceHistoryRepo;

	@Inject
	private AffWorkplaceHistoryItemRepository affWorkplaceHistoryItemRepo;

	// private final DateHistoryCache<FooHistoryItem> historyCache = new
	// DateHistoryCache<>();

	public List<DateInformation> get(GetDateInfoDuringThePeriodInput param) {

		DatePeriod period = new DatePeriod(param.startDate, param.endDate);
		
		List<DateInformation> listDateInfo = new ArrayList<DateInformation>();

		List<GeneralDate> dates = period.datesBetween();
		
		RequireImpl require = new RequireImpl(workplaceSpecificDateRepo, companySpecificDateRepo, workplaceEventRepo,
				companyEventRepo, publicHolidayRepo, specificDateItemRepo);
		
		for (GeneralDate generalDate : dates) {
			List<AffWorkplaceHistory> workplaceHistories = affWorkplaceHistoryRepo.findByEmployees(param.sids, generalDate);
			List<String> histIds = new ArrayList<>();
			
			for (AffWorkplaceHistory affWorkplaceHistory : workplaceHistories) {
				for (String histId : affWorkplaceHistory.getHistoryIds()) {
					histIds.add(histId);
				}
			}

			if (histIds.isEmpty()) {
				continue;
			}

			List<AffWorkplaceHistoryItem> items = affWorkplaceHistoryItemRepo.findByHistIds(histIds);

			List<String> workplaceIds = items.stream().map(m -> m.getWorkplaceId()).collect(Collectors.toList());
			
			if (workplaceIds.isEmpty()){
				continue;
			}
			
			List<DateInformation> list = workplaceIds.stream().map(s -> {
				TargetOrgIdenInfor idenInfor = TargetOrgIdenInfor.creatIdentifiWorkplace(s);
				DateInformation dateInformation = DateInformation.create(require, generalDate,
						idenInfor);
				return dateInformation;
			}).collect(Collectors.toList());
			
			listDateInfo.addAll(list);
		}

		return listDateInfo;
	}

	@AllArgsConstructor
	private static class RequireImpl implements DateInformation.Require {

		private WorkplaceSpecificDateRepository workplaceSpecificDateRepo;
		private CompanySpecificDateRepository companySpecificDateRepo;
		private WorkplaceEventRepository workplaceEventRepo;
		private CompanyEventRepository companyEventRepo;
		private PublicHolidayRepository publicHolidayRepo;
		private SpecificDateItemRepository specificDateItemRepo;

		@Override
		public List<WorkplaceSpecificDateItem> getWorkplaceSpecByDate(String workplaceId, GeneralDate specificDate) {
			List<WorkplaceSpecificDateItem> data = workplaceSpecificDateRepo.getWorkplaceSpecByDate(workplaceId,
					specificDate);
			return data;
		}

		@Override
		public List<CompanySpecificDateItem> getComSpecByDate(GeneralDate specificDate) {
			List<CompanySpecificDateItem> data = companySpecificDateRepo
					.getComSpecByDate(AppContexts.user().companyId(), specificDate);
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
		public boolean getHolidaysByDate(GeneralDate date) {
			Optional<PublicHoliday> data = publicHolidayRepo.getHolidaysByDate(AppContexts.user().companyId(), date);
			return data.isPresent();
		}

		@Override
		public List<SpecificDateItem> getSpecifiDateByListCode(List<SpecificDateItemNo> lstSpecificDateItemNo) {
			if (lstSpecificDateItemNo.isEmpty()) {
				return new ArrayList<>();
			}

			List<Integer> _lstSpecificDateItemNo = lstSpecificDateItemNo.stream().map(mapper -> mapper.v())
					.collect(Collectors.toList());
			List<SpecificDateItem> data = specificDateItemRepo.getSpecifiDateByListCode(AppContexts.user().companyId(),
					_lstSpecificDateItemNo);
			return data;
		}
	}
}
