/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.base.simplehistory;

import java.util.Optional;

import nts.arc.time.YearMonth;

/**
 * The Class SimpleHistoryBaseService.
 */
public abstract class SimpleHistoryBaseService<M extends Master, H extends History<H>> {

	/**
	 * Delete history.
	 *
	 * @param uuid the uuid
	 */
	public void deleteHistory(String uuid) {
		Optional<H> hisOpt = this.getRepository().findHistoryByUuid(uuid);
		if (!hisOpt.isPresent()) {
			throw new RuntimeException("History not found.");

		}

		// Remove history.
		H history = hisOpt.get();
		this.getRepository().deleteHistory(history.getUuid());

		// Update latest history.
		hisOpt = this.getRepository().findLastestHistoryByMasterCode(
				history.getCompanyCode().v(),
				history.getMasterCode().v());
		if (hisOpt.isPresent()) {
			history = hisOpt.get();
			history.setEnd(YearMonth.of(999999));
			this.getRepository().updateHistory(history);
		}
	}

	/**
	 * Copy history.
	 *
	 * @param copiedHistoryId the copied history id
	 * @param startYear the start year
	 */
	public H copyFromLasterHistory(String companyCode, String masterCode, YearMonth startYear) {
		Optional<H> historyOpt = this.getRepository().findLastestHistoryByMasterCode(companyCode, masterCode);
		if (!historyOpt.isPresent()) {
			throw new RuntimeException("History not found.");
		}		
		H lastestHistory = historyOpt.get();

		// New history.
		H newHistory = lastestHistory.copyWithDate(startYear);
		this.getRepository().addHistory(newHistory);

		// Update old history.
		lastestHistory.setEnd(startYear.previousMonth());
		this.getRepository().updateHistory(lastestHistory);

		// Ret.
		return newHistory;
	}

	/**
	 * Create history.
	 *
	 * @param copiedHistoryId the copied history id
	 * @param startYear the start year
	 */
	public H createHistory(String companyCode, String masterCode, YearMonth startYear) {
		Optional<H> historyOpt = this.getRepository().findLastestHistoryByMasterCode(companyCode, masterCode);

		// New history.
		H newHistory = this.createInitalHistory(companyCode, masterCode, startYear);
		this.getRepository().addHistory(newHistory);

		// Update old history.
		if (historyOpt.isPresent()) {
			H lastestHistory = historyOpt.get();
			lastestHistory.setEnd(startYear.previousMonth());
			this.getRepository().updateHistory(lastestHistory);
		}

		// Ret.
		return newHistory;
	}

	/**
	 * Gets the repository.
	 *
	 * @return the repository
	 */
	public abstract SimpleHistoryRepository<M, H> getRepository();

	/**
	 * Creates the inital history.
	 *
	 * @param masterCode the master code
	 * @return the m
	 */
	public abstract H createInitalHistory(String companyCode, String masterCode, YearMonth startTime);
}
