package nts.uk.screen.at.app.query.ksu.ksu002.a;

import java.time.YearMonth;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.adapter.employment.BsEmploymentHistoryImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.CurrentMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.screen.at.app.query.ksu.ksu002.a.dto.TheInitialDisplayDateDto;
import nts.uk.shr.com.context.AppContexts;

/**
 * 初期表示の年月を取得する
 * UKDesign.UniversalK.就業.KSU_スケジュール.KSU002_個人スケジュール修正(個人別).A:メイン画面.メニュー別OCD.初期表示の年月を取得する
 * 
 * @author chungnt
 *
 */

@Stateless
public class TheInitialDisplayDate {

	@Inject
	private ClosureEmploymentRepository closureEmploymentRepo;

	@Inject
	private ClosureRepository closureRepo;

	@Inject
	private ShareEmploymentAdapter employmentAdapter;

	public TheInitialDisplayDateDto getInitialDisplayDate() {
		CacheCarrier cacheCarrier = new CacheCarrier();
		TheInitialDisplayDateDto result = new TheInitialDisplayDateDto();
		
		TheInitialDisplayDateRequireImpl require = new TheInitialDisplayDateRequireImpl(closureEmploymentRepo,
				closureRepo, employmentAdapter);
		String employeeId = AppContexts.user().employeeId();
		GeneralDate baseDate = GeneralDate.today();

		Closure closure = ClosureService.getClosureDataByEmployee(require, cacheCarrier, employeeId, baseDate);
		
		CurrentMonth currentMonth = new CurrentMonth(YearMonth.now().getMonth().getValue());
		
		if (closure != null){
			currentMonth = closure.getClosureMonth();
		}
		
		result = new TheInitialDisplayDateDto(currentMonth.getProcessingYm().nextMonth().v());

		return result;
	}

	@AllArgsConstructor
	private class TheInitialDisplayDateRequireImpl implements ClosureService.RequireM3 {

		private ClosureEmploymentRepository closureEmploymentRepo;
		private ClosureRepository closureRepo;
		private ShareEmploymentAdapter employmentAdapter;

		@Override
		public Optional<ClosureEmployment> employmentClosure(String companyID, String employmentCD) {
			return closureEmploymentRepo.findByEmploymentCD(companyID, employmentCD);
		}

		@Override
		public Optional<Closure> closure(String companyId, int closureId) {
			return closureRepo.findById(companyId, closureId);
		}

		@Override
		public Optional<BsEmploymentHistoryImport> employmentHistory(CacheCarrier cacheCarrier, String companyId,
				String employeeId, GeneralDate baseDate) {
			return employmentAdapter.findEmploymentHistoryRequire(cacheCarrier, companyId, employeeId, baseDate);
		}

	}
}
