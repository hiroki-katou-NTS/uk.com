/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.service.workrule.closure;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.adapter.employment.BsEmploymentHistoryImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.CurrentMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

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
	
	/** The closure service. */
	@Inject
	private ClosureService closureService;
	
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
		return this.closureService.getClosurePeriod(
				closure.get().getClosureId().value, currentMonth.getProcessingYm());
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
}
