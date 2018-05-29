package nts.uk.ctx.pereg.dom.person.setting.selectionitem.history;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface PerInfoHistorySelectionRepository {
	void add(PerInfoHistorySelection perInfoHistorySelection);

	void remove(String histId);

	void update(PerInfoHistorySelection perInfoHistorySelection);

	Optional<PerInfoHistorySelection> getAllHistoryByHistId(String histId);

	List<PerInfoHistorySelection> getAllHistoryBySelectionItemId(String selectionItemId);
	
	void removeInSelectionItemId(String selectionItemId);

	List<PerInfoHistorySelection> getHistoryByStartDate(GeneralDate startDate);

	List<PerInfoHistorySelection> getAllBySelecItemIdAndCompanyId(String selectionItemId, String companyId);

	/**
	 * get History Selection Item By Date
	 * 
	 * @param baseDate
	 * @param lstSelItemId
	 * @return
	 */
	List<PerInfoHistorySelection> getHistorySelItemByDate(GeneralDate baseDate, List<String> lstSelItemId);

	/**
	 * get all histId
	 */
	List<String> getAllHistId(String histId);

	/**
	 * GET_LAST_HISTORY_BY_SELECTION_ID
	 */
	Optional<PerInfoHistorySelection> getLastHistoryBySelectioId(String selectionItemId);

	/**
	 * Tuan nv: Get all history by companyID:
	 */
	List<PerInfoHistorySelection> getAllHistoryByCompanyID(String companyId);

	// hoatt
	/**
	 * get History Selection By EndDate
	 * 
	 * @param selectionItemId
	 * @param endDate
	 * @return
	 */
	List<PerInfoHistorySelection> getHistSelByEndDate(String selectionItemId, String companyId, GeneralDate endDate);

	/**
	 * get History Selection By HistId
	 * 
	 * @param histId
	 * @return
	 */
	Optional<PerInfoHistorySelection> getHistSelByHistId(String histId);
}
