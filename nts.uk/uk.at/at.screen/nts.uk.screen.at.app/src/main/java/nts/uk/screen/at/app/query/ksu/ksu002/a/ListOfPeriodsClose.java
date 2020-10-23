package nts.uk.screen.at.app.query.ksu.ksu002.a;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.ClosurePeriod;
import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.GetClosurePeriod;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employment.SharedSidPeriodDateEmploymentImport;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryItem;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryItemRepository;
import nts.uk.screen.at.app.query.ksu.ksu002.a.dto.PeriodsClose;
import nts.uk.screen.at.app.query.ksu.ksu002.a.dto.SystemDateDto;
import nts.uk.screen.at.app.query.ksu.ksu002.a.input.EmployeeInformationInput;
import nts.uk.screen.at.app.query.ksu.ksu002.a.input.ListOfPeriodsCloseInput;
import nts.uk.shr.com.context.AppContexts;

/**
 * 締めに応じる期間リストを取得する
 * UKDesign.UniversalK.就業.KSU_スケジュール.KSU002_個人スケジュール修正(個人別).A:メイン画面.メニュー別OCD.締めに応じる期間リストを取得する
 * @author chungnt
 *
 */

@Stateless
public class ListOfPeriodsClose {

	@Inject
	private ClosureRepository closureRepo;

	@Inject
	private ShareEmploymentAdapter shareEmploymentAdapter;

	@Inject
	private ClosureEmploymentRepository closureEmploymentRepo;
	
	@Inject
	private ClosureRepository closureRepository;
	
	@Inject
	private TheInitialDisplayDate theInitialDisplayDate;
	
	@Inject
	private EmployeeInformation employeeInformation;
	
	@Inject
	private AffWorkplaceHistoryItemRepository affWorkplaceHistoryItemRepo;
	
	public SystemDateDto get(ListOfPeriodsCloseInput input) {
		
		GetClosurePeriodRequireImpl require = new GetClosurePeriodRequireImpl(closureRepo, shareEmploymentAdapter, closureEmploymentRepo);
		CacheCarrier cacheCarrier = new CacheCarrier();
		String companyId = AppContexts.user().companyId();
		String sid = AppContexts.user().employeeId();
		
		SystemDateDto dto = new SystemDateDto();
		
		if (input.getYearMonth() <= 0) {
			dto.setYearMonth(this.theInitialDisplayDate.getInitialDisplayDate().getYearMonth());
		} else {
			dto.setYearMonth(input.getYearMonth());
		}
		
		List<PeriodsClose> closes = new ArrayList<>();
		
		List<ClosurePeriod> periods = GetClosurePeriod.fromYearMonth(require, cacheCarrier, sid, GeneralDate.today(), YearMonth.of(dto.getYearMonth()));
		
		List<Closure> closures = this.closureRepository.findByListId(companyId,
				periods.stream().map(m -> m.getClosureId().value).collect(Collectors.toList()));
		
		for (ClosurePeriod closurePeriod : periods) {
			PeriodsClose periodsClose = new PeriodsClose();
			
			DatePeriod period = closurePeriod.getAggrPeriods().get(0).getPeriod();
			
			periodsClose.setStartDate(period.start());
			periodsClose.setEndDate(period.end());
			
			List<AffWorkplaceHistoryItem> affWorkplaceHistoryItem = affWorkplaceHistoryItemRepo
					.getAffWrkplaHistItemByEmpIdAndDate(period.start(), sid);

			if (!affWorkplaceHistoryItem.isEmpty()) {
				periodsClose.setWorkplaceId(affWorkplaceHistoryItem.get(0).getWorkplaceId());
			}
			
			Optional<Closure> closure = closures.stream().filter(f -> f.getClosureId().value ==  closurePeriod.getClosureId().value).findFirst();
			
			periodsClose.setClosureName(closure.map(m -> m.getClosureHistories().get(0).getClosureName().v()).orElse(""));
			
			closes.add(periodsClose);
		}
		
		dto.setPeriodsClose(closes);
		
		if(dto.getPeriodsClose().isEmpty()){
			return dto;
		}
		
		EmployeeInformationInput inputEmployee = new EmployeeInformationInput(AppContexts.user().employeeId(), dto.getPeriodsClose().get(0).getEndDate());
		dto.setEmployeeInfo(this.employeeInformation.getEmployeeInfo(inputEmployee));
		
		return dto;
	}

	@AllArgsConstructor
	private class GetClosurePeriodRequireImpl implements GetClosurePeriod.RequireM2 {
		private ClosureRepository closureRepo;
		private ShareEmploymentAdapter shareEmploymentAdapter;
		private ClosureEmploymentRepository closureEmploymentRepo;

		@Override
		public List<Closure> closure(String companyId) {
			return this.closureRepo.findAll(companyId);
		}

		@Override
		public List<SharedSidPeriodDateEmploymentImport> employmentHistories(CacheCarrier cacheCarrier,
				List<String> sids, DatePeriod datePeriod) {
			return this.shareEmploymentAdapter.getEmpHistBySidAndPeriodRequire(cacheCarrier, sids, datePeriod);
		}

		@Override
		public List<ClosureEmployment> employmentClosure(String companyId, List<String> employmentCDs) {
			return this.closureEmploymentRepo.findListEmployment(companyId, employmentCDs);
		}

		@Override
		public Optional<Closure> closure(String companyId, int closureId) {
			return this.closureRepo.findById(companyId, closureId);
		}
	}
}
