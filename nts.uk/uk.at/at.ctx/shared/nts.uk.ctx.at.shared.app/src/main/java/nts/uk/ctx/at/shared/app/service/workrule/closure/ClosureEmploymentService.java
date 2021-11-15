/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.service.workrule.closure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.adapter.employment.BsEmploymentHistoryImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.CurrentMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.context.AppContexts;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * The Class ClosureEmploymentService.
 */
@Stateless
public class ClosureEmploymentService {
	
	/** The employment adapter. */
	@Inject
	private ShareEmploymentAdapter employmentAdapter;
	
	/** The closure employment repository. */
	@Inject
	private ClosureEmploymentRepository closureEmploymentRepository;
	
	/** The closure repository. */
	@Inject
	private ClosureRepository closureRepository;
	
	/**
	 * Find closure period.
	 *
	 * @param employeeId the employee id
	 * @param baseDate the base date
	 * @return the date period
	 */
	// 社員に対応する締め期間を取得する.
	public DatePeriod findClosurePeriod(String employeeId, GeneralDate baseDate) {
		// 社員に対応する処理締めを取得する.
		Optional<Closure> closure = this.findClosureByEmployee(employeeId, baseDate);
		if(!closure.isPresent()) {
			return null;
		}
		CurrentMonth currentMonth = closure.get().getClosureMonth();
		
		// 当月の期間を算出する.
		return ClosureService.getClosurePeriod(
				closure.get().getClosureId().value, currentMonth.getProcessingYm(), closure);
	}
	
	/**
	 * Find employment closure.
	 *
	 * @param employeeId the employee id
	 * @param baseDate the base date
	 * @return the optional
	 */
	//社員に対応する処理締めを取得する
	public Optional<Closure> findClosureByEmployee(String employeeId, GeneralDate baseDate) {
		String companyId = AppContexts.user().companyId();
		
		// Find Employment History by employeeId and base date.
		Optional<BsEmploymentHistoryImport> empHistOpt = this.employmentAdapter
				.findEmploymentHistory(companyId, employeeId, baseDate);
		
		if (!empHistOpt.isPresent()) {
			return Optional.empty();
		}
		
		// Find closure employment by emp code.
		Optional<ClosureEmployment> closureEmpOpt = this.closureEmploymentRepository
				.findByEmploymentCD(companyId, empHistOpt.get().getEmploymentCode());
		
		if (!closureEmpOpt.isPresent()) {
			return Optional.empty();
		}
		
		// Find closure.
		return this.closureRepository.findById(companyId, closureEmpOpt.get().getClosureId());
	}
	
	public YearMonth getCurentMonth() {
		String employee = AppContexts.user().employeeId();
		CacheCarrier cacheCarrier = new CacheCarrier();
		Closure Closure = ClosureService.getClosureDataByEmployee(createRequireM3(),
				cacheCarrier, employee, GeneralDate.today());
		if(Closure == null) {
			throw new BusinessException("Msg_1134");
		}
		return Closure.getClosureMonth().getProcessingYm();
	}

	/**
	 * Find employment closure.
	 *
	 * @param employeeIds the list employee id
	 * @param baseDate the base date
	 * @return the optional
	 */
	//社員に対応する処理締めを取得する
	public Map<String, Closure> findClosureByEmployee(List<String> employeeIds, GeneralDate baseDate) {
		String companyId = AppContexts.user().companyId();
		Map<String, Closure> results = new HashMap<>();

		// Find Employment History by employeeId and base date.
		Map<String, BsEmploymentHistoryImport> empHistAll = this.employmentAdapter
				.findEmpHistoryVer2(companyId, employeeIds, baseDate);

		List<String> employmentCDs = empHistAll.entrySet().stream().map(x -> x.getValue().getEmploymentCode()).distinct()
				.collect(Collectors.toList());

		// Find closure employment by emp code.
		Map<String, ClosureEmployment> employmentAll = this.closureEmploymentRepository.findListEmployment(companyId, employmentCDs)
				.stream().collect(Collectors.toMap(ClosureEmployment::getEmploymentCD, x -> x));

		List<Integer> closureIds = employmentAll.entrySet().stream().map(x -> x.getValue().getClosureId()).distinct()
				.collect(Collectors.toList());

		// Find closure.
		Map<Integer, Closure> closureALl = this.closureRepository.findByListId(companyId, closureIds)
				.stream().collect(Collectors.toMap(x -> x.getClosureId().value, x -> x));

		for (String employeeId : employeeIds) {
			if (!empHistAll.containsKey(employeeId)) {
				continue;
			}
			String employmentCode = empHistAll.get(employeeId).getEmploymentCode();

			if (!employmentAll.containsKey(employmentCode)) {
				continue;
			}
			Integer closureId = employmentAll.get(employmentCode).getClosureId();

			if (!closureALl.containsKey(closureId)) {
				continue;
			}

			results.put(employeeId, closureALl.get(closureId));
		}

		return results;
	}


	@Inject
	private ClosureEmploymentRepository closureEmpRepo;
	
	@Inject
	private ShareEmploymentAdapter shrEmpAdapter;
	
	private ClosureService.RequireM3 createRequireM3() {
		return new ClosureService.RequireM3() {
			
			@Override
			public Optional<Closure> closure(String companyId, int closureId) {
				return closureRepository.findById(companyId, closureId);
			}
			
			@Override
			public Optional<ClosureEmployment> employmentClosure(String companyID, String employmentCD) {
				// TODO Auto-generated method stub
				return closureEmpRepo.findByEmploymentCD(companyID, employmentCD);
			}

			@Override
			public Optional<BsEmploymentHistoryImport> employmentHistory(CacheCarrier cacheCarrier, String companyId,
					String employeeId, GeneralDate baseDate) {
				return shrEmpAdapter.findEmploymentHistoryRequire(cacheCarrier, companyId, employeeId, baseDate);
			}

			@Override
			public List<ClosureEmployment> employmentClosureClones(String companyID, List<String> employmentCD) {
				return closureEmpRepo.findListEmployment(companyID, employmentCD);
			}

			@Override
			public List<Closure> closureClones(String companyId, List<Integer> closureId) {
				return closureRepository.findByListId(companyId, closureId);
			}

			@Override
			public Map<String, BsEmploymentHistoryImport> employmentHistoryClones(String companyId, List<String> employeeId,
					GeneralDate baseDate) {
				return shrEmpAdapter.findEmpHistoryVer2(companyId, employeeId, baseDate);
			}
		};
	}
}
