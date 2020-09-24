package nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.Getter;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.adapter.employment.BsEmploymentHistoryImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.EmploymentHistShareImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyAdapter;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyDto;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.param.AbsRecMngInPeriodRefactParamInput;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.param.CompenLeaveAggrResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimAbsMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecAbasMngRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.DigestionAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemainRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutManagementData;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutManagementDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutSubofHDManaRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutSubofHDManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SubstitutionOfHDManaDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SubstitutionOfHDManagementData;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacationRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.EmpSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.EmpSubstVacationRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;

@Stateless
public class NumberCompensatoryLeavePeriodProcess {

	@Inject
	private SubstitutionOfHDManaDataRepository substitutionOfHDManaDataRepository;

	@Inject
	private PayoutManagementDataRepository payoutManagementDataRepository;

	@Inject
	private InterimRemainRepository interimRemainRepository;

	@Inject
	private InterimRecAbasMngRepository interimRecAbasMngRepository;

	@Inject
	private ShareEmploymentAdapter shareEmploymentAdapter;

	@Inject
	private EmpSubstVacationRepository empSubstVacationRepository;

	@Inject
	private ComSubstVacationRepository comSubstVacationRepository;

	@Inject
	private ClosureEmploymentRepository closureEmploymentRepo;

	@Inject
	private ClosureRepository closureRepo;

	@Inject
	private CompanyAdapter companyAdapter;
	
	@Inject
	private PayoutSubofHDManaRepository payoutSubofHDManaRepository;

	public CompenLeaveAggrResult process(AbsRecMngInPeriodRefactParamInput inputParam) {
		RequireImpl impl = new RequireImplBuilder(substitutionOfHDManaDataRepository, payoutManagementDataRepository,
				interimRemainRepository, interimRecAbasMngRepository, shareEmploymentAdapter)
						.empSubstVacationRepository(empSubstVacationRepository)
						.comSubstVacationRepository(comSubstVacationRepository).companyAdapter(companyAdapter)
						.closureEmploymentRepo(closureEmploymentRepo).closureRepo(closureRepo)
						.payoutSubofHDManaRepository(payoutSubofHDManaRepository).build();

		return NumberCompensatoryLeavePeriodQuery.process(impl, inputParam);

	}

	public class RequireImpl implements NumberCompensatoryLeavePeriodQuery.Require {

		private final SubstitutionOfHDManaDataRepository substitutionOfHDManaDataRepository;

		private final PayoutManagementDataRepository payoutManagementDataRepository;

		private final InterimRemainRepository interimRemainRepository;

		private final InterimRecAbasMngRepository interimRecAbasMngRepository;

		private final ShareEmploymentAdapter shareEmploymentAdapter;

		private final EmpSubstVacationRepository empSubstVacationRepository;

		private final ComSubstVacationRepository comSubstVacationRepository;


		private final CompanyAdapter companyAdapter;
		
		private final PayoutSubofHDManaRepository payoutSubofHDManaRepository;

		private final ClosureEmploymentRepository closureEmploymentRepo;

		private final ClosureRepository closureRepo;

		public RequireImpl(RequireImplBuilder builder) {
			this.substitutionOfHDManaDataRepository = builder.getSubstitutionOfHDManaDataRepository();
			this.payoutManagementDataRepository = builder.getPayoutManagementDataRepository();
			this.interimRemainRepository = builder.getInterimRemainRepository();
			this.interimRecAbasMngRepository = builder.getInterimRecAbasMngRepository();
			this.shareEmploymentAdapter = builder.getShareEmploymentAdapter();
			this.empSubstVacationRepository = builder.getEmpSubstVacationRepository();
			this.comSubstVacationRepository = builder.getComSubstVacationRepository();
			this.companyAdapter = builder.getCompanyAdapter();
			this.closureEmploymentRepo = builder.getClosureEmploymentRepo();
			this.closureRepo = builder.getClosureRepo();
			this.payoutSubofHDManaRepository = builder.getPayoutSubofHDManaRepository();

		}

		@Override
		public List<SubstitutionOfHDManagementData> getByYmdUnOffset(String cid, String sid, GeneralDate ymd,
				double unOffseDays) {
			return substitutionOfHDManaDataRepository.getByYmdUnOffset(cid, sid, ymd, unOffseDays);
		}

		@Override
		public List<PayoutManagementData> getByUnUseState(String cid, String sid, GeneralDate ymd, double unUse,
				DigestionAtr state) {
			return payoutManagementDataRepository.getByUnUseState(cid, sid, ymd, unUse, state);
		}

		@Override
		public List<InterimRemain> getRemainBySidPriod(String employeeId, DatePeriod dateData, RemainType remainType) {
			return interimRemainRepository.getRemainBySidPriod(employeeId, dateData, remainType);
		}

		@Override
		public List<InterimAbsMng> getAbsBySidDatePeriod(String sid, DatePeriod period) {
			return interimRecAbasMngRepository.getAbsBySidDatePeriod(sid, period);
		}

		@Override
		public List<InterimRecMng> getRecBySidDatePeriod(String sid, DatePeriod period) {
			return interimRecAbasMngRepository.getRecBySidDatePeriod(sid, period);
		}

		@Override
		public Optional<BsEmploymentHistoryImport> findEmploymentHistory(String companyId, String employeeId,
				GeneralDate baseDate) {
			return shareEmploymentAdapter.findEmploymentHistory(companyId, employeeId, baseDate);
		}

		@Override
		public Optional<EmpSubstVacation> findEmpById(String companyId, String contractTypeCode) {
			return empSubstVacationRepository.findById(companyId, contractTypeCode);
		}

		@Override
		public Optional<ComSubstVacation> findComById(String companyId) {
			return comSubstVacationRepository.findById(companyId);
		}

		@Override
		public CompanyDto getFirstMonth(String companyId) {
			return companyAdapter.getFirstMonth(companyId);
		}

		@Override
		public List<EmploymentHistShareImport> findByEmployeeIdOrderByStartDate(String employeeId) {
			return shareEmploymentAdapter.findByEmployeeIdOrderByStartDate(employeeId);
		}

		@Override
		public List<PayoutSubofHDManagement> getBySubId(String sid, GeneralDate digestDate) {
			return payoutSubofHDManaRepository.getBySubId(sid, digestDate);
		}

		@Override
		public List<PayoutSubofHDManagement> getByPayoutId(String sid, GeneralDate occDate) {
			return payoutSubofHDManaRepository.getByPayoutId(sid, occDate);
		}

		@Override
		public Optional<BsEmploymentHistoryImport> employmentHistory(CacheCarrier cacheCarrier, String companyId,
				String employeeId, GeneralDate baseDate) {
			return shareEmploymentAdapter.findEmploymentHistoryRequire(cacheCarrier, companyId, employeeId, baseDate);
		}

		@Override
		public Optional<ClosureEmployment> employmentClosure(String companyID, String employmentCD) {
			return closureEmploymentRepo.findByEmploymentCD(companyID, employmentCD);
		}

		@Override
		public Optional<Closure> closure(String companyId, int closureId) {
			return closureRepo.findById(companyId, closureId);
		}

	}

	@Getter
	public class RequireImplBuilder {

		private SubstitutionOfHDManaDataRepository substitutionOfHDManaDataRepository;

		private PayoutManagementDataRepository payoutManagementDataRepository;

		private InterimRemainRepository interimRemainRepository;

		private InterimRecAbasMngRepository interimRecAbasMngRepository;

		private ShareEmploymentAdapter shareEmploymentAdapter;

		private EmpSubstVacationRepository empSubstVacationRepository;

		private ComSubstVacationRepository comSubstVacationRepository;


		private CompanyAdapter companyAdapter;
		
		private PayoutSubofHDManaRepository payoutSubofHDManaRepository;

		private ClosureEmploymentRepository closureEmploymentRepo;

		private ClosureRepository closureRepo;

		public RequireImplBuilder(SubstitutionOfHDManaDataRepository substitutionOfHDManaDataRepository,
				PayoutManagementDataRepository payoutManagementDataRepository,
				InterimRemainRepository interimRemainRepository,
				InterimRecAbasMngRepository interimRecAbasMngRepository,
				ShareEmploymentAdapter shareEmploymentAdapter) {
			this.substitutionOfHDManaDataRepository = substitutionOfHDManaDataRepository;
			this.payoutManagementDataRepository = payoutManagementDataRepository;
			this.interimRemainRepository = interimRemainRepository;
			this.interimRecAbasMngRepository = interimRecAbasMngRepository;
			this.shareEmploymentAdapter = shareEmploymentAdapter;
		}

		public RequireImplBuilder empSubstVacationRepository(EmpSubstVacationRepository empSubstVacationRepository) {
			this.empSubstVacationRepository = empSubstVacationRepository;
			return this;
		}

		public RequireImplBuilder comSubstVacationRepository(ComSubstVacationRepository comSubstVacationRepository) {
			this.comSubstVacationRepository = comSubstVacationRepository;
			return this;
		}


		public RequireImplBuilder companyAdapter(CompanyAdapter companyAdapter) {
			this.companyAdapter = companyAdapter;
			return this;
		}
		
		public RequireImplBuilder payoutSubofHDManaRepository(PayoutSubofHDManaRepository payoutSubofHDManaRepository) {
			this.payoutSubofHDManaRepository = payoutSubofHDManaRepository;
			return this;
		}

		public RequireImplBuilder closureEmploymentRepo(ClosureEmploymentRepository closureEmploymentRepo) {
			this.closureEmploymentRepo = closureEmploymentRepo;
			return this;
		}

		public RequireImplBuilder closureRepo(ClosureRepository closureRepo) {
			this.closureRepo = closureRepo;
			return this;
		}

		public RequireImpl build() {
			return new RequireImpl(this);
		}
	}
}
