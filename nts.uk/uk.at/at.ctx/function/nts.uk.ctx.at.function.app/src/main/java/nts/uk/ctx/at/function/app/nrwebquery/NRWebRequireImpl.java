package nts.uk.ctx.at.function.app.nrwebquery;

import java.util.List;
import java.util.Map;
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
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;

@Stateless
public class NRWebRequireImpl {

	@Inject
	private ShareEmploymentAdapter shareEmploymentAdapter;

	@Inject
	private ClosureRepository closureRepo;

	@Inject
	private ClosureEmploymentRepository closureEmploymentRepo;

	public RequireClosureService createClosureService() {
		return new RequireClosureService(shareEmploymentAdapter, closureRepo, closureEmploymentRepo);
	}

	@AllArgsConstructor
	public class RequireClosureService implements ClosureService.RequireM3 {

		private ShareEmploymentAdapter shareEmploymentAdapter;

		private ClosureRepository closureRepo;

		private ClosureEmploymentRepository closureEmploymentRepo;

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
			return shareEmploymentAdapter.findEmploymentHistoryRequire(cacheCarrier, companyId, employeeId, baseDate);
		}

		
		@Override
		public List<ClosureEmployment> employmentClosureClones(String companyID, List<String> employmentCD) {
			return closureEmploymentRepo.findListEmployment(companyID, employmentCD);
		}

		@Override
		public List<Closure> closureClones(String companyId, List<Integer> closureId) {
			return closureRepo.findByListId(companyId, closureId);
		}

		@Override
		public Map<String, BsEmploymentHistoryImport> employmentHistoryClones(String companyId,
				List<String> employeeId, GeneralDate baseDate) {
			return shareEmploymentAdapter.findEmpHistoryVer2(companyId, employeeId, baseDate);
		}

	}
}
