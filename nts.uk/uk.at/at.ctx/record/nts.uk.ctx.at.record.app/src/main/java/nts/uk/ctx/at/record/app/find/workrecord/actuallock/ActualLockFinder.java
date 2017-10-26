/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.workrecord.actuallock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDateTime;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLock;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLockHistory;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLockHistoryRepository;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLockRepository;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.LockStatus;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistory;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistoryRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.UseClassification;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class ActualLockFinder.
 */
@Stateless
public class ActualLockFinder {

	/** The actual lock repo. */
	@Inject
	private ActualLockRepository actualLockRepo;

	/** The closure repo. */
	@Inject
	private ClosureRepository closureRepo;

	/** The closure hist repo. */
	@Inject
	private ClosureHistoryRepository closureHistRepo;

	@Inject
	private ClosureService closureService;

	/** The actual lock hist repo. */
	@Inject
	private ActualLockHistoryRepository actualLockHistRepo;

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<ActualLockFinderDto> findAll() {
		// Get LoginUserContext
		LoginUserContext loginUserContext = AppContexts.user();
		// CompanyId
		String companyId = loginUserContext.companyId();

		// Target List
		List<ActualLockFinderDto> actualList = new ArrayList<>();

		// FindAll Closure
		List<Closure> closureList = this.closureRepo.findAll(companyId);

		List<Closure> filtedList = closureList.stream().filter(closure -> {
			Optional<ClosureHistory> closureHistOpt = this.closureHistRepo.findBySelectedYearMonth(companyId,
					closure.getClosureId(), closure.getClosureMonth().getProcessingYm().v());
			// Check exist
			if (!closureHistOpt.isPresent()) {
				return false;
			} else {
				return (closure.getUseClassification().value == UseClassification.UseClass_Use.value);
			}
		}).collect(Collectors.toList());

		// Check isEmpty
		if (CollectionUtil.isEmpty(filtedList)) {
			throw new BusinessException("Msg_183");
		} else {
			filtedList.stream().forEach(c -> {
				ActualLockFinderDto dto = new ActualLockFinderDto();
				// Find ClosureHistory
				Optional<ClosureHistory> closureHistOpt = this.closureHistRepo.findBySelectedYearMonth(companyId,
						c.getClosureId(), c.getClosureMonth().getProcessingYm().v());
				// Find ActualLock
				Optional<ActualLock> actualLockOpt = this.actualLockRepo.findById(companyId, c.getClosureId());

				// Get ClosurePeriod to get StartDate, EndDate
				DatePeriod datePeriod = closureService.getClosurePeriod(c.getClosureId(),
						c.getClosureMonth().getProcessingYm());
				// PeriodCurrentMonth period = new PeriodCurrentMonth(datePeriod.start(), datePeriod.end());
				// Set Dto
				dto.setClosureId(ClosureId.valueOf(c.getClosureId()));
				dto.setClosureName(closureHistOpt.get().getClosureName().v());
				// Set Period: StartDate, EndDate
				dto.setStartDate(datePeriod.start().toString());
				dto.setEndDate(datePeriod.end().toString());
				
				dto.setDailyLockState(
						actualLockOpt.isPresent() ? actualLockOpt.get().getDailyLockState() : LockStatus.UNLOCK);
				dto.setMonthlyLockState(
						actualLockOpt.isPresent() ? actualLockOpt.get().getMonthlyLockState() : LockStatus.UNLOCK);

				// Add ActualLockFinderDto to Target List
				actualList.add(dto);
			});
		}

		return actualList;
	}

	/**
	 * Find by id.
	 *
	 * @param closureId
	 *            the closure id
	 * @return the actual lock find dto
	 */
	public ActualLockFindDto findById(int closureId) {
		// Get LoginUserContext
		LoginUserContext loginUserContext = AppContexts.user();

		// CompanyId
		String companyId = loginUserContext.companyId();

		ActualLockFindDto dto = new ActualLockFindDto();

		// Find ActualLock By ClosureId
		Optional<ActualLock> actualLockOpt = this.actualLockRepo.findById(companyId, closureId);
		if (!actualLockOpt.isPresent()) {
			return null;
		}
		// Save To Memento
		actualLockOpt.get().saveToMemento(dto);
		return dto;
	}

	/**
	 * Find hist by target YM.
	 *
	 * @param closureId
	 *            the closure id
	 * @param targetMonth
	 *            the target month
	 * @return the list
	 */
	public List<ActualLockHistFindDto> findHistByTargetYM(int closureId, int targetMonth) {
		// Get LoginUserContext
		LoginUserContext loginUserContext = AppContexts.user();

		// CompanyId
		String companyId = loginUserContext.companyId();

		// FindAll ActualLock By ClosureId
		List<ActualLockHistory> actualLockHistList = this.actualLockHistRepo.findByTargetMonth(companyId, closureId,
				targetMonth);
		// Filter
		List<ActualLockHistory> filtedList = actualLockHistList.stream().filter(hist -> {
			// Filter ActualLockHistory: YearMonth of LockDateTime equal to
			// selected targetMonth
			return hist.getLockDateTime().yearMonth().v() == targetMonth;
		}).collect(Collectors.toList());

		// Convert to Dto
		return filtedList.stream().map(actualLockHist -> {
			ActualLockHistFindDto dto = new ActualLockHistFindDto();
			actualLockHist.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());

	}

	public List<ActualLockHistFindDto> findHistByClosure(int closureId) {
		// Get LoginUserContext
		LoginUserContext loginUserContext = AppContexts.user();

		// CompanyId
		String companyId = loginUserContext.companyId();

		int targetMonth = GeneralDateTime.now().yearMonth().v();

		// FindAll ActualLock By ClosureId
		List<ActualLockHistory> actualLockHistList = this.actualLockHistRepo.findByTargetMonth(companyId, closureId,
				targetMonth);

		// Convert to Dto
		return actualLockHistList.stream().map(actualLockHist -> {
			ActualLockHistFindDto dto = new ActualLockHistFindDto();
			actualLockHist.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());

	}

}
