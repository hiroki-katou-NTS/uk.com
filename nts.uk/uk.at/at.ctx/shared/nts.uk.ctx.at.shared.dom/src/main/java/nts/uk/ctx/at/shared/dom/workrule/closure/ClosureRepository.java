/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workrule.closure;

import java.util.List;
import java.util.Optional;

import nts.arc.time.YearMonth;

/**
 * The Interface ClosureRepository.
 */
public interface ClosureRepository {

	/**
	 * Adds the.
	 *
	 * @param closure the closure
	 */
	void add(Closure closure);

	/**
	 * Update.
	 *
	 * @param closure the closure
	 */
	void update(Closure closure);

	/**
	 * Find all.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	List<Closure> findAll(String companyId);
	
	/**
	 * Find all use.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	List<Closure> findAllUse(String companyId);

	/**
	 * Find by id.
	 *
	 * @param companyId the company id
	 * @param closureId the closure id
	 * @return the optional
	 */
	Optional<Closure> findById(String companyId, int closureId);
	
	/**
	 * Find by list id.
	 *
	 * @param companyId the company id
	 * @param closureIds the closure ids
	 * @return the list
	 */
	List<Closure> findByListId(String companyId, List<Integer> closureIds);

	/**
	 * Find all active.
	 *
	 * @param companyId the company id
	 * @param useAtr the use atr
	 * @return the list
	 */
	List<Closure> findAllActive(String companyId, UseClassification useAtr);
	
	
	
	/**
	 * Find by closure id.
	 *
	 * @param companyId the company id
	 * @param closureId the closure id
	 * @return the list
	 */
	List<ClosureHistory> findByClosureId(String companyId, int closureId);
	
	
	/**
	 * Find by company id.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	List<ClosureHistory> findByCompanyId(String companyId);
	
	/**
	 * Find by id.
	 *
	 * @param companyId the company id
	 * @param closureId the closure id
	 * @param startYM the start YM
	 * @return the optional
	 */
	Optional<ClosureHistory> findById(String companyId, int closureId, int startYM);
	
	
	/**
	 * Find by selected year month.
	 *
	 * @param companyId the company id
	 * @param closureId the closure id
	 * @param yearMonth the year month
	 * @return the optional
	 */
	Optional<ClosureHistory> findBySelectedYearMonth(String companyId, int closureId, int yearMonth);
	
	
	/**
	 * Find by history last.
	 *
	 * @param companyId the company id
	 * @param closureId the closure id
	 * @return the optional
	 */
	Optional<ClosureHistory> findByHistoryLast(String companyId, int closureId);
	
	/**
	 * Find by history begin.
	 *
	 * @param companyId the company id
	 * @param closureId the closure id
	 * @return the optional
	 */
	Optional<ClosureHistory> findByHistoryBegin(String companyId, int closureId);
	
	/**
	 * Add by ThanhPV
	 * @param companyId
	 * @param closureId
	 * @param closureMonth
	 * @return Optional<ClosureHistory>
	 */
	Optional<Closure> findClosureHistory(String companyId, int closureId, int useClass);
	
	/**
	 * Find by closure id and current month.
	 *
	 * @param closureId the closure id
	 * @param closureMonth the closure month
	 * @return the optional
	 */
	Optional<ClosureHistory> findByClosureIdAndCurrentMonth(String companyId, Integer closureId, Integer closureMonth);
	
	/**
	 * Find by current month.
	 *
	 * @param companyId the company id
	 * @param currentMonth the current month
	 * @return the list
	 */
	List<ClosureHistory> findByCurrentMonth(String companyId, YearMonth currentMonth);

	/**
	 * Find by current year month and used.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	List<ClosureHistory> findByCurrentYearMonthAndUsed(String companyId);
	
	
	/**
	 * Adds the history.
	 *
	 * @param closureHistory the closure history
	 */
	void addHistory(ClosureHistory closureHistory);
	
	
	/**
	 * Update history.
	 *
	 * @param closureHistory the closure history
	 */
	void updateHistory(ClosureHistory closureHistory);
	
	
	/**
	 * Find history by id and current month.
	 *
	 * @param closureIds the closure ids
	 * @param closureMonths the closure months
	 * @return the list
	 */
	List<ClosureHistory> findHistoryByIdAndCurrentMonth(List<Integer> closureIds , List<Integer> closureMonths);
	
	/**
	 * Gets the closure list.
	 *
	 * @param companyId the company id
	 * @return the closure list
	 */
	List<Closure> getClosureList(String companyId);
	
	/**
	 * Find by id and use atr.
	 *
	 * @param companyId the company id
	 * @param closureId the closure id
	 * @param useAtr the use atr
	 * @return the optional
	 */
	default Optional<Closure> findByIdAndUseAtr(String companyId, int closureId, UseClassification useAtr) {
		Optional<Closure> foundedClosure = this.findById(companyId, closureId);
		
		// Check closure exits.
		if (!foundedClosure.isPresent()) {
			return Optional.empty();
		}
		
		// Check use Atr;
		if (foundedClosure.get().getUseClassification() == useAtr) {
			return foundedClosure;
		}
		
		return Optional.empty();
	}
	
	/**
	 * Find.
	 *
	 * @param companyId the company id
	 * @param closureId the closure id
	 * @param useAtr the use atr
	 * @param processingYm the processing ym
	 * @return the optional
	 */
	default Optional<Closure> find(String companyId, int closureId, UseClassification useAtr, YearMonth processingYm) {
		Optional<Closure> foundedClosure = this.findByIdAndUseAtr(companyId, closureId, useAtr);
		// Check closure exits.
		if (!foundedClosure.isPresent()) {
			return Optional.empty();
		}
		// Check processingYM.
		if (!foundedClosure.get().getClosureMonth().getProcessingYm().equals(processingYm)) {
			return foundedClosure;
		}
		return Optional.empty();
	}

}
