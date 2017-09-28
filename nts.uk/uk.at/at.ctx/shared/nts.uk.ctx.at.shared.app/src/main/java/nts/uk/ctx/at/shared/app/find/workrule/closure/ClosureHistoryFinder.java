/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.workrule.closure;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.ClosureHistoryFindDto;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.ClosureHistoryHeaderDto;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.ClosureHistoryInDto;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.ClosurePeriodDto;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDay;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistory;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistoryRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class ClosureHistoryFinder.
 */
@Stateless
public class ClosureHistoryFinder {

	/** The repository. */
	@Inject
	private ClosureRepository repository;

	/** The repository history. */
	@Inject
	private ClosureHistoryRepository repositoryHistory;

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<ClosureHistoryFindDto> findAll() {

		// get user login
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		// get all closure
		List<Closure> closures = this.repository.findAll(companyId);

		// get data
		List<ClosureHistory> closureHistories = new ArrayList<>();

		closures.forEach(closure -> {
			Optional<ClosureHistory> closureHistoryLast = this.repositoryHistory
					.findBySelectedYearMonth(companyId, closure.getClosureId(),
							closure.getClosureMonth().getProcessingYm().v());

			if (closureHistoryLast.isPresent()) {
				closureHistories.add(closureHistoryLast.get());
			}
		});

		// domain to data
		return closureHistories.stream().map(closureHistory -> {
			ClosureHistoryFindDto dto = new ClosureHistoryFindDto();
			closureHistory.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());
	}

	/**
	 * Detail.
	 *
	 * @param master
	 *            the master
	 * @return the closure history header dto
	 */
	public ClosureHistoryHeaderDto findById(ClosureHistoryInDto master) {
		// get user login
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		// call repository find closure history
		Optional<ClosureHistory> closureHistory = this.repositoryHistory.findById(companyId,
				master.getClosureId(), master.getStartDate());

		// return data
		ClosureHistoryHeaderDto dto = new ClosureHistoryHeaderDto();
		if (closureHistory.isPresent()) {
			closureHistory.get().saveToMemento(dto);
		}
		return dto;
	}

	/**
	 * Gets the closure period.
	 *
	 * @param closureId
	 *            the closure id
	 * @param processingYm
	 *            the processing ym
	 * @return the closure period
	 */
	// 当月の期間を算出する
	public ClosurePeriodDto getClosurePeriod(int closureId, YearMonth processingYm) {
		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		//
		Optional<ClosureHistory> optClosureHistory = this.repositoryHistory
				.findBySelectedYearMonth(companyId, closureId, processingYm.v());

		// Check exist
		if (!optClosureHistory.isPresent()) {
			return null;
		}

		ClosureHistory closureHistory = optClosureHistory.get();

		ClosureDay closureDay = closureHistory.getClosureDate().getClosureDay();
		GeneralDate startDate = GeneralDate.ymd(processingYm.year(), processingYm.month(), 1);
		GeneralDate endDate = DateUtil.getLastDateOfMonth(processingYm.year(),
				processingYm.month());

		if (closureHistory.getClosureDate().getLastDayOfMonth() || !DateUtil
				.isDateOfMonth(processingYm.year(), processingYm.month(), closureDay.v())) {
			return ClosurePeriodDto.builder().startDate(startDate).endDate(endDate).build();
		}

		endDate = GeneralDate.ymd(processingYm.year(), processingYm.month(), closureDay.v());
		return ClosurePeriodDto.builder().startDate(startDate).endDate(endDate).build();
	}

}
